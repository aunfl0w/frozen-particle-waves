package fpw.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * login and logout with json requests post in json body to login.json {
 * "userName" : "admin" , "password" : "password", rememberMe: boolean}
 * 
 * @author aunfl0w
 *
 */
@RestController
@RequestMapping("api/")
public class JsonLoginResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDeatilsService;

	@Value("${authn.password.remembermekey:fpw}")
	String rememberMeKey;

	//@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	@PostMapping(path = "/login.json")
	public void jsonLoginAttempt(@RequestBody LoginRequest loginRequest, jakarta.servlet.http.HttpServletRequest request,
			HttpServletResponse response) {

		try {

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					loginRequest.getUserName(), loginRequest.getPassword());

			token.setDetails(new WebAuthenticationDetails(request));

			Authentication auth = authenticationManager.authenticate(token);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);

			if (auth.isAuthenticated()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
				if (loginRequest.isRememberMe()) {
					addRememberMeCookie(request, response, auth);
				}
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				SecurityContextHolder.getContext().setAuthentication(null);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}

	}

	private void addRememberMeCookie(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		TokenBasedRememberMeServices tbrs = new TokenBasedRememberMeServices(rememberMeKey, userDeatilsService);
		tbrs.onLoginSuccess(request, response, auth);

	}

	//@RequestMapping(value = "/logout.json", method = RequestMethod.GET)
	@GetMapping(path = "/logout.json")
	public void jsonLogoutAttempt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			new SecurityContextLogoutHandler().logout(request, response, auth);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		response.sendRedirect("");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
