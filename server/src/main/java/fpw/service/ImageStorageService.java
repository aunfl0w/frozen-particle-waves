package fpw.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fpw.domain.image.Image;
import fpw.service.storage.FileStorage;
import fpw.service.storage.ImageStorage;
import fpw.service.storage.MemoryStorage;

@Service
public class ImageStorageService {

	private static final Logger log = LoggerFactory.getLogger(ImageStorageService.class);

	@Value("${camera.config.storage.type}")
	String storageType;

	@Value("${camera.config.storage.file.path}")
	String storagePath;

	@Value("${camera.config.storage.max:500}")
	int storageMax;

	ConcurrentHashMap<String, ConcurrentNavigableMap<Long, ImageStorage>> images = new ConcurrentHashMap<String, ConcurrentNavigableMap<Long, ImageStorage>>();

	public ImageStorage getLatestImage(String cameraID) {
		return getImageMap(cameraID).lastEntry().getValue();
	}

	public ImageStorage getImage(String cameraID, Long imageID) {
		return getImageMap(cameraID).get(imageID);

	}

	public List<Long> getImageIdList(String cameraID) {
		return getImageIdList(cameraID, 50);
	}
	
	public List<String> getImagePathList(String cameraID){
		Collection<ImageStorage> imageList = getImageMap(cameraID).values();
		List<String> filePaths = new ArrayList<String>();
		for (ImageStorage imageStorage : imageList) {
			filePaths.add(imageStorage.getFilePath());
		}
		
		return filePaths;
	}

	public List<Long> getImageIdList(String cameraID, int limit) {
		List<Long> keylist = new ArrayList<Long>();
		keylist.addAll(getImageMap(cameraID).keySet());
		Collections.reverse(keylist);

		if (keylist.size() > limit)
			keylist = keylist.subList(0, limit);

		return keylist;
	}

	private ImageStorage getImageStorageInstance() {

		if ("File".equalsIgnoreCase(storageType)) {
			return new FileStorage(storagePath);
		} else if ("Memory".equalsIgnoreCase(storageType)) {
			return new MemoryStorage();
		} else {
			log.error("Storatge type camera.config.storage.type not understood Using MemoryStorage");
			return new MemoryStorage();
		}

	}

	public Long saveImage(String cameraid, Image image) {
		Long uniqueID = makeUniqueID();
		ConcurrentNavigableMap<Long, ImageStorage> imageMap = getImageMap(cameraid);
		ImageStorage iss = getImageStorageInstance();
		iss.saveBytes(image);
		imageMap.put(uniqueID, iss);
		cleanup(cameraid);
		return uniqueID;
	}

	private void cleanup(String cameraid) {
		ConcurrentNavigableMap<Long, ImageStorage> imglist = getImageMap(cameraid);
		if (imglist.keySet().size() >= storageMax) {
			int removeCount = imglist.keySet().size() - storageMax;
			while (removeCount > 0) {
				removeCount--;
				imglist.remove(imglist.firstEntry().getKey());
				log.info("removing " + removeCount);
			}
		}

	}

	synchronized ConcurrentNavigableMap<Long, ImageStorage> getImageMap(String cameraid) {
		ConcurrentNavigableMap<Long, ImageStorage> imageMap;
		imageMap = images.get(cameraid);
		if (imageMap == null) {
			imageMap = new ConcurrentSkipListMap<Long, ImageStorage>();
			images.put(cameraid, imageMap);
		}
		return imageMap;
	}

	Long makeUniqueID() {
		return new Date().getTime();
	}

}
