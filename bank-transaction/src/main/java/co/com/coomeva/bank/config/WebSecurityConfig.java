package co.com.coomeva.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class WebSecurityConfig {

	private static final String ACTUATOR_BASE                = "/actuator";
	private static final String MATCHERS_ACTUATOR_HEALTH     = ACTUATOR_BASE + "/health";
	private static final String MATCHERS_ACTUATOR_PROMETHEUS = ACTUATOR_BASE + "/prometheus";
	
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        		.requestMatchers(HttpMethod.GET, MATCHERS_ACTUATOR_HEALTH, MATCHERS_ACTUATOR_PROMETHEUS).permitAll()
        		
                //.requestMatchers(HttpMethod.GET, "/test/anonymous", "/test/anonymous/**").permitAll()
                //.requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(ADMIN)
                //.requestMatchers(HttpMethod.GET, "/test/user").hasAnyRole(ADMIN, USER)
        		
                .anyRequest().authenticated();
        
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}