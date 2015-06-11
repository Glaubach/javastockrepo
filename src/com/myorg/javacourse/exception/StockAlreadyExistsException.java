package com.myorg.javacourse.exception;

@SuppressWarnings("serial")
public class StockAlreadyExistsException extends Exception{
	
	public StockAlreadyExistsException(String symbol) {
		super("Stock " + symbol + " already exists!");
	}
}
