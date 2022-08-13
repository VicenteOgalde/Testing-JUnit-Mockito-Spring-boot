package cl.test.bank.DTOs;

import java.math.BigDecimal;

public class TransactionDTO {
	
	private Long originAccountId;
	private Long destinationAccountId;
	private BigDecimal amount;
	private Long bankId;
	
	
	public TransactionDTO() {
		
	}
	public TransactionDTO(Long originAccountId, Long destinationAccountId, BigDecimal amount, Long bankId) {
		
		this.originAccountId = originAccountId;
		this.destinationAccountId = destinationAccountId;
		this.amount = amount;
		this.bankId = bankId;
	}
	public Long getOriginAccountId() {
		return originAccountId;
	}
	public void setOriginAccountId(Long originAccountId) {
		this.originAccountId = originAccountId;
	}
	public Long getDestinationAccountId() {
		return destinationAccountId;
	}
	public void setDestinationAccountId(Long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	
	
	

}
