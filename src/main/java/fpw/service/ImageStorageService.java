package fpw.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fpw.service.storage.FileStorage;
import fpw.service.storage.ImageStorage;
import fpw.service.storage.MemoryStorage;

@Service
public class ImageStorageService {
	
	@Value("${camera.config.storage.type}")
	String storageType;
	
	@Value("${camera.config.storage.file.path}")
	String storagePath;
	
	
	public ImageStorage getImageStorageInstance(){
		
		if ("File".equalsIgnoreCase(storageType)){
			return new FileStorage(storagePath);
		}else if ("Memory".equalsIgnoreCase(storageType)){
			return new MemoryStorage();
		} else {
			System.err.println("Storatge type camera.config.storage.type not understood Using MemoryStorage");
			
			return new MemoryStorage();
		}
		
	}
	

}
