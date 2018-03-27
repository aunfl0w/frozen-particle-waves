package fpw.service;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

@Component
public class ImageRetrieverService {
	
	ImageStorageService imageStorageService;

	WebSocketNotifier wsn;

	private Map<String, ImageRetriever> imageRetrievers = new HashMap<String, ImageRetriever>();
	private Map<String, QueueImageService> queueIR = new HashMap<String, QueueImageService>();

	public Map<String, ImageRetriever>  getImageRetrievers() {
		return imageRetrievers;
	}

	public void setImageRetrievers(Map<String, ImageRetriever>  imageRetrievers) {
		this.imageRetrievers = imageRetrievers;
	}

	@Autowired
	public ImageRetrieverService(@Value("${camera.config.file}") String configFile, 
								 @Value("${camera.config.requestWait:15000}") int requestWait,
								 ImageStorageService iss,
								 WebSocketNotifier wsn) throws FileNotFoundException {
		imageStorageService = iss;
		this.wsn = wsn;
		loageImageRetrievers(configFile, requestWait);
	}

	@SuppressWarnings("unchecked")
	public void loageImageRetrievers(String location, int timeoutMS) throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(location)));
		Object result = d.readObject();
		for (ImageRetriever tempIR: (List<ImageRetriever>)result){
			imageRetrievers.put(tempIR.getID(), tempIR);
		}
		d.close();

		makeQueueRetrievers(timeoutMS);
		waitForSeconds(15);
		scheduleThreads();

	}

	void waitForSeconds(int seconds)  {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void scheduleThreads() {
		for (QueueImageService qir : queueIR.values()) {
			qir.setNotifier(wsn);
			Thread t = new Thread(qir,qir.ir.getID() + " Thread");
			t.setDaemon(true);
			t.start();
		}
	}

	void makeQueueRetrievers(int timeoutMS) {
		for (ImageRetriever ir : imageRetrievers.values()) {
			queueIR.put(ir.getID(), new QueueImageService(ir, timeoutMS, imageStorageService));
		}
	}

	public ImageStorage getFirstImage(String ID) throws IOException {
		return queueIR.get(ID).getFirst();
	}
	
	public ImageStorage getImageAt(String ID, int index) throws IOException {
		return queueIR.get(ID).getAt(index);
	}
		

}
