package eu.unicredit.dummy_java_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class DummyJavaAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DummyJavaAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DummyJavaAppApplication.class, args);
	}
}
