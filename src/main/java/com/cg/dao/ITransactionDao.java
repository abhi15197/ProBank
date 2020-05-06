package com.cg.dao;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.entity.Account;
import com.cg.entity.Transaction;

public interface ITransactionDao extends JpaRepository<Transaction, Integer>{
	
	
		@Query("from Transaction t where t.account=?1")
		public List<Transaction> getTransactionsOfAccount(Account account);
		
		List<Transaction> findByAccount(Account account);
		
		List<Transaction> findBytransDate(Date date);
		
		@Query("from Transaction t where t.transDate>=?1 and t.account=?2")
		List<Transaction> findTransactionAfterDate(Date date,Account account);
}
