package com.cg.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.Exception.BankException;
import com.cg.dto.NewAccount;
import com.cg.dto.NewTransaction;
import com.cg.entity.Account;
import com.cg.entity.Transaction;
import com.cg.service.BankService;

@RestController
public class BankController {
	
	@Autowired
	private BankService bankservice;
	
	@PostMapping("/newAccount")
	public Account createAccount(@RequestBody NewAccount request) throws BankException {
	
		return bankservice.createAccount(request);	
	}
	@PostMapping("{accountID}/newTransaction")
	public Transaction createTransaction(@RequestBody NewTransaction request,@PathVariable int accountID) throws BankException {
	
		return bankservice.createTransaction(request,accountID);	
	}
	
	@GetMapping("/transaction/{transactionID}")
	public Transaction getTransaction(@PathVariable int transactionID) throws BankException
	{
		return bankservice.getTransaction(transactionID);
	}
	
	@GetMapping("{accountID}/viewTransactions")
	public List<Transaction> getTransactionsOfAccount(@PathVariable int accountID) throws BankException
	{
		return bankservice.getTransactionOfAccount(accountID);
	}
	
	@GetMapping("/account/{accountID}")
	public Account getAccount(@PathVariable int accountID) throws BankException
	{
		return bankservice.getAccount(accountID);
	}
	
	@GetMapping("/updatePassbook")
	public List<Transaction> getAllOfDate(Date date) throws BankException
	{
		return bankservice.getTransactionsAfterDate(date);
	}
	
}
