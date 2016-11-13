package fpw.service.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import fpw.domain.image.Image;

public class FileStorage implements ImageStorage {

	String path;
	String mimeType;
	int length = 0;
	
	private static Map<String, String> MIME_TO_FILE = new HashMap<String,String>();
	static 	{
		MIME_TO_FILE.put("image/jpeg", ".jpg");
		MIME_TO_FILE.put("image/jpg", ".jpg");
		MIME_TO_FILE.put("image/gif", ".gif");
		
	}
	
	public FileStorage(String path) {
		this.path = path;
	}

	@Override
	public void saveBytes(Image image) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String basePath = path + File.separator + image.getId() + File.separator + dateString + File.separator;
		
		File checkPath = new File(basePath);
		checkPath.mkdirs();

		mimeType  = image.getContentType();
		length = image.getData().length;
		path = basePath + System.currentTimeMillis() 
				    + getExtentionFromMimeType(mimeType);


		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(image.getData());
			fos.flush();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			image.setData(null);
		}
	}


	@Override
	public InputStream getInputStream() throws FileNotFoundException {

		FileInputStream fis = new FileInputStream(path);
		return fis;
	}
	
	private String getExtentionFromMimeType(String mimeType) {
		// TODO use a mimetype to ext library.
		String extension = MIME_TO_FILE.get(mimeType);
		return extension == null ? ".unknown" : extension;
	}


	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public String getContentType() {
		return mimeType;
	}
	
}
