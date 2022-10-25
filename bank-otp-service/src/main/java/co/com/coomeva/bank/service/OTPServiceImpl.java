package co.com.coomeva.bank.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.com.coomeva.bank.domain.Users;
import co.com.coomeva.bank.dto.OTPValidationRequest;
import co.com.coomeva.bank.dto.OTPValidationResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	private UsersService usersService;

	@Override
	@Transactional(readOnly = true)
	public OTPValidationResponse validateOTP(OTPValidationRequest otpValidationRequest) throws Exception {

		log.info("Validando el OTP " + otpValidationRequest.getOtp() + 
				" del usuario " + otpValidationRequest.getUser());
		
		//Se consulta el usuario
		String userEmail = otpValidationRequest.getUser();
		Optional<Users> userOptional = usersService.findById(userEmail);
		
		if (!userOptional.isPresent()) {
			throw new Exception ("No existe el usaurio " + userEmail);
		}
		
		//Se obtiene el token del usuario
		Users user = userOptional.get();
		String token = user.getToken();
		String tokenToValidate = otpValidationRequest.getOtp();
		
		OTPValidationResponse otpValidationResponse = new OTPValidationResponse();
		
		//Se valida si el token coincide
		if (token!=null && token.equals(tokenToValidate)) {
			Random rnd = new Random();
			int doubleCheckNumber = rnd.nextInt(9999-1111) + 1111;
			otpValidationResponse.setDoubleCheckCode(doubleCheckNumber);
			
			otpValidationResponse.setValid(true);
			otpValidationResponse.setDoubleCheckCode(doubleCheckNumber);
		} else {
			
			//El token no coincide
			otpValidationResponse.setValid(false);
			otpValidationResponse.setErrorMessage("Invalid token");
			
		}
		
		return otpValidationResponse;
	}

}
