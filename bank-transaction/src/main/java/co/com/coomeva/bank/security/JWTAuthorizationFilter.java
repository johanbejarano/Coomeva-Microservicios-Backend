package co.com.coomeva.bank.security;

import static co.com.coomeva.bank.security.Constants.HEADER_AUTHORIZACION_KEY;
import static co.com.coomeva.bank.security.Constants.SUPER_SECRET_KEY;
import static co.com.coomeva.bank.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
* @author Zathura Code Generator Version 23.03 http://zathuracode.org/
* www.zathuracode.org
* @generationDate 2023-05-03T09:19:19.667315
*
*/
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)throws IOException, ServletException {
		
		String header = req.getHeader(HEADER_AUTHORIZACION_KEY);

		log.info("URL {}", req.getRequestURI());
		
		if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			// Se procesa el token y se recupera el usuario.
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SUPER_SECRET_KEY));
			String user = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, "")).getBody().getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
