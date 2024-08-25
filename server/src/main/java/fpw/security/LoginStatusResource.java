package fpw.security;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class LoginStatusResource {
	public static class Status {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	@RequestMapping("/status")
	public Status getStatus(Principal p) throws Exception {
		Status s = new Status();
		s.name = p.getName();
		return s;
	}
	
	
}
