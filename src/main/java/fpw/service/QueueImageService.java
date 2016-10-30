package fpw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

public class QueueImageService implements Runnable {
	ImageRetriever ir;
	ImageStorageService iss; 
	int requestWait = 15000;
	List<ImageStorage> images = Collections.synchronizedList(new ArrayList<ImageStorage>());

	public QueueImageService(ImageRetriever ir2, int timeoutMS, ImageStorageService imageStorageService) {
		ir = ir2;
		iss = imageStorageService;
		this.requestWait = timeoutMS;
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Gettimg Image from " + ir.toString());
				Image image = ir.getImage();
				ImageStorage is = iss.getImageStorageInstance();
				is.saveBytes(image);
				images.add(0, is);
				if (images.size() > 10) {
					System.out.println("removing " + (images.size() - 1));
					images.remove(images.size() - 1);

				}
			} catch (Throwable t) {
				System.out.println(t);
			}
			try {
				Thread.sleep(requestWait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public ImageStorage getFirst() {
		return getAt(0);
	}

	public ImageStorage getAt(int i) {
		return images.get(i);
	}

}
