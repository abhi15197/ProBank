package com.cg.service;

import java.util.Date;
import java.util.List;

import com.cg.Exception.BankException;
import com.cg.dto.NewAccount;
import com.cg.dto.NewTransaction;
import com.cg.entity.Account;
import com.cg.entity.Transaction;

public interface IBankService {

	public Account createAccount(NewAccount request) throws BankException;
	public Transaction createTransaction(NewTransaction request, int accountId) throws BankException;
	public Transaction getTransaction(int transactionId) throws BankException;
	public Account getAccount(int accountId) throws BankException;
	public List<Transaction> getTransactionsAfterDate(Date date) throws BankException;
	public List<Transaction> getTransactionOfAccount(int accountId) throws BankException;




}
