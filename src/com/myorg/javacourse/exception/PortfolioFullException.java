package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * A class to throw an exception when user wants to add more stocks than MAX_PORTFOLIO_SIZE
 * @author Glaubach
 */
@SuppressWarnings("serial")
public class PortfolioFullException extends PortfolioException{
	/**
	 * c'tor to print the correct message to user 
	 */
	public PortfolioFullException() {
		super("You have reached Max number of stocks, sell/remove a stock before adding a new one.");
	}
}
	
