package cl.test.bank.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.test.bank.entities.Account;
import cl.test.bank.entities.Bank;
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

	@Query("select a from Account a where a.client=?1")
	public Optional<Account> findByClient(String client);
	
}
