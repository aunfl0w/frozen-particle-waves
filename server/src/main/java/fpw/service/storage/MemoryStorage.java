package fpw.service.storage;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fpw.domain.image.Image;

public class MemoryStorage implements ImageStorage {
	
	Image data;

	@Override
	public int getLength() {
		return data.getData().length;
	}

	@Override
	public void saveBytes(Image image) {
		data = image;
	}

	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data.getData());
		return bis;
	}

	@Override
	public String getContentType() {
		return data.getContentType();
	}

}
