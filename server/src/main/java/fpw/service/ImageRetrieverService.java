package fpw.service;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import fpw.domain.image.ImageRetriever;

@Component
public class ImageRetrieverService {

	ImageStorageService imageStorageService;
	ApplicationEventPublisher eventPublisher;

	private Map<String, ImageRetriever> imageRetrievers = new HashMap<String, ImageRetriever>();
	private Map<String, QueueImageService> queueIR = new HashMap<String, QueueImageService>();

	public Map<String, ImageRetriever> getImageRetrievers() {
		return imageRetrievers;
	}

	public void setImageRetrievers(Map<String, ImageRetriever> imageRetrievers) {
		this.imageRetrievers = imageRetrievers;
	}

	@Autowired
	public ImageRetrieverService(@Value("${camera.config.file}") String configFile,
			@Value("${camera.config.requestWait:15000}") int requestWait, ImageStorageService iss, ApplicationEventPublisher eventPublisher)
			throws FileNotFoundException {
		imageStorageService = iss;
		this.eventPublisher = eventPublisher; 
		loageImageRetrievers(configFile, requestWait);
	}

	@SuppressWarnings("unchecked")
	public void loageImageRetrievers(String location, int timeoutMS) throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(location)));
		Object result = d.readObject();
		for (ImageRetriever tempIR : (List<ImageRetriever>) result) {
			imageRetrievers.put(tempIR.getID(), tempIR);
		}
		d.close();

		makeQueueRetrievers(timeoutMS);
		scheduleThreads();

	}

	private void scheduleThreads() {
		for (QueueImageService qir : queueIR.values()) {
			qir.setNotifier(eventPublisher);
			Thread t = new Thread(qir, qir.ir.getID() + " Thread");
			t.setDaemon(true);
			t.start();
		}
	}

	void makeQueueRetrievers(int timeoutMS) {
		for (ImageRetriever ir : imageRetrievers.values()) {
			queueIR.put(ir.getID(), new QueueImageService(ir, timeoutMS, imageStorageService));
		}
	}

}
