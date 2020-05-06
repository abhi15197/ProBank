package com.cg.service;

import java.util.List;

import javax.validation.Valid;

import com.cg.Exception.BankException;
import com.cg.dto.NewAccount;
import com.cg.dto.NewTransaction;
import com.cg.dto.Transaction2;
import com.cg.entity.Account;
import com.cg.entity.Transaction;

public interface IBankService {

	
	/*
	 * this is the interface of the service class,
	 * this contains all the necessary methods and functions needed in the service class.
	 */
	public String createAccount(NewAccount request) throws BankException;
	public String newTransaction(@Valid Transaction2 transaction2, String accountId) throws BankException;
	public String createTransaction(NewTransaction request, String accountId) throws BankException;
	public Transaction getTransaction(int transactionId) throws BankException;
	public Account getAccount(String accountId) throws BankException;
	public List<Transaction> getTransactionOfAccount(String accountId) throws BankException;
	public List<Transaction> updatePassbook(String accountId) throws BankException;




}
