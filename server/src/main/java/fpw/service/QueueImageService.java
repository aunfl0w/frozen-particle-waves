package fpw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import fpw.domain.image.FailImage;
import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;
import fpw.events.ClientCommunication;
import fpw.events.ClientCommunicationEvent;

public class QueueImageService implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueImageService.class);
	ImageRetriever ir;
	ApplicationEventPublisher eventPublisher;
	ImageStorageService iss;
	int requestWait = 15000;

	public QueueImageService() {
	}

	public QueueImageService(ImageRetriever ir2, int timeoutMS, ImageStorageService imageStorageService) {
		ir = ir2;
		iss = imageStorageService;
		this.requestWait = timeoutMS;

	}

	public void run() {

		try {
			while (true) {
				log.info("Getting Image from " + ir.toString());
				Image image = null;

				try {
					image = ir.getImage();
				} catch (Throwable t) {
					log.error(ir.toString(), t);
					log.error("Setting FailImage");
					image = new FailImage(ir.getID());
				}

				try {
					Long imageID = iss.saveImage(ir.getID(), image);
					notifyClients(ir.getID(), imageID);
				} catch (Throwable t) {
					log.error("Error storing image", t);
				}

				try {
					Thread.sleep(requestWait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable t) {
			log.error("Unexpected Exception exit from run thread", t);
		}
		log.error("Unexpected exit from run thread");
	}

	private void notifyClients(String cameraID, Long imageID) {
		log.info("notify clients " + cameraID + " : " + imageID);
		ClientCommunication com = new ClientCommunication();
		com.setImageID(imageID);
		com.setMessageType("1");
		com.setUpdateCamera(cameraID);
		ClientCommunicationEvent event = new ClientCommunicationEvent(this, com);
		eventPublisher.publishEvent(event);
	}

	public void setNotifier(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;

	}

}
