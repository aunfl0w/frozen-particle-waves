package fpw.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fpw.domain.image.ImageRetriever;
import fpw.service.storage.ImageStorage;

@RestController
public class ImageControllerResources {

	@Autowired
	ImageRetrieverService imageRS;
	
	@RequestMapping("/camera/status")
	public String getStatus(Principal p){
		System.out.println("Principal is " + p.getName());
		return (new Date()).toString();
	}

	@RequestMapping("/camera/info")
	public Collection<ImageRetriever> getInfo() throws IOException {
		return imageRS.getImageRetrievers().values();
	}
	
	@RequestMapping(path = "/camera/{cameraID}/image", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, HttpServletResponse response) throws Throwable {
		ImageStorage img = imageRS.getFirstImage(cameraID);
		writeImage(response, img);
	}
	
	@RequestMapping(path = "/camera/{cameraID}/image/{imageID}", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, @PathVariable int imageID, HttpServletResponse response) throws Throwable {
		ImageStorage img = imageRS.getImageAt(cameraID, imageID);
		writeImage(response, img);
	}
	
	void writeImage(HttpServletResponse response, ImageStorage img) throws IOException, FileNotFoundException {
		
		response.setContentType(img.getContentType());

		//read image
		BufferedImage buffImage = ImageIO.read(img.getInputStream());
		
		//scale image
		Image scaledImage = buffImage.getScaledInstance(buffImage.getWidth() / 2, buffImage.getHeight() / 2 , java.awt.Image.SCALE_SMOOTH);
		BufferedImage scaledBuffImage = new BufferedImage( scaledImage.getWidth(null), scaledImage.getHeight(null), buffImage.getType());
		Graphics2D graphics = scaledBuffImage.createGraphics();
		graphics.drawImage(scaledImage,0,0,null);
		graphics.dispose();
		
		//compress image
		Iterator<ImageWriter> imageWriters =  ImageIO.getImageWritersByMIMEType(img.getContentType());
		ImageWriter imageWriter = imageWriters.next();
		ImageWriteParam parms  =  imageWriter.getDefaultWriteParam();
		parms.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parms.setCompressionQuality(0.15f);
		
		//write image
		OutputStream os = response.getOutputStream();
		ImageIO.write(scaledBuffImage, "jpg", os);
		
		response.flushBuffer();
		imageWriter.dispose();
		os.close();
	}
}
