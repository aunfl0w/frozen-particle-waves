package fpw;

import static org.junit.Assert.assertEquals;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fpw.domain.image.ImageRetriever;
import fpw.domain.image.camera.BasicURLCamera;
import fpw.service.ImageRetrieverService;

public class LoadCamearasTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void SaveList() throws FileNotFoundException {

		List<ImageRetriever> lcs = new ArrayList<ImageRetriever>();
		BasicURLCamera c = new BasicURLCamera();
		c.setUrl("http://192.168.1.2:8080/photo.jpg");
		c.setContentType("image/jpg");
		c.setID("one");
		lcs.add(c);
		
		c = new BasicURLCamera();
		c.setUrl("http://192.168.100.1/logo.gif");
		c.setContentType("image/gif");
		c.setID("two");
		lcs.add(c);

		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("src/test/resources/ImageRetrievers.xml")));
		e.writeObject(lcs);
		e.close();
	}

	@Test
	public void readList() throws Exception {
		ImageRetrieverService imageRS = new ImageRetrieverService("src/test/resources/ImageRetrievers.xml");
		Map<String,ImageRetriever> x = imageRS.getImageRetrievers();
		assertEquals("http://192.168.1.2:8080/photo.jpg", ((BasicURLCamera)x.get("one")).getUrl());
		assertEquals("image/jpg", ((BasicURLCamera)x.get("one")).getContentType());
		assertEquals("http://192.168.100.1/logo.gif", ((BasicURLCamera)x.get("two")).getUrl());
		assertEquals("image/gif", ((BasicURLCamera)x.get("two")).getContentType());

	}

}
