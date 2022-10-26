package co.com.coomeva.bank.service;

import co.com.coomeva.bank.dto.OTPValidationResponse;

public interface OTPServiceCircuitBreaker {

	public OTPValidationResponse validateOTP(String user, String otp) throws Exception;
	
}

