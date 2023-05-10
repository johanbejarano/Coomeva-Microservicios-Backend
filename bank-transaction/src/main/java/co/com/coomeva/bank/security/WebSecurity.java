package co.com.coomeva.bank.security;

import static co.com.coomeva.bank.security.Constants.ACTUATOR_URL;
import static co.com.coomeva.bank.security.Constants.LOGIN_URL;
import static co.com.coomeva.bank.security.Constants.API_DOCS;
import static co.com.coomeva.bank.security.Constants.SWAGGER_UI;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
* @author Zathura Code Generator Version 23.03 http://zathuracode.org/
* www.zathuracode.org
* @generationDate 2023-05-03T09:19:19.667315
* 
*/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
	
		// Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
       
        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		/*
		 * 1. Se desactiva el uso de cookies
		 * 2. Se activa la configuracion CORS con los valores por defecto
		 * 3. Se desactiva el filtro CSRF
		 * 4. Se indica que el login no requiere autenticacion
		 * 5. Se indica que el resto de URLs esten securizadas
		 */
		httpSecurity
			.sessionManagement(sessionManager -> {
				sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			.authorizeHttpRequests( auth -> {
				auth.requestMatchers(LOGIN_URL, ACTUATOR_URL, API_DOCS, SWAGGER_UI).permitAll();
				auth.anyRequest().authenticated();
			})
			
			.csrf( AbstractHttpConfigurer::disable )
			.addFilter(new JWTAuthorizationFilter(authenticationManager))
			.addFilter(new JWTAuthorizationFilter(authenticationManager))
			.authenticationManager(authenticationManager)
			.headers( headersConfig -> {
				headersConfig.frameOptions().disable();
			});
	
		 return httpSecurity.build(); 	
	}


	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar los passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "HEAD"));
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
