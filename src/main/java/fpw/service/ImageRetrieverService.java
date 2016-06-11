package fpw.service;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

@Component
public class ImageRetrieverService {

	//private List<ImageRetriever> imageRetrievers = null;
	private Map<String, ImageRetriever> imageRetrievers = new HashMap<String, ImageRetriever>();
	private Map<String, QueueImageService> queueIR = new HashMap<String, QueueImageService>();

	public Map<String, ImageRetriever>  getImageRetrievers() {
		return imageRetrievers;
	}

	public void setImageRetrievers(Map<String, ImageRetriever>  imageRetrievers) {
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
		for (ImageRetriever tempIR: (List<ImageRetriever>)result){
			imageRetrievers.put(tempIR.getID(), tempIR);
		}
		d.close();

		makeQueueRetrievers();
		scheduleThreads();

	}

	private void scheduleThreads() {
		for (QueueImageService qir : queueIR.values()) {
			Thread t = new Thread(qir);
			t.setDaemon(true);
			t.start();
		}
	}

	void makeQueueRetrievers() {
		for (ImageRetriever ir : imageRetrievers.values()) {
			queueIR.put(ir.getID(), new QueueImageService(ir));
		}
	}

	public Image getFirstImage(String ID) throws IOException {
		return queueIR.get(ID).getFirst();
	}
	
	public Image getImageAt(String ID, int index) throws IOException {
		return queueIR.get(ID).getAt(index);
	}
		

}
