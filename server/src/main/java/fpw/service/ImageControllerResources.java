package fpw.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

@RestController
@RequestMapping("api/")
public class ImageControllerResources {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageControllerResources.class);

	@Autowired
	VideoProducerService videoProducerService;

	@Autowired
	ImageStorageService iss;

	@Autowired
	ImageRetrieverService imageRS;

	@RequestMapping("/camera/info")
	public Collection<ImageRetriever> getInfo() throws IOException {
		return imageRS.getImageRetrievers().values();
	}

	@RequestMapping("/camera/{cameraID}/idlist")
	public Collection<Long> getidList(@PathVariable String cameraID) throws IOException {
		return iss.getImageIdList(cameraID, 40);
	}

	@RequestMapping(path = "/camera/{cameraID}/image", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, HttpServletResponse response) throws Throwable {
		ImageStorage img = iss.getLatestImage(cameraID);
		writeImage(response, img);
	}

	@RequestMapping(path = "/camera/{cameraID}/image/{imageID}", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, @PathVariable Long imageID, HttpServletResponse response)
			throws Throwable {
		response.setHeader("cache-control", "private, max-age=7200");
		ImageStorage img = iss.getImage(cameraID, imageID);
		writeImage(response, img);
	}

	@RequestMapping("/camera/{cameraID}/video")
	@ResponseBody
	public void getidVideo(@PathVariable String cameraID, HttpServletResponse response)
			throws IOException, InterruptedException {
		List<String> pathList = iss.getImagePathList(cameraID);
		videoProducerService.sendVideo(response, pathList, cameraID);
	}

	void writeImage(HttpServletResponse response, ImageStorage img) throws IOException, FileNotFoundException {

		response.setContentType(img.getContentType());

		// read image
		BufferedImage buffImage = ImageIO.read(img.getInputStream());

		float scaleby = 5.0f, qualityby = 0.2f;

		if (buffImage.getWidth() < 4000)
			scaleby = 3.5f;
		qualityby = 0.14f;
		if (buffImage.getWidth() < 3000)
			scaleby = 2.5f;
		qualityby = 0.14f;
		if (buffImage.getWidth() < 2000)
			scaleby = 2.5f;
		qualityby = 0.175f;
		if (buffImage.getWidth() < 1000)
			scaleby = 1.75f;
		qualityby = 0.2f;
		if (buffImage.getWidth() < 750)
			scaleby = 1.0f;
		qualityby = 0.225f;
		if (buffImage.getWidth() < 600)
			scaleby = 1.0f;
		qualityby = 0.25f;

		LOGGER.info(String.format("Image is %d by %d scale to %f quality to %f", buffImage.getWidth(),
				buffImage.getHeight(), scaleby, qualityby));

		// scale image
		Image scaledImage = buffImage.getScaledInstance((int) (buffImage.getWidth() / scaleby),
				(int) (buffImage.getHeight() / scaleby), java.awt.Image.SCALE_SMOOTH);
		BufferedImage scaledBuffImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null),
				buffImage.getType());
		Graphics2D graphics = scaledBuffImage.createGraphics();
		graphics.drawImage(scaledImage, 0, 0, null);
		graphics.dispose();

		// compress image
		Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByMIMEType(img.getContentType());
		ImageWriter imageWriter = imageWriters.next();
		ImageWriteParam parms = imageWriter.getDefaultWriteParam();
		parms.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parms.setCompressionQuality(qualityby);

		// write image
		OutputStream os = response.getOutputStream();
		ImageIO.write(scaledBuffImage, "jpg", os);

		response.flushBuffer();
		imageWriter.dispose();
		os.close();
	}
}
