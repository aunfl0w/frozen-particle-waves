package fpw.domain.image.camera;



import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Base64.Encoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BasicAuthURLCamera extends BasicURLCamera {
	String username;
	String password;
	
	String generateBAString(){
		String toenc = username + ":" + password;
		Encoder encoder = Base64.getEncoder();
		byte[] encoded = encoder.encode(toenc.getBytes());
		String b64 = new String(encoded);
		return "Basic " + b64;
	}
	
	@Override
	InputStream getInputStream(URL u) throws IOException {
		URLConnection urlConn = u.openConnection();
		urlConn.setRequestProperty("Authorization", generateBAString());
		return urlConn.getInputStream();
	}
	
	
	@JsonIgnore
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
