package fpw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

public class QueueImageService implements Runnable {
	ImageRetriever ir;
	int timeoutMS = 15000;
	List<Image> images = Collections.synchronizedList(new ArrayList<Image>());

	public QueueImageService(ImageRetriever ir2, int timeoutMS) {
		ir = ir2;
		this.timeoutMS = timeoutMS;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(timeoutMS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("Gettimg Image from " + ir.toString());
				Image image = ir.getImage();
				images.add(0, image);
				if (images.size() > 10) {
					System.out.println("removing " + (images.size() - 1));
					images.remove(images.size() - 1);

				}
			} catch (Throwable t) {
				// TODO use correct logging
				System.out.println(t);
			}
		}
	}

	public Image getFirst() {
		return getAt(0);
	}

	public Image getAt(int i) {
		return images.get(i);
	}

}
