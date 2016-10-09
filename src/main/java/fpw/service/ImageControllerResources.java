package fpw.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;

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
	public Collection<ImageRetriever> getInfo(HttpServletResponse response) throws IOException {
		//response.getWriter().println(String.format("{\"count\": %d }", imageRS.getImageRetrievers().size()));
		//getClass().response.flushBuffer();
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
		response.setContentLength(img.getLength());
		OutputStream os = response.getOutputStream();
		InputStream is = img.getInputStream();
		byte [] buff = new byte[1024];
		int size = 0; 
		do {
			size = is.read(buff);
			if (size > 0)
				os.write(buff, 0, size);
			
		} while (size > 0);
		is.close();
		
		response.flushBuffer();
	}
}
