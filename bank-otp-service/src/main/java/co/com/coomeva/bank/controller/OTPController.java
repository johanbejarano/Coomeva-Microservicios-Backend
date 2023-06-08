package co.com.coomeva.bank.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.coomeva.bank.dto.OTPValidationRequest;
import co.com.coomeva.bank.dto.OTPValidationResponse;
import co.com.coomeva.bank.service.OTPService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/otp")
@Slf4j
public class OTPController {

	@Autowired
	OTPService otpService;
	
	@PostMapping("/validate")
	public OTPValidationResponse validateOTP(
			@Valid @RequestBody
			OTPValidationRequest otpValidationRequest) throws Exception {
		
		log.info("Validando el OTP " + otpValidationRequest.getOtp() + " del usuario " + otpValidationRequest.getUser());
		
		return otpService.validateOTP(otpValidationRequest);
	}
	
}
