package com.cg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.Exception.BankException;
import com.cg.Exception.ErrorInfo;

/*
 * AccountAdvice is the class to take the badrequest processed to the frontend
 * so errors can be seen on screen
 * 
 */
@RestControllerAdvice
public class AccountAdvice {

	//this annotation here captures the request from the custom exception class
	@ExceptionHandler(value= {BankException.class})
	
	//this annotation responds for any bad request 
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	
	/*
	 * function for handelling the exceptions and passing to the frontend
	 */
	public ErrorInfo  handelException(BankException ex) {
		return new ErrorInfo(ex.getMessage());
	}
}