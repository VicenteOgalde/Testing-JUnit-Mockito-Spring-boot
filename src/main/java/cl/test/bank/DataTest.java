package cl.test.bank;

import java.math.BigDecimal;
import java.util.Optional;

import cl.test.bank.entities.Account;
import cl.test.bank.entities.Bank;

public class DataTest {
	
	public static Optional<Account> createAcc001(){
		return Optional.of(new Account(1L,"client1",new BigDecimal("1000")));
	}
	public static Optional<Account> createAcc002(){
		return Optional.of(new Account(2L,"client2",new BigDecimal("2000")));
	}
	public static Optional<Bank> createBank001(){
		return Optional.of(new Bank(1L,"bank1",0));
	}
	

}
