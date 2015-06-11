package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;
/**
 * A class to throw an exception when user wants to withdraw more than portfolio balance
 * @author Glaubach
 */
@SuppressWarnings("serial")
public class BalanceException extends PortfolioException{
	/**
	 * c'tor to print the correct message to user 
	 */
	public BalanceException() {
		super( "You are trying to Overdraft! Unfortunetly this is not allowed. =[");
	}
}
