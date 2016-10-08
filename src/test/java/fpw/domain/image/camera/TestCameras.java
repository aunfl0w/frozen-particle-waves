package fpw.domain.image.camera;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCameras {

	@Test
	public void basic_auth_camera_builds_auth_header() {
		String expected = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";
		
		BasicAuthURLCamera baurlc = new BasicAuthURLCamera();
		baurlc.setUsername("username");
		baurlc.setPassword("password");
		baurlc.setDescription("test");
		String actual = baurlc.generateBAString();
		
		assertEquals(expected, actual);
		
		
	}

}
