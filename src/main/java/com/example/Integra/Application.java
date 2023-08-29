package com.example.Integra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
		return factory -> {
			if (factory instanceof TomcatServletWebServerFactory) {
				TomcatServletWebServerFactory tomcatFactory = (TomcatServletWebServerFactory) factory;
				tomcatFactory.addConnectorCustomizers(connector -> {
					connector.setPort(443);
					connector.setSecure(true);
					connector.setScheme("https");
					connector.setProperty("sslEnabled", "true");
					connector.setProperty("sslProtocol", "TLS");
				});
			}
		};
	}


}
