package com.cg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.Exception.BankException;
import com.cg.Exception.ErrorInfo;

@RestControllerAdvice
public class AccountAdvice {

	@ExceptionHandler(value= {BankException.class})
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorInfo  handelException(BankException ex) {
		return new ErrorInfo(ex.getMessage());
	}
}