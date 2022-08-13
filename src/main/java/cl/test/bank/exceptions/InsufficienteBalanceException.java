package cl.test.bank.exceptions;

public class InsufficienteBalanceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficienteBalanceException(String message) {
		super(message);
		
	}

	
}
