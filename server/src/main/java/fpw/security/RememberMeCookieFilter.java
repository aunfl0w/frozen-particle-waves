package fpw.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class RememberMeCookieFilter implements Filter {

	public static class RememberMeResponseWrapper extends HttpServletResponseWrapper{

		public RememberMeResponseWrapper(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void addCookie(Cookie cookie) {
			if (cookie.getName().equals("remember-me")) {
				cookie.setPath("/fpw2/api/");
			}
			super.addCookie(cookie);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			if (response instanceof HttpServletResponse) {
				HttpServletResponse res = new RememberMeResponseWrapper((HttpServletResponse)response);
				chain.doFilter(request, res);
			}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {

	}
}
