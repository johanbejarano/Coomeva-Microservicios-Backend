package co.com.coomeva.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.coomeva.bank.dto.OTPValidationRequest;
import co.com.coomeva.bank.dto.OTPValidationResponse;
import jakarta.validation.Valid;



@FeignClient(
		url = "${otp.service.url}",
		value = "otp-service"
		)
public interface OTPServiceClient {

	@PostMapping("/validate")
	public OTPValidationResponse validateOTP(
			@Valid @RequestBody
			OTPValidationRequest otpValidationRequest);
	
}