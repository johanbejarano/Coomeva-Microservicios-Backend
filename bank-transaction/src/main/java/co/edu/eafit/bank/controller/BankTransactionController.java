package co.edu.eafit.bank.controller;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eafit.bank.dto.DepositDTO;
import co.edu.eafit.bank.dto.TransactionResultDTO;
import co.edu.eafit.bank.dto.TransferDTO;
import co.edu.eafit.bank.dto.WithdrawDTO;
import co.edu.eafit.bank.service.BankTransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
@RefreshScope
public class BankTransactionController {

	@Autowired
	BankTransactionService bankTransactionService;
	
	@Value("${my.property}")
	String myProperty;

	@PostMapping("/transfer")
	@RolesAllowed("ROLE_bank_holder")
	public ResponseEntity<TransactionResultDTO> transfer(@Valid @RequestBody TransferDTO transferDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.transfer(transferDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/withdraw")
	@RolesAllowed("ROLE_bank_holder")
	public ResponseEntity<TransactionResultDTO> withdraw(@Valid @RequestBody WithdrawDTO withdrawDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.withdraw(withdrawDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/deposit")
	@RolesAllowed("ROLE_bank_holder")
	public ResponseEntity<TransactionResultDTO> deposit(@Valid @RequestBody DepositDTO depositDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.deposit(depositDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}
	
	@PostMapping("/unlock")
	@RolesAllowed("ROLE_cashier")
	public ResponseEntity<String> unlockAccount() throws Exception {
		return ResponseEntity.ok().body("Account unlocked");
	}
	
	@GetMapping("/my-property")
	public ResponseEntity<String> getMyProperty(){
		return ResponseEntity.ok(myProperty);
	}

}
