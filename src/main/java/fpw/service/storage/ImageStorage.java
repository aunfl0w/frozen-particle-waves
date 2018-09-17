package fpw.service.storage;

import java.io.FileNotFoundException;
import java.io.InputStream;

import fpw.domain.image.Image;

public interface ImageStorage {

	public void saveBytes(Image image);
	public InputStream getInputStream() throws FileNotFoundException; 
	public String getContentType();
	public int getLength();
	public default String getFilePath() { return "";}
}
