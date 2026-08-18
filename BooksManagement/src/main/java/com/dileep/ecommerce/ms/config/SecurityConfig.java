package com.dileep.ecommerce.ms.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
				// Disable Basic Auth
				.httpBasic(httpBasic -> httpBasic.disable())
				// Disable form login
				.formLogin(form -> form.disable())
				// JWT should be stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/authors/all",
								"/users/createAdmin",
								"/books/audio/*",
										 "/users/login")
						.permitAll()
						.anyRequest().authenticated())
				        // ADD THIS
				        .addFilterBefore(
				        		jwtAuthenticationFilter,
				            UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();

	    configuration.setAllowedOriginPatterns(List.of("*"));
//		configuration.setAllowedOrigins(List.of("http://localhost:5173"));
		configuration.setAllowedMethods(List.of("*"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
//@Bean
//SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//	http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(csrf -> csrf.disable())
//			.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//					.requestMatchers("/v3/api-docs/**",
//							"/swagger-ui/**",
//							"/swagger-ui.html",
//							"/industries/save",
//							"/organizations/save",
//							"/roles/save",
//							"/users/save",
//							"/auth/login")
//					.permitAll().anyRequest().authenticated())
//			.httpBasic(Customizer.withDefaults());
//
//	return http.build();
//}