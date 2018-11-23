package fpw.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoProducerService {
	private static final Logger log = LoggerFactory.getLogger(VideoProducerService.class);

	static {
		log.info("static block");
	}

	@Value("${camera.config.storage.file.path}")
	String storagePath;

	@Value("${camera.config.vidoe.cahceminutes:6}")
	int minutesToCacheVideo;

	Map<String, VideoItem> videoMap = Collections.synchronizedMap(new HashMap<String, VideoItem>());
	List<VideoItem> cleanupList = Collections.synchronizedList(new ArrayList<VideoItem>());

	class VideoItem {
		Date requested = new Date();
		String videoFile = null;

		VideoItem() {
		}

		boolean isRecently() {
			Date now = new Date();
			long age = now.getTime() - requested.getTime();
			if (age > 1000 * 60 * minutesToCacheVideo) {
				return false;
			}
			return true;
		}

		boolean isCleanupRead() {
			Date now = new Date();
			long age = now.getTime() - requested.getTime();
			if (age > 1000 * 60 * ((minutesToCacheVideo * 2) + 1)) {
				return false;
			}
			return true;
		}

		void cleanup() {
			File f = new File(videoFile);
			f.delete();
		}
	}

	void cleanup() {
		for (String key : videoMap.keySet()) {
			if (!videoMap.get(key).isRecently()) {
				cleanupList.add(videoMap.remove(key));
			}
		}

		for (VideoItem vi : cleanupList) {
			if (vi.isCleanupRead()) {
				vi.cleanup();
				cleanupList.remove(vi);
			}
		}
	}

	public void sendVideo(HttpServletResponse response, List<String> pathList, String cameraID)
			throws FileNotFoundException, IOException, InterruptedException {
		VideoItem vi = null;

		synchronized (this) {
			cleanup();
			vi = videoMap.get(cameraID);
			if (vi == null)
				vi = getVideo(pathList, cameraID);

			videoMap.put(cameraID, vi);
		}

		log.info("Sending video for " + cameraID + ", image count is " + pathList.size());
		response.setContentType("video/mp4");
		OutputStream os = response.getOutputStream();
		File tmpVideoFile = new File(vi.videoFile);
		FileInputStream fis = new FileInputStream(tmpVideoFile);
		byte[] buff = new byte[4096];
		int size = 0;
		do {
			os.write(buff, 0, size);
			size = fis.read(buff);
		} while (size > 0);

		os.flush();
		fis.close();
	}

	synchronized VideoItem getVideo(List<String> pathList, String cameraID) throws IOException, InterruptedException {

		String tmpFileName = storagePath + File.separatorChar + UUID.randomUUID().toString();
		File tmpFile = new File(tmpFileName);
		try (   
			FileOutputStream fos = new FileOutputStream(tmpFile);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		) {
			
			for (String string : pathList) {
				bw.write(string);
				bw.newLine();
			}
		} 

		String cmd = "mencoder mf://@" + tmpFileName
				+ " -idx -nosound -noskip -of lavf -lavfopts format=mp4 -ovc x264 -x264encopts bitrate=2500:nocabac:bframes=0:level_idc=12:crf=20 -mf fps=6 -vf scale=640 -o "
				+ tmpFileName + ".mp4";

		log.info(cmd);
		String[] cmdArr = cmd.split(" ");
		Process p = Runtime.getRuntime().exec(cmdArr);
		p.waitFor();
		tmpFile.delete();

		VideoItem vi = new VideoItem();
		vi.videoFile = tmpFileName + ".mp4";
		return vi;
	}

}
