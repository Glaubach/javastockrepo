package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * A class to throw an exception when user wants to sell more than stockQuantity
 * @author Glaubach
 */
@SuppressWarnings("serial")
public class NotEnoughStocksException extends PortfolioException{
	/**
	 * c'tor to print the correct message to user 
	 */
	public NotEnoughStocksException(int amount){
		super("You do not own " +amount+ " Shares of this stock!");
	}
}
