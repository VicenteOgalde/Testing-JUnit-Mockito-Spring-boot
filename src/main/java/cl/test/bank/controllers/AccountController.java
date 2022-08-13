package cl.test.bank.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.test.bank.DTOs.TransactionDTO;
import cl.test.bank.entities.Account;
import cl.test.bank.services.IAccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private IAccountService accountService;
	
	@GetMapping
	@ResponseStatus(code=HttpStatus.OK)
	public List<Account> findAll() {
		return accountService.findAll();
	}
	@GetMapping("/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	public Account getAccountDetails(@PathVariable Long id) {
		return accountService.findAccountById(id);
	}
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Account saveAccount(@RequestBody Account account) {
		return accountService.saveAccount(account);
	}
	@PostMapping("/moneyTransfer")
	public ResponseEntity<?> moneyTransfer(@RequestBody TransactionDTO transactionDTO){
		accountService.moneyTransfer(transactionDTO.getOriginAccountId(),
				transactionDTO.getDestinationAccountId(),transactionDTO.getAmount(),
				transactionDTO.getBankId());
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("message", "Transfer done successfully");
		response.put("transactionDTO", transactionDTO);
		return ResponseEntity.ok(response);
	}
	
}
