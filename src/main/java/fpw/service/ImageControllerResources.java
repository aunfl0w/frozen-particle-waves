package fpw.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fpw.domain.image.Image;
import fpw.domain.image.ImageRetriever;

@RestController
public class ImageControllerResources {

	@Autowired
	ImageRetrieverService imageRS;

	@RequestMapping("/camera/info")
	public Collection<ImageRetriever> getInfo(HttpServletResponse response) throws IOException {
		//response.getWriter().println(String.format("{\"count\": %d }", imageRS.getImageRetrievers().size()));
		//getClass().response.flushBuffer();
		return imageRS.getImageRetrievers().values();
	}
	

	@RequestMapping(path = "/camera/{cameraID}/image", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, HttpServletResponse response) throws Throwable {
		Image img = imageRS.getFirstImage(cameraID);
		response.setContentType(img.getContentType());
		response.setContentLength(img.getData().length);
		response.getOutputStream().write(img.getData());
		response.flushBuffer();
	}
	
	@RequestMapping(path = "/camera/{cameraID}/image/{imageID}", method = RequestMethod.GET)
	@ResponseBody
	public void getImage(@PathVariable String cameraID, @PathVariable int imageID, HttpServletResponse response) throws Throwable {
		Image img = imageRS.getImageAt(cameraID, imageID);
		response.setContentType(img.getContentType());
		response.setContentLength(img.getData().length);
		response.getOutputStream().write(img.getData());
		response.flushBuffer();
	}
}
