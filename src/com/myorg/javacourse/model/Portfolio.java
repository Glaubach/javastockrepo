package com.myorg.javacourse.model;
/**
 * Class to hold and manage portfolio information
 * This class has methods to change portfolio details and stocks
 * and to print the array of all portfolios stocks
 */
public class Portfolio {
	private static final int MAX_PORTFOLIO_SIZE = 5;
	
	private String title;
	private int portfolioSize = 0;
	private Stock[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	/**
	 * Portfolio C'tor
	 * @param title  name of portfolio
	 * @param stocks an array of stocks
	 */
	public Portfolio(String title) {
		this.title = title;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
	}
	/**
	 * Copy of Portfolio C'tor 
	 * @param portfolio object of class Portfolio
	 * @param copied    a temp copy array of portfolio.stock array
	 */
	public Portfolio(Portfolio portfolio) {
		this(portfolio.getTitle());
		Stock[] copied = portfolio.getStocks();
		for (int i = 0; i < copied.length; i++) {
			if (portfolio.stocks[i] != null)
				this.addStock(new Stock(copied[i]));
		}	
	}
	/**
	 * Method to add a new stock in an array of stocks
	 * @param stock   object of class Stock  hold all stocks details
	 * @param portfolioSize  index to show where to put new stock in the array
	 */
	public void addStock(Stock stock) {
		if(stock != null && portfolioSize < MAX_PORTFOLIO_SIZE) {
			this.stocks[portfolioSize] = stock;
			portfolioSize++;
		}else {
			System.out.println("The Portfolio is full, or the stock is null");
		}
	}
	/**
	 * Method to remove a stock from array
	 * @param index place of stock in the array
	 */
	public void removeStock(int index) {
		this.stocks[index] = null;
	}
	/**
	 * Method that changes the Bid price of a stock
	 * @param index  location of stock in array
	 * @param x      desired new value of bid price
	 */
	public void updateBid(int index, float x){
		stocks[index].setBid(x);
	}
	/**
	 * Method that returns a string of all stocks in stock array
	 * @return  res  string of stocks
	 */
	public String getHtmlString() {
		String res = new String( "<h1>" + getTitle() + "</h1>" );
		
		for(int i = 0; i < portfolioSize ;i++) {
			if (this.stocks[i]!= null)
			    res += this.stocks[i].getHtmlDescription() + "<br>";
		}
		return res;
	}
	
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
}
