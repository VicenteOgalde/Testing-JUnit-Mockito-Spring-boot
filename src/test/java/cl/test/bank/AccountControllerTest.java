package cl.test.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.test.bank.DTOs.TransactionDTO;
import cl.test.bank.controllers.AccountController;
import cl.test.bank.entities.Account;
import cl.test.bank.services.IAccountService;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IAccountService accountService;

	ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testGetDetails() throws Exception {
		when(accountService.findAccountById(1L)).thenReturn(DataTest.createAcc001().orElseThrow());

		mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.client").value("client1")).andExpect(jsonPath("$.balance").value("1000"));

		verify(accountService.findAccountById(1L));
	}

	@Test
	public void testMoneyTransfer() throws Exception {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setOriginAccountId(1L);
		transactionDTO.setDestinationAccountId(2L);
		transactionDTO.setAmount(new BigDecimal("100"));
		transactionDTO.setBankId(1L);

		System.out.println(objectMapper.writeValueAsString(transactionDTO));

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("message", "Transfer done successfully");
		response.put("transactionDTO", transactionDTO);

		System.out.println(objectMapper.writeValueAsString(response));

		mockMvc.perform(post("/api/accounts/moneyTransfer").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transactionDTO))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.message").value("Transfer done successfully"))
				.andExpect(jsonPath("$.transactionDTO.originAccountId").value(transactionDTO.getOriginAccountId()))
				.andExpect(content().json(objectMapper.writeValueAsString(response)));

	}

	@Test
	public void accountList() throws JsonProcessingException, Exception {
		List<Account> accounts = Arrays.asList(DataTest.createAcc001().orElseThrow(),
				DataTest.createAcc002().orElseThrow());
		when(accountService.findAll()).thenReturn(accounts);

		mockMvc.perform(get("/api/accounts/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[0].client").value("client1"))
				.andExpect(jsonPath("$.[1].client").value("client2")).andExpect(jsonPath("$.[0].balance").value("1000"))
				.andExpect(jsonPath("$.[1].balance").value("2000")).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().json(objectMapper.writeValueAsString(accounts)));
	}

	@Test
	public void saveAccount() throws JsonProcessingException, Exception {
		Account account = new Account(null, "client3", new BigDecimal("3000"));
		when(accountService.saveAccount(any())).then(invocation -> {
			Account a = invocation.getArgument(0);
			a.setId(3L);
			return a;
		});
		
		mockMvc.perform(post("/api/accounts").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(account)))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(3)))
		.andExpect(jsonPath("$.client", is("client3")))
		.andExpect(jsonPath("$.balance", is(3000)));
		
		verify(accountService.saveAccount(any()));

	}

}
