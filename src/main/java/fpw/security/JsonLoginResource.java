package fpw.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * login and logout with json requests
 * post in json body to login.json { "userName" : "admin" , "password" : "password" }
 * 
 * @author aunfl0w
 *
 */
@RestController
public class JsonLoginResource {

	@Autowired
	AuthenticationManager authenticationManager;


	@RequestMapping(value="/login.json", method = RequestMethod.POST)
	public void jsonLoginAttempt(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

	    try {
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
	        token.setDetails(new WebAuthenticationDetails(request));

	        Authentication auth = authenticationManager.authenticate(token);
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	        securityContext.setAuthentication(auth);

	        if(auth.isAuthenticated()){
	        	// how to do without session?
	            HttpSession session = request.getSession(true);
	            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
	            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	        }else{
	            SecurityContextHolder.getContext().setAuthentication(null);
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        }   
	    } catch (Exception e) {
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    }

	}
	
	@RequestMapping(value="/logout.json", method = RequestMethod.GET)
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
