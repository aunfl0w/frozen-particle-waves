package fpw.service;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

@Component
public class ImageRetrieverService {

	List<ImageRetriever> imageRetrievers = null;



	public List<ImageRetriever> getImageRetrievers() {
		return imageRetrievers;
	}

	public void setImageRetrievers(List<ImageRetriever> imageRetrievers) {
		this.imageRetrievers = imageRetrievers;
	}

	@Autowired
	public ImageRetrieverService(@Value("${camera.config.file}") String configFile) throws FileNotFoundException {
		loageImageRetrievers(configFile);
	}

	@SuppressWarnings("unchecked")
	public void loageImageRetrievers(String location) throws FileNotFoundException {
		XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(location)));
		Object result = d.readObject();
		imageRetrievers = (List<ImageRetriever>) result;
		d.close();
	}

	public Image getFirstImage(int index) throws IOException {
		return imageRetrievers
				.get(index)
				.getImage();
	}

}
