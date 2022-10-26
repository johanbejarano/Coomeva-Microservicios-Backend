package co.com.coomeva.bank.openfeignclients;

import java.util.Collection;
import java.util.Map;

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
		
		log.info("Reading and setting headers to " + url);
		
		//Setting headers
		template.header("MyHeader", "My Value");
		
		//Reading headers
		Map<String, Collection<String>> headers = template.headers();
		headers.forEach((key, values) -> {
			log.info("Key: " + key);
			values.forEach(value -> log.info("Value: " + value));
			
		});
	}
}