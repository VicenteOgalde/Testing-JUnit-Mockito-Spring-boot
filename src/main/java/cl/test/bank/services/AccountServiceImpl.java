package cl.test.bank.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.test.bank.entities.Account;
import cl.test.bank.repositories.AccountRepository;
import cl.test.bank.repositories.BankRepository;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private BankRepository bankRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Account findAccountById(Long id) {
		return accountRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	@Transactional(readOnly = true)
	public int checkTotalTransfers(Long bankId) {
		return bankRepository.findById(bankId).orElseThrow().getTotalTransfers();
	}

	@Override
	public BigDecimal checkBalance(Long accountId) {
		return accountRepository.findById(accountId).orElseThrow().getBalance();
	}

	@Override
	public void moneyTransfer(Long originAccountId, Long destinationAccountId, BigDecimal amount, Long bankId) {
		
		accountRepository.findById(originAccountId).orElseThrow().debitMove(amount);
		
		accountRepository.findById(destinationAccountId).orElseThrow().creditMove(amount);
		
		bankRepository.findById(bankId).orElseThrow()
				.setTotalTransfers(bankRepository.findById(bankId).orElseThrow().getTotalTransfers() + 1);
	}

}
