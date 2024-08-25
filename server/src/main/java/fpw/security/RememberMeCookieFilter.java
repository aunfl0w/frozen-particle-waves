package fpw.security;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

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
	public void doFilter(jakarta.servlet.ServletRequest request, ServletResponse response, FilterChain chain)
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
