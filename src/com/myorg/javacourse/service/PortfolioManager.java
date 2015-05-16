package com.myorg.javacourse.service;

import java.util.Date;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Stock;

/**
 * A class to manage all portfolios and their data
 */
public class PortfolioManager {
	
	/**
	 * This method builds a portfolio of stocks
	 * @param  Date       The date a stock was bought/sold
	 * @param  Stock      An object of class Stock that holds a single stock's info.
	 * @return Portfolio  The name of the portfolio and an array of all it's stocks
	 */
	@SuppressWarnings("deprecation")
	public Portfolio getPortfolio() {
		Portfolio myPortfolio = new Portfolio("Exercise 7 portfolio");
		myPortfolio.updateBalance(10000);
		
		Date date1 = new Date(114,11,15);
		Date date2 = new Date(114,11,15);
		Date date3 = new Date(114,11,15);
		
		Stock stock = new Stock("PIH", 10.0f, 8.5f, date1);
		myPortfolio.buyStock(stock, 20);
		stock = new Stock("AAL", 30.0f, 25.5f, date2);
		myPortfolio.buyStock(stock, 30);
		stock = new Stock("CAAS", 20.0f, 15.5f, date3);
		myPortfolio.buyStock(stock, 40);
		
		myPortfolio.sellStock("AAL",-1);
		myPortfolio.removeStock("CAAS");
		
		return myPortfolio;
	}
	
	/*
	public Portfolio getPortfolio2() {
		Portfolio portfolio2 = new Portfolio(getPortfolio());
		portfolio2.setTitle("Portfolio #2");
		
		return portfolio2;
	}*/
}
