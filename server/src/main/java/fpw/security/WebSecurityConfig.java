package fpw.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private RESTAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	

	@Value("${authn.password.admin:password}") 
	String adminPassword; 
	
	@Value("${authn.password.user:password}") 
	String userPassword; 
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("User")
				.password(userPassword)
				.roles("USER")
				.build();
		UserDetails admin = User.withDefaultPasswordEncoder()
				.username("Admin")
				.password(adminPassword)
				.roles("USER", "ADMIN")
				.build();
		ArrayList<UserDetails> users = new ArrayList<UserDetails>();
		users.add(user);
		users.add(admin);
		
		return new InMemoryUserDetailsManager(users);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/c**")
			.authenticated()
		.and()
			.csrf()
			.disable()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
			.and().formLogin().successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler);
			
		
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
}
