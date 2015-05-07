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
	public Portfolio getPortfolio() {
		Portfolio portfolio = new Portfolio("Portfolio #1");
		
		Date date1 = new Date();
		date1.setYear(114);
		date1.setMonth(11);
		date1.setDate(15);
		
		Date date2 = new Date();
		date2.setYear(114);
		date2.setMonth(11);
		date2.setDate(15);
		
		Date date3 = new Date();
		date3.setYear(114);
		date3.setMonth(11);
		date3.setDate(15);
		
		Stock stock = new Stock("PIH", 13.1f, 12.4f, date1);
		portfolio.addStock(stock);
		stock = new Stock("AAL", 5.78f, 5.5f, date2);
		portfolio.addStock(stock);
		stock = new Stock("CAAS", 32.2f, 31.5f, date3);
		portfolio.addStock(stock);
		
		return portfolio;
	}
	
	public Portfolio getPortfolio2() {
		Portfolio portfolio2 = new Portfolio(getPortfolio());
		portfolio2.setTitle("Portfolio #2");
		
		return portfolio2;
	}
}
