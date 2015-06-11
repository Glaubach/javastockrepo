package com.myorg.javacourse.exception;

@SuppressWarnings("serial")
public class NotEnoughStocksException extends Exception{

	public NotEnoughStocksException(int amount){
		super("You do not own " +amount+ " Shares of this stock!");
	}
}
