package fpw.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

@RestController
public class ImageControllerResources {
	private static final Logger log = LoggerFactory.getLogger(ImageControllerResources.class);
	
	@Value("${camera.config.storage.file.path}")
	String storagePath;

	@Autowired
	ImageStorageService iss;

	@Autowired
	ImageRetrieverService imageRS;

	@Autowired
	WebSocketNotifier ws;

	@RequestMapping("/camera/status")
	public String getStatus(Principal p) throws Exception {
		log.info("Principal is " + p.getName());
		return (new Date()).toString();
	}

	@RequestMapping("/camera/info")
	public Collection<ImageRetriever> getInfo() throws IOException {
		return imageRS.getImageRetrievers().values();
	}

	@RequestMapping("/camera/{cameraID}/idlist")
	public Collection<Long> getidList(@PathVariable String cameraID) throws IOException {
		return iss.getImageIdList(cameraID, 40);
	}

	@RequestMapping(path = "/camera/{cameraID}/image", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, HttpServletResponse response) throws Throwable {
		ImageStorage img = iss.getLatestImage(cameraID);
		writeImage(response, img);
	}

	@RequestMapping(path = "/camera/{cameraID}/image/{imageID}", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, @PathVariable Long imageID, HttpServletResponse response)
			throws Throwable {
		response.setHeader("cache-control", "private, max-age=7200");
		ImageStorage img = iss.getImage(cameraID, imageID);
		writeImage(response, img);
	}

	@RequestMapping("/camera/{cameraID}/video")
	@ResponseBody
	public void getidVideo(@PathVariable String cameraID, HttpServletResponse response) throws IOException, InterruptedException {
		List<String> pathList = iss.getImagePathList(cameraID);
		log.info("Making video for " + cameraID + ", image count is " + pathList.size() );
		sendVideo(response, pathList);
	}

	void sendVideo(HttpServletResponse response, List<String> pathList)
			throws FileNotFoundException, IOException, InterruptedException {
		String tmpFileName = storagePath + File.separatorChar + UUID.randomUUID().toString();
		File tmpFile = new File(tmpFileName);
		BufferedWriter bw = null;
		try {
			FileOutputStream fos = new FileOutputStream(tmpFile);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (String string : pathList) {
				bw.write(string);
				bw.newLine();
			}
		} finally {
			bw.close();
		}
		
		
		String cmd = "mencoder mf://@" + tmpFileName + " -idx -nosound -noskip -of lavf -lavfopts format=mp4 -ovc x264 -x264encopts bitrate=2500:bframes=0:crf=20 -mf fps=6 -vf scale=640 -o " + tmpFileName + ".mp4";
		log.info(cmd);
		String[] cmdArr = cmd.split(" ");
		Process p = Runtime.getRuntime().exec(cmdArr);
		p.waitFor();
		
		
		response.setContentType("video/mp4");
		OutputStream os = response.getOutputStream();
		File tmpVideoFile = new File(tmpFileName + ".mp4");
		FileInputStream fis = new FileInputStream(tmpVideoFile);
		byte[] buff = new byte[4096];
		int size = 0;
		do {
			os.write(buff, 0, size);
			size = fis.read(buff);
		} while (size > 0 );
			
		os.flush();
		fis.close();
		tmpFile.delete();
		tmpVideoFile.delete();
	}


	void writeImage(HttpServletResponse response, ImageStorage img) throws IOException, FileNotFoundException {

		response.setContentType(img.getContentType());

		// read image
		BufferedImage buffImage = ImageIO.read(img.getInputStream());

		float scaleby = 5.0f, qualityby = 0.2f;

		if (buffImage.getWidth() < 4000)
			scaleby = 3.5f;
		qualityby = 0.14f;
		if (buffImage.getWidth() < 3000)
			scaleby = 2.5f;
		qualityby = 0.14f;
		if (buffImage.getWidth() < 2000)
			scaleby = 2.5f;
		qualityby = 0.175f;
		if (buffImage.getWidth() < 1000)
			scaleby = 1.75f;
		qualityby = 0.2f;
		if (buffImage.getWidth() < 750)
			scaleby = 1.0f;
		qualityby = 0.225f;
		if (buffImage.getWidth() < 600)
			scaleby = 1.0f;
		qualityby = 0.25f;

		log.info(String.format("Image is %d by %d scale to %f quality to %f", buffImage.getWidth(),
				buffImage.getHeight(), scaleby, qualityby));

		// scale image
		Image scaledImage = buffImage.getScaledInstance((int) (buffImage.getWidth() / scaleby),
				(int) (buffImage.getHeight() / scaleby), java.awt.Image.SCALE_SMOOTH);
		BufferedImage scaledBuffImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null),
				buffImage.getType());
		Graphics2D graphics = scaledBuffImage.createGraphics();
		graphics.drawImage(scaledImage, 0, 0, null);
		graphics.dispose();

		// compress image
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(img.getContentType());
		ImageWriter imageWriter = imageWriters.next();
		ImageWriteParam parms = imageWriter.getDefaultWriteParam();
		parms.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parms.setCompressionQuality(qualityby);

		// write image
		OutputStream os = response.getOutputStream();
		ImageIO.write(scaledBuffImage, "jpg", os);

		response.flushBuffer();
		imageWriter.dispose();
		os.close();
	}
}
