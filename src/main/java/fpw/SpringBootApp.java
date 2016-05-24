package fpw;

import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringBootApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp.class, args);

	}
	
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory(){

			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				((StandardHost)tomcat.getHost()).setErrorReportValveClass("");
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}
	    };
	    return tomcat;
	}

	
}
