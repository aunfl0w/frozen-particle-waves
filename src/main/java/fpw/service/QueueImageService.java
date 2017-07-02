package fpw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fpw.domain.image.FailImage;
import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

public class QueueImageService implements Runnable {
	WebSocketNotifier wsn = null;
	ImageRetriever ir;
	ImageStorageService iss; 
	int requestWait = 15000;
	List<ImageStorage> images = Collections.synchronizedList(new ArrayList<ImageStorage>());
	
	
	
	public QueueImageService(){}
	
	public QueueImageService(ImageRetriever ir2, int timeoutMS, ImageStorageService imageStorageService) {
		ir = ir2;
		iss = imageStorageService;
		this.requestWait = timeoutMS;
	}

	public void run() {
		while (true) {
				System.out.println("Gettimg Image from " + ir.toString());
				Image image = null;
				
				try {
					image = ir.getImage();
				} catch (Throwable t) {
					System.out.println(ir.toString());
					System.out.println(t);
					t.printStackTrace();
					image = new FailImage(ir.getID());
				}
				
				try {
					ImageStorage is = iss.getImageStorageInstance();
					is.saveBytes(image);
					notifyClients(ir.getID());
					images.add(0, is);
					if (images.size() > 20) {
						System.out.println("removing " + (images.size() - 1));
						images.remove(images.size() - 1);
					}
				} catch (Throwable t) {
					System.out.println(t);
					t.printStackTrace();
				}

			try {
				Thread.sleep(requestWait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	


	private void notifyClients(String id) {
		if (wsn == null){
			System.err.println("wsn is null. cannot notify about " + id);
		}else{
			wsn.announceUpdate(id);
		}
	}

	public ImageStorage getFirst() {
		return getAt(0);
	}

	public ImageStorage getAt(int i) {
		return images.get(i);
	}

	public void setNotifier(WebSocketNotifier wsn) {
		this.wsn = wsn;
	}

}
