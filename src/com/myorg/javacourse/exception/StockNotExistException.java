package com.myorg.javacourse.exception;

@SuppressWarnings("serial")
public class StockNotExistException extends Exception{
	
	public StockNotExistException(String symbol) {
		super("Stock " + symbol + " doesn't exists in the portfolio!");
	}
}
