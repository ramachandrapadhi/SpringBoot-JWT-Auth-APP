package com.learningtech.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JWTAuthenticationFilter jwtAuthFilter;
	
	private final AuthenticationProvider authenticationProvider;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(JWTAuthenticationFilter.UN_PROTECTED_API_LIST)
//							auth.requestMatchers("/api/v1/auth/**")
						.permitAll().anyRequest().authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(authenticationProvider).formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
		// .cors(cors-> cors.configurationSource(crosConfig()))
		;

//		This type Configuration got deprecate in spring boot 3
//		http
//		.csrf()
//		.disable()
//		.authorizeHttpRequests()
//		.requestMatchers(JWTAuthenticationFilter.UN_PROTECTED_API_LIST)
//		.permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()
//		.authenticationProvider(authenticationProvider)
//		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//		.formLogin(Customizer.withDefaults())
//		.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}

//	used for CORS error
//	private CorsConfigurationSource crosConfig() {
//		
//		 CorsConfiguration configuration = new CorsConfiguration();
//		    configuration.setAllowedOrigins(Arrays.asList("*"));
//		    configuration.setAllowedMethods(Arrays.asList("*"));
//		    configuration.setAllowedHeaders(Arrays.asList("*"));
//		    configuration.setMaxAge(3600l);
//		    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		    source.registerCorsConfiguration("/**", configuration);
//		    return source;
//	}

	
	
	
	

}
