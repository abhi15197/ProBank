package com.cg.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.Exception.BankException;
import com.cg.dao.IAccountDao;
import com.cg.dao.ITransactionDao;
import com.cg.dto.NewAccount;
import com.cg.dto.NewTransaction;
import com.cg.entity.Account;
import com.cg.entity.Transaction;

@Service
public class BankService {
	
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ITransactionDao transactionDao;

	public Account createAccount(NewAccount request) throws BankException
	{
		
		Date date=new Date();
		Transaction newtransaction=new Transaction();
		
		Account account=accountDao.save(request.getAccount());
		newtransaction.setTransAmount(request.getAccount().getAccountBalance());
		newtransaction.setTransClosingBalance(request.getAccount().getAccountBalance());
		newtransaction.setTransDate(date);
		newtransaction.setTransFrom(request.getAccount().getAccountId());
		newtransaction.setTransOption("New Account Created");
		newtransaction.setTransTo(request.getAccount().getAccountId());
		newtransaction.setTransType("Credit");
		
		NewTransaction newTrans=new NewTransaction();
		newTrans.setTransaction(newtransaction);
		createTransaction(newTrans, request.getAccount().getAccountId());
		
		return 	account;
	}
	

	public Transaction createTransaction(NewTransaction request, int accountId) throws BankException
	{
		getAccount(request.getTransaction().getTransFrom());
		getAccount(request.getTransaction().getTransTo());
		if(request.getTransaction().getTransDate()==null)
			throw new BankException("Invalid Date");
		
		
		Account account=getAccount(accountId);
		if(request.getTransaction().getTransType().equalsIgnoreCase("Debit"))
		{
			if(request.getTransaction().getTransAmount()>getAccount(accountId).getAccountBalance())
				throw new BankException("Insufficient Balance to Complete the Transaction");
			account.setAccountBalance(account.getAccountBalance()-request.getTransaction().getTransAmount());
		}	
		if(request.getTransaction().getTransType().equalsIgnoreCase("Credit"))
			account.setAccountBalance(account.getAccountBalance()+request.getTransaction().getTransAmount());
		accountDao.save(account);
		
		request.getTransaction().setAccount(getAccount(accountId));
		request.getTransaction().setTransClosingBalance(getAccount(accountId).getAccountBalance());
		return transactionDao.save(request.getTransaction());

	}

	public Transaction getTransaction(int transactionId) throws BankException {
		try {
			Optional<Transaction> transaction=transactionDao.findById(transactionId);
			return transaction.get();
		}
		catch(Exception e)
		{
			throw new BankException("Transaction Not Found With "+transactionId+" Id");
		}
	}

	public Account getAccount(int accountId) throws BankException {
		
		try {
			
			Optional<Account> account= accountDao.findById(accountId);
			return account.get();
		}
		catch(Exception e)
		{
			throw new BankException("No Account Associated to "+accountId);
		}	
	}
	
	public List<Transaction> getTransactions(Date date) throws BankException {
		
		if(date==null)
			throw new BankException("Invalid Date");
		try {
		return transactionDao.findBytransDate(date);}
		catch(Exception e)
		{
			throw new BankException(e.getMessage());
		}
	}

	public List<Transaction> getTransactionOfAccount(int accountId) throws BankException {
		
		Account account=getAccount(accountId);	
		if(account==null)
			throw new BankException("No Account Associated to "+accountId);
		return transactionDao.getTransactionsOfAccount(account);
	}

}
