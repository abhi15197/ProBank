package com.cg.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.Exception.BankException;
import com.cg.dao.IAccountDao;
import com.cg.dao.ITransactionDao;
import com.cg.dao.IUpdateDateDao;
import com.cg.dto.NewAccount;
import com.cg.dto.NewTransaction;
import com.cg.dto.Transaction2;
import com.cg.entity.Account;
import com.cg.entity.Transaction;
import com.cg.entity.UpdateDate;


/*
 * Service Class:
 * this is the class which mainly communicates between controller class and Dao Interfaces which are basically JPA Repository
 * this class deals with all the maojor functions in the program.
 * So,all the logics are mainly handled here, like
 * @creating account logic 
 * @creating new transaction
 * @fetching any account
 * @fetching transactions of a single account
 * @fetching any transaction
 * also updating passbook logic is implemented which takes a date and returns the transactions happened after that date
 */
@Service
public class BankService implements IBankService{
	
	@Autowired
	private IAccountDao accountDao;
	@Autowired
	private ITransactionDao transactionDao;
	@Autowired
	private IUpdateDateDao updateDateDao;

	@Override
	public String createAccount(NewAccount request) throws BankException
	{
		Transaction newtransaction=new Transaction();
		accountDao.save(request.getAccount());
		
		newtransaction.setTransAmount(request.getAccount().getAccountBalance());
		newtransaction.setTransFrom(request.getAccount().getAccountId());
		newtransaction.setTransOption("Cash");
		newtransaction.setTransTo(request.getAccount().getAccountId());
		newtransaction.setTransType("New Account Credit");
		NewTransaction newTrans=new NewTransaction();
		newTrans.setTransaction(newtransaction);
		
		createTransaction(newTrans, request.getAccount().getAccountId());
		
		UpdateDate updateDate = new UpdateDate();
		updateDate.setAccountId(request.getAccount().getAccountId());
		updateDate.setLastUpdateDate(new Date());
		updateDate.setAccount(request.getAccount());
		updateDateDao.save(updateDate);
		
		return 	"account created";
	}
	
	@Override
	public String newTransaction(@Valid Transaction2 transaction2, String accountId) throws BankException {
		Transaction req=new Transaction();
		req.setTransAmount(transaction2.getTransAmount());
		req.setTransFrom(transaction2.getTransFrom());
		req.setTransTo(transaction2.getTransTo());
		req.setTransType(transaction2.getTransType());
		req.setTransOption(transaction2.getTransOption());
		NewTransaction request=new NewTransaction();
		request.setTransaction(req);
		return createTransaction(request, accountId);
	}

	@Override
	public String createTransaction(NewTransaction request, String accountId) throws BankException
	{	
		
		Transaction transaction=new Transaction();
		Account account=getAccount(accountId);
		
		Account accountfrom=getAccount(request.getTransaction().getTransFrom());
		
		Account accountto=getAccount(request.getTransaction().getTransTo());
		
		
		if(request.getTransaction().getTransOption().equalsIgnoreCase("Transfer"))
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
						
						
						transaction.setAccount(accountto);
						transaction.setTransAmount(request.getTransaction().getTransAmount());
						transaction.setTransClosingBalance(accountto.getAccountBalance());
						transaction.setTransDate(new Date());
						transaction.setTransFrom(request.getTransaction().getTransFrom());
						transaction.setTransTo(request.getTransaction().getTransTo());
						transaction.setTransOption("Transfer");
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
		
		if(request.getTransaction().getTransOption().equalsIgnoreCase("Cash"))
		{request.getTransaction().setTransTo(accountId);
			if(request.getTransaction().getTransType().equalsIgnoreCase("Debit"))
			{
				if(request.getTransaction().getTransAmount()>getAccount(accountId).getAccountBalance())
					throw new BankException("Insufficient Balance to Complete the Transaction");
				account.setAccountBalance(account.getAccountBalance()-request.getTransaction().getTransAmount());
			}	
			if(request.getTransaction().getTransType().equalsIgnoreCase("Credit"))
				account.setAccountBalance(account.getAccountBalance()+request.getTransaction().getTransAmount());
			if(request.getTransaction().getTransType().equalsIgnoreCase("New Account Credit"))
				account.setAccountBalance(account.getAccountBalance());
			accountDao.save(account);
		}
		
		request.getTransaction().setTransDate(new Date());
		request.getTransaction().setAccount(getAccount(accountId));
		request.getTransaction().setTransClosingBalance(getAccount(accountId).getAccountBalance());
		
		transactionDao.save(request.getTransaction());
		return "Transaction Done";
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
	public Account getAccount(String accountId) throws BankException {
		
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
	public List<Transaction> updatePassbook(String accountId) throws BankException {
	try {
			Account acc=getAccount(accountId);
			Optional<UpdateDate> update=updateDateDao.findById(accountId);
			UpdateDate updateDate=update.get();
			Date date=updateDate.getLastUpdateDate();
			Date newdate=new Date();
			updateDate.setLastUpdateDate(newdate);
			updateDateDao.save(updateDate);
			return transactionDao.findTransactionAfterDate(date,acc);
		}
		catch(Exception e)
		{
			throw new BankException("Update Failed");
		}
	}

	@Override
	public List<Transaction> getTransactionOfAccount(String accountId) throws BankException {
		
		Account account=getAccount(accountId);	
		if(account==null)
			throw new BankException("No Account Associated to "+accountId);
		return transactionDao.getTransactionsOfAccount(account);
	}

	

}
