package com.myorg.javacourse.model;
import com.myorg.javacourse.Stock;

public class Portfolio {
	
	private static final int MAX_PORTFOLIO_SIZE = 5;
	
	private String title;
	private int portfolioSize = 0;
	private Stock[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Stock[] getStocks() {
		return stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = stocks;
	}
	
	public void addStock(Stock stock) {
		
		if(stock != null && portfolioSize < MAX_PORTFOLIO_SIZE) {
			this.stocks[portfolioSize] = stock;
			portfolioSize++;
		}else {
			System.out.println("The Portfolio is full, or the stock is null");
		}
	}
	public String getHtmlString() {
		
		String res = new String( "<h1>" + getTitle() + "</h1>" );
		
		for(int i = 0; i < portfolioSize ;i++) {
			res += this.stocks[i].getHtmlDescription() + "<br>";
		}
		return res;
	}
}
