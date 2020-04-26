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
public class BankService implements IBankService{
	
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ITransactionDao transactionDao;

	@Override
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
	
	@Override
	public Transaction createTransaction(NewTransaction request, int accountId) throws BankException
	{
		
		if(request.getTransaction().getTransDate()==null)
			throw new BankException("Invalid Date");
		
		Account account=getAccount(accountId);
		Account accountfrom=getAccount(request.getTransaction().getTransFrom());
		Account accountto=getAccount(request.getTransaction().getTransTo());
		
		if(request.getTransaction().getTransOption().equalsIgnoreCase("BY Transfer"))
		{
			if(request.getTransaction().getTransFrom().equals(accountId)) {
				
				if(request.getTransaction().getTransFrom().equals(request.getTransaction().getTransTo()))
					throw new BankException("Same Account Transfer");	
				else 
				{
					if(request.getTransaction().getTransType().equalsIgnoreCase("Debit"))
					{
						if(request.getTransaction().getTransAmount()>getAccount(accountId).getAccountBalance())
							throw new BankException("Insufficient Balance to Complete the Transaction");
						accountfrom.setAccountBalance(accountfrom.getAccountBalance()-request.getTransaction().getTransAmount());
						accountto.setAccountBalance(accountto.getAccountBalance()+request.getTransaction().getTransAmount());
						
						Transaction transaction=new Transaction();
						transaction.setAccount(accountto);
						transaction.setTransAmount(request.getTransaction().getTransAmount());
						transaction.setTransClosingBalance(accountto.getAccountBalance()+request.getTransaction().getTransAmount());
						transaction.setTransDate(new Date());
						transaction.setTransFrom(request.getTransaction().getTransFrom());
						transaction.setTransTo(request.getTransaction().getTransTo());
						transaction.setTransOption("By Transfer");
						transaction.setTransType("Credit");
						transactionDao.save(transaction);
						
					}
					if(request.getTransaction().getTransType().equalsIgnoreCase("Credit"))
						throw new BankException("Credit is not an option of Account Transfer");
					
					accountDao.save(accountfrom);
					accountDao.save(accountto);
				}
			}
			else {
				throw new BankException("Account Id Does Not Match with the account Required");
			}
				
		}
		
		if(request.getTransaction().getTransOption().equalsIgnoreCase("BY SELF"))
		{
			if(request.getTransaction().getTransType().equalsIgnoreCase("Debit"))
			{
				if(request.getTransaction().getTransAmount()>getAccount(accountId).getAccountBalance())
					throw new BankException("Insufficient Balance to Complete the Transaction");
				account.setAccountBalance(account.getAccountBalance()-request.getTransaction().getTransAmount());
			}	
			if(request.getTransaction().getTransType().equalsIgnoreCase("Credit"))
				account.setAccountBalance(account.getAccountBalance()+request.getTransaction().getTransAmount());
			accountDao.save(account);
		}
		
		request.getTransaction().setTransDate(new Date());
		request.getTransaction().setAccount(getAccount(accountId));
		request.getTransaction().setTransClosingBalance(getAccount(accountId).getAccountBalance());
		return transactionDao.save(request.getTransaction());
	}

	@Override
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

	@Override
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
	
	@Override
	public List<Transaction> getTransactionsAfterDate(Date date) throws BankException {
		
		if(date==null)
			throw new BankException("Invalid Date");
		if(date.after(new Date()))
			throw new BankException("Invalid Date");
		try {
		return transactionDao.findTransactionAfterDate(date);
		}
		catch(Exception e)
		{
			throw new BankException(e.getMessage());
		}
	}

	@Override
	public List<Transaction> getTransactionOfAccount(int accountId) throws BankException {
		
		Account account=getAccount(accountId);	
		if(account==null)
			throw new BankException("No Account Associated to "+accountId);
		return transactionDao.getTransactionsOfAccount(account);
	}

}
