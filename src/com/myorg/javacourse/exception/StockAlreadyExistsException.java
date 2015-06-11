package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * A class to throw an exception when user wants to buy a stock already in portfolio
 * @author Glaubach
 */
@SuppressWarnings("serial")
public class StockAlreadyExistsException extends PortfolioException{
	/**
	 * c'tor to print the correct message to user 
	 */
	public StockAlreadyExistsException(String symbol) {
		super("Stock " + symbol + " already exists!");
	}
}
