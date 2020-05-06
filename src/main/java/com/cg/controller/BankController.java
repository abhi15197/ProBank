package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.Exception.BankException;
import com.cg.dto.NewAccount;
import com.cg.dto.Transaction2;
import com.cg.entity.Account;
import com.cg.entity.Transaction;
import com.cg.service.BankService;


/*
 * controller class for handling request from frontend 
 * also the annotation here are restcontroller which specifies that it is a rest call class
 * which handles the restfull api calls.
 * also the crossorigin annotation specifies the origins of request handled by this 
 * restfull controller class.
 * here * specifies that it can handel request from anywhere
 */
@RestController
@CrossOrigin(origins = "*")
public class BankController {
	
	
	//service class object is created using autowiring to reduce dependencies
	@Autowired
	private BankService bankservice;
	
	
	//method to handle post type request to create account 
	//this also consumes json file which has the required data
	//this function returns the created account message as string
	@PostMapping(value="/newAccount",consumes = {"application/json"})
	public String createAccount(@RequestBody NewAccount request) throws BankException {
	
		return bankservice.createAccount(request);	
	}
	
	//method to handle post type request to create new Transaction 
	//this also consumes json file which has the required data
	//this function returns the created transaction message as string
	@PostMapping(value="{accountId}/newTransaction",consumes =  {"application/json"})
	public String createTransaction(@RequestBody Transaction2 transaction2,@PathVariable String accountId) throws BankException {
		return bankservice.newTransaction(transaction2, accountId);
	}
	
	
	//method to handle get type request to return a specific Transaction with its transaction Id 
	//this also produces a json file which has the required data
	//this function returns the transaction data
	@GetMapping(value="/transaction/{transactionID}",produces= {"application/json"})
	public Transaction getTransaction(@PathVariable int transactionID) throws BankException
	{
		return bankservice.getTransaction(transactionID);
	}
	
	//method to handle get type request to return a specific Transaction of a specific account using the account ID
	//this also produces a json file which has the required data
	//this function returns the list of all specific transaction data
	@GetMapping(value="{accountID}/viewTransaction",produces= {"application/json"})
	public List<Transaction> getTransactionsOfAccount(@PathVariable String accountID) throws BankException
	{
		return bankservice.getTransactionOfAccount(accountID);
	}
	
	//method to handle get type request to return a specific Account with its account Id 
		//this also produces a json file which has the required data
		//this function returns the account data
	@GetMapping(value="/account/{accountID}",produces= {"application/json"})
	public Account getAccount(@PathVariable String accountID) throws BankException
	{
		return bankservice.getAccount(accountID);
	}
	
	
	//method to handle get type request to return a specific Transaction 
	//	of a specific account using the account ID after a specific date to update the passbook
	//this also produces a json file which has the required data
	//this function returns the list of all specific transaction data
	@GetMapping(value="{accountId}/updatePassbook",produces = {"application/json"})
	public List<Transaction> updatePassbook(@PathVariable String accountId) throws BankException
	{
		System.out.println(accountId);
		return bankservice.updatePassbook(accountId);
	}
	
}
