package com.dileep.ecommerce.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dileep.ecommerce.ms.config.TtsProperties;

@SpringBootApplication
@EnableConfigurationProperties(TtsProperties.class)
//@ConfigurationPropertiesScan

public class BooksManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksManagementApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {

				registry.addMapping("/**").allowedOrigins("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS").allowedHeaders("*")
						.exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "jwt_token").maxAge(3600);
			}
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
