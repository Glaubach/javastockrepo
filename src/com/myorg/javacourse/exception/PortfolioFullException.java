package com.myorg.javacourse.exception;

@SuppressWarnings("serial")
public class PortfolioFullException extends Exception{
	
	public PortfolioFullException() {
		super("You have reached Max number of stocks");
	}
}
	
