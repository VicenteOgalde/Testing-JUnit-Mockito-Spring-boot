package cl.test.bank.entities;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cl.test.bank.exceptions.InsufficienteBalanceException;

@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String client;
	private BigDecimal balance;

	public Account() {

	}

	public Account(Long id,String client, BigDecimal balance) {
		this.id=id;
		this.client = client;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void debitMove(BigDecimal amount) {
		BigDecimal newBalance =this.balance.subtract(amount);
		if(newBalance.compareTo(BigDecimal.ZERO)<0) {
			throw new InsufficienteBalanceException("insufficient money in your account");
		}
		this.balance= newBalance;
	}
	public void creditMove(BigDecimal amount) {
		this.balance=balance.add(amount);
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, client, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(client, other.client)
				&& Objects.equals(id, other.id);
	}
	

}
