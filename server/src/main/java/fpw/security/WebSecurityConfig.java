package fpw.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RESTAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	UserDetailsManager MANAGER = null;

	@Value("${authn.password.admin:password}") 
	String adminPassword; 
	
	@Value("${authn.password.user:password}") 
	String userPassword; 
	
	@Value("${authn.password.remembermekey:fpw}")
	String rememberMeKey;
	
	class FPWAuthenticationProvider implements AuthenticationProvider {

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			String userId = (String)authentication.getPrincipal();
			String password = (String)authentication.getCredentials();
			String storedPassword = MANAGER.loadUserByUsername(userId).getPassword();
			String encodedPassword = ENCODER.apply(password);
			if (storedPassword.equals(encodedPassword)) {
				UserDetails userDetails = MANAGER.loadUserByUsername(userId);
				UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
						userId, authentication.getCredentials(),
						userDetails.getAuthorities());
				result.setDetails(authentication.getDetails());
				return result; 
			}
			return null;
		}

		@Override
		public boolean supports(Class<?> authentication) {
			return authentication.equals(org.springframework.security.authentication.UsernamePasswordAuthenticationToken.class);
		}
		
	}
	
	Function<String, String> ENCODER = (String input) -> {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] data = md.digest((rememberMeKey + input).getBytes());
			result = "{fpw}." + new String(Base64.getEncoder().encode(data));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
			
		return result;
		 
	};
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.builder().passwordEncoder(ENCODER)
				.username("User")
				.password(userPassword)
				.roles("USER")
				.build();
		UserDetails admin = User.builder().passwordEncoder(ENCODER)
				.username("Admin")
				.password(adminPassword)
				.roles("USER", "ADMIN")
				.build();
		ArrayList<UserDetails> users = new ArrayList<UserDetails>();
		users.add(user);
		users.add(admin);
		MANAGER = new InMemoryUserDetailsManager(users);
		return MANAGER;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/log**").permitAll()
			.antMatchers("/api/**").authenticated()
		.and()
			.authenticationProvider(new FPWAuthenticationProvider())
			.headers()
			.disable()
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
		.and()
			.formLogin()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
		.and()
			.rememberMe()
			.alwaysRemember(false)
			.key(rememberMeKey);
		
	}
	

	
	@Bean("authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
}
