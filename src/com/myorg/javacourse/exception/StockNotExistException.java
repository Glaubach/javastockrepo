package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * A class to throw an exception when user wants to sell/remove a stock that isn't in the portfolio
 * @author Glaubach
 */
@SuppressWarnings("serial")
public class StockNotExistException extends PortfolioException{
	/**
	 * c'tor to print the correct message to user 
	 */
	public StockNotExistException(String symbol) {
		super("Stock " + symbol + " doesn't exists in the portfolio!");
	}
}
