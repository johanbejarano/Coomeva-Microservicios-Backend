package co.com.coomeva.bank.service;

import co.com.coomeva.bank.dto.OTPValidationRequest;
import co.com.coomeva.bank.dto.OTPValidationResponse;

public interface OTPService {

	public OTPValidationResponse validateOTP(OTPValidationRequest otpValidationRequest) throws Exception;
	
}
