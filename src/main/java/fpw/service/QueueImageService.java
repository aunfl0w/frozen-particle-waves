package fpw.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	static Object ONE_PROCESS_LOCK = new Object();
	
	
	public QueueImageService(){}
	
	public QueueImageService(ImageRetriever ir2, int timeoutMS, ImageStorageService imageStorageService) {
		ir = ir2;
		iss = imageStorageService;
		this.requestWait = timeoutMS;
	}

	public void run() {

		try {
			while (true) {
					System.out.println("Gettimg Image from " + ir.toString());
					Image image = null;
					boolean extraProcessing = ir.isExtraprocessing();
					try {
						image = ir.getImage();
					} catch (Throwable t) {
						System.out.println(ir.toString());
						System.out.println(t);
						t.printStackTrace();
						System.out.println("Setting FailImage");
						image = new FailImage(ir.getID());
					}
					
					try {
						ImageStorage is = iss.getImageStorageInstance();
						is.saveBytes(image);
						notifyClients(ir.getID(), is.getFileName(), extraProcessing);
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
		} catch (Throwable t) {
			System.out.println("Unexpected Exception exit from run thread");
			t.printStackTrace();
		}
		System.out.println("Unexpected exit from run thread");
	}
	


	private void notifyClients(String id, String path, boolean extraProcessing) {
		HashMap<String, String > map = new HashMap<>();
		if (wsn == null){
			System.err.println("wsn is null. cannot notify about " + id);
			return;
		}
		if (path != null && extraProcessing) {
			labelImage(path, map);
		} else {
			map.put("disabled_labeling", "1.00000");
		}
		wsn.announceUpdate(id, map);
		
	}

	void labelImage(String path, HashMap<String, String> map) {
		try {
			String cmd = System.getenv().get("IMAGE_REC_CMD");
			Process p = null;
			
			synchronized (ONE_PROCESS_LOCK) {
				p = Runtime.getRuntime().exec(String.format(cmd, path));
			
				BufferedReader brin = new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader brerr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

				String line = brin.readLine();
				
				while(line != null) {
					
					System.out.println(line);
					Pattern pattern = Pattern.compile("\"(.*)\",\"(.*)\"");
					Matcher m = pattern.matcher(line);
					m.find();
					map.put(m.group(1), m.group(2));
					line = brin.readLine();
				}
				

				line = brerr.readLine();
				while(line != null) {
					
					System.err.println(line);
					line = brerr.readLine();
				}
				p.destroy();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
