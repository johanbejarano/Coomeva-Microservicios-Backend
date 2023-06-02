package co.com.coomeva.bank.openfeignclients;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;

import feign.Feign;

@LoadBalancerClient("bank-otp")
public class OTPServiceClientLoadBalancer {

	@LoadBalanced
	@Bean
	public Feign.Builder buildBalancer() {
		return Feign.builder();
	}
	
}