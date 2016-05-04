package fpw.service;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

@Component
public class ImageRetrieverService {

	private List<ImageRetriever> imageRetrievers = null;
	private List<QueueImageService> queueIR = null;

	public List<ImageRetriever> getImageRetrievers() {
		return imageRetrievers;
	}

	public void setImageRetrievers(List<ImageRetriever> imageRetrievers) {
		this.imageRetrievers = imageRetrievers;
	}

	@Autowired
	public ImageRetrieverService(@Value("${camera.config.file}") String configFile) throws FileNotFoundException {
		loageImageRetrievers(configFile);
	}

	@SuppressWarnings("unchecked")
	public void loageImageRetrievers(String location) throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(location)));
		Object result = d.readObject();
		imageRetrievers = (List<ImageRetriever>) result;
		d.close();

		makeQueueRetrievers();
		scheduleThreads();

	}

	private void scheduleThreads() {
		for (QueueImageService qir : queueIR) {
			Thread t = new Thread(qir);
			t.setDaemon(true);
			t.start();
		}
	}

	void makeQueueRetrievers() {
		queueIR = new ArrayList<QueueImageService>();
		for (ImageRetriever ir : imageRetrievers) {
			queueIR.add(new QueueImageService(ir));
		}
	}

	public Image getFirstImage(int cameraIndex) throws IOException {
		return queueIR.get(cameraIndex).getFirst();
	}
	
	public Image getImageAt(int cameraIndex, int index) throws IOException {
		return queueIR.get(cameraIndex).getAt(index);
	}
		

}
