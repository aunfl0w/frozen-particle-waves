package fpw;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry
		        .addResourceHandler("/resources/**")
		        .addResourceLocations("/resources/")
		        .setCachePeriod(31556926);
	    }

	
}
