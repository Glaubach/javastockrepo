package com.myorg.javacourse.service;

import java.util.Date;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.Stock;

public class PortfolioManager {
	
	public Portfolio getPortfolio() {
		Portfolio portfolio = new Portfolio();
		portfolio.setTitle("Stock Portfolio");
		
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

}
