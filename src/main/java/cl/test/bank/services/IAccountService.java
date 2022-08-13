package cl.test.bank.services;

import java.math.BigDecimal;
import java.util.List;


import cl.test.bank.entities.Account;


public interface IAccountService {

	public List<Account> findAll();

	public Account findAccountById(Long id);

	public Account saveAccount(Account account);

	public int checkTotalTransfers(Long bankId);

	public BigDecimal checkBalance(Long accountId);

	public void moneyTransfer(Long originAccountId, Long destinationAccountId, BigDecimal amount, Long bankId);

}
