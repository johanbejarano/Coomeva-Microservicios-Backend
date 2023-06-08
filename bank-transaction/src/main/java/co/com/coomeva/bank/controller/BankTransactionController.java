package co.com.coomeva.bank.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.coomeva.bank.dto.DepositDTO;
import co.com.coomeva.bank.dto.TransactionResultDTO;
import co.com.coomeva.bank.dto.TransferDTO;
import co.com.coomeva.bank.dto.WithdrawDTO;
import co.com.coomeva.bank.service.BankTransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
@Slf4j
public class BankTransactionController {

	@Autowired
	BankTransactionService bankTransactionService;

	@PostMapping("/transfer")
	@RolesAllowed("bank_holder")
	public ResponseEntity<TransactionResultDTO> transfer(
			Principal principal,
			@Valid @RequestBody TransferDTO transferDTO) throws Exception {

		JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
		String userName = (String) token.getTokenAttributes().get("name");
        String userEmail = (String) token.getTokenAttributes().get("email");
        
        
		log.info("Username: {}, email: {}, Principal: {}", userName, userEmail, principal);
		
		TransactionResultDTO transactionResultDTO = bankTransactionService.transfer(transferDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/withdraw")
	@RolesAllowed("bank_holder")
	public ResponseEntity<TransactionResultDTO> withdraw(@Valid @RequestBody WithdrawDTO withdrawDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.withdraw(withdrawDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/deposit")
	public ResponseEntity<TransactionResultDTO> deposit(@Valid @RequestBody DepositDTO depositDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.deposit(depositDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}
	
	@PostMapping("/unlock")
	@RolesAllowed("cashier")
	public ResponseEntity<String> unlockAccount(Principal principal) throws Exception {
		
		JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
		String userName = (String) token.getTokenAttributes().get("name");
		
		return ResponseEntity.ok().body("Authenticated user " + userName + ", has unloked a bank account!");
	}

}
