package com.cg.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Account;

public interface IAccountDao extends JpaRepository<Account, String> {

}
