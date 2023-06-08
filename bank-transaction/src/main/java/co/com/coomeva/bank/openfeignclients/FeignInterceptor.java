package co.com.coomeva.bank.openfeignclients;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		String url = template.url();
		
		//Se lee el token de autenticación
		JwtAuthenticationToken token = (JwtAuthenticationToken)SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (token!=null && token.getToken()!=null) {

			log.info("Escribiendo el Bearer token en la petición a: " + url);
			
			//Se envía el token al servicio invocado
			template.header("Authorization", "Bearer " + token.getToken().getTokenValue());
		}
		
	}
}