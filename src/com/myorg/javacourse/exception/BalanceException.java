package com.myorg.javacourse.exception;

@SuppressWarnings("serial")
public class BalanceException extends Exception{
	
	public BalanceException() {
		super( "You are trying to Overdraft! Unfortunetly this is not allowed. =[");
	}
}
