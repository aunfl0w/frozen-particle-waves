package fpw.domain.image.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

public class BasicURLCamera implements ImageRetriever {


	String url;
	String contentType;
	String id;
	String description;
	

	@Override
	public Image getImage() throws IOException {
		URL u = new URL(url);
		Image image = new Image(id, contentType);
		byte data[] = null;

		InputStream is = null;
		ByteArrayOutputStream bos = null;

		try {
			is = getInputStream(u);
			bos = new ByteArrayOutputStream();
			byte buff[] = new byte[1024];
			int result = -1;
			do {
				result = is.read(buff);
				bos.write(buff, 0, result > 0 ? result : 0);
			} while (result > 0);
			data = bos.toByteArray();
		} finally {
			bos.close();
			is.close();
		}

		image.setData(data);
		return image;
	}

	@JsonIgnore
	InputStream getInputStream(URL u) throws IOException {
		InputStream is;
		is = u.openStream();
		return is;
	}

	@JsonIgnore 
	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return getUrl();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public void setID(String name) {
		this.id = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
