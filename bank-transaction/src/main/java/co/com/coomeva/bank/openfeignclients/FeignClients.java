package co.com.coomeva.bank.openfeignclients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.coomeva.bank.dto.OTPValidationRequest;
import co.com.coomeva.bank.dto.OTPValidationResponse;
import jakarta.validation.Valid;

@FeignClient(
		value = "api-gateway"
		)
public interface FeignClients {

	@PostMapping("/bank-otp/api/v1/otp/validate")
	public OTPValidationResponse validateOTP(
			@Valid @RequestBody
			OTPValidationRequest otpValidationRequest);
	
}