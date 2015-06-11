package com.myorg.javacourse.model;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.NotEnoughStocksException;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistsException;
import com.myorg.javacourse.exception.StockNotExistException;

/**
 * Class to hold and manage portfolio information
 * This class has methods to change portfolio details and stocks
 * and to print the array of all portfolios stocks
 */
public class Portfolio implements PortfolioInterface {
	private static final int MAX_PORTFOLIO_SIZE = 5;
	
	private String title;
	private int portfolioSize = 0;
	private float balance;
	private StockInterface[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	
	public enum ALGO_RECOMMENDATION {
		BUY, SELL, REMOVE, HOLD
	}
	
	/**
	 * Portfolio C'tor
	 * @param title  name of portfolio
	 * @param stocks an array of stocks
	 */
	public Portfolio() {}

	/**
	 * Portfolio C'tor to retrieve info from online portfolio
	 * @param stockArray    Array of stocks of Datastore
	 * @param size          Number of stocks in the array
	 */
	public Portfolio(Stock[] stockArray, int size) {
		for (int i = 0; i < size; i++) {
			this.stocks[i] = stockArray[i];
		}
		portfolioSize = size;
	}
	
	/**
	 * This method updates the portfolio balance available for trading 
	 * @param amount  The amount of funds to withdraw from balance
	 * @throws BalanceException
	 */
	public void updateBalance(float amount) throws BalanceException{
		if(amount >= 0)
			balance += amount;
		else{
			if((amount*(-1)) > balance)
				throw new BalanceException();
			else
				balance += amount;
		}
	}
	/**
	 * Method to add a new stock in an array of stocks
	 * @param stock   object of class Stock  hold all stocks details
	 * @param portfolioSize  index to show where to put new stock in the array
	 * @throws StockAlreadyExistsException
	 * @throws PortfolioFullException
	 */
	public void addStock(Stock stock) throws StockAlreadyExistsException, PortfolioFullException{
		if ((findStockPlace(stock.getSymbol())) != -2)
			throw new StockAlreadyExistsException(stock.getSymbol());
		
		if(stock != null && portfolioSize < MAX_PORTFOLIO_SIZE) {
			if ((findStockPlace(stock.getSymbol())) == -2){
				this.stocks[portfolioSize] = stock;
				portfolioSize++;
			}		
		}
		else 
			throw new PortfolioFullException();
	}
	
	/**
	 * This method checks if the stock already is in the portfolio and if does returns it's location
	 */
	public int findStockPlace(String symbol){
		int i;
		for(i = 0; i < portfolioSize ;i++){
			if( this.stocks[i].getSymbol().equals(symbol))
				return i;
		}	
		return -2;
	}

	/** 
	This method removes a stock from the portfolio
	 * @param  symbol     stock's symbol
	 * @throws NotEnoughStocksException 
	 * @throws BalanceException 
	 * @throws StockNotExistException
	 */
	public void removeStock(String symbol) throws StockNotExistException, NotEnoughStocksException, BalanceException{
		int index = findStockPlace(symbol);
		if(index == -2)
			throw new StockNotExistException(symbol);
		
		if(((Stock) this.stocks[index]).getStockQuantity() > 0  )	{
			this.sellStock(symbol, -1);
			this.stocks[index] = null;
		}
		else
			this.stocks[index] = null;
	}

	/**
	 * This method sell's shares of a stock without deleting it
	 * @param  symbol    Stock's symbol
	 * @param  quantity  Shares to sell
	 * @throws BalanceException 
	 * @throws StockNotExistException
	 * @throws NotEnoughStocksException
	 */
	public void sellStock(String symbol, int quantity) throws BalanceException, StockNotExistException, NotEnoughStocksException{
		int index = findStockPlace(symbol);
		int temp;
		
		if(index == -2){
			throw new StockNotExistException(symbol);
		}	
		if(quantity == -1){
			updateBalance(((Stock) this.stocks[index]).getStockQuantity()*this.stocks[index].getBid());
			((Stock) this.stocks[index]).setStockQuantity(0);
		}
		else{
			if(quantity <= ((Stock) this.stocks[index]).getStockQuantity()){
				updateBalance(quantity*this.stocks[index].getBid());
				temp = ((Stock) this.stocks[index]).getStockQuantity();
				temp = temp - quantity;
				((Stock) this.stocks[index]).setStockQuantity(temp);
			}
			else{
				throw new NotEnoughStocksException(quantity);
			}
		}
	}

	/**
	 * This method buy's new shares of a stock to the portfolio
	 * @param  stock      The Stock to be bought
	 * @param  quantity   Amount of shares of the stock
	 * @throws BalanceException
	 * @throws StockNotExistException
	 * @throws NotEnoughStocksException
	 */
	public void buyStock(Stock stock, int quantity) throws BalanceException, StockNotExistException, NotEnoughStocksException{
		int sharesToBuy = 0;
		int index = findStockPlace(stock.getSymbol());
		
		if(index == -2){
			throw new StockNotExistException(stock.getSymbol());
		}
		
		if(quantity == -1)
		{
			sharesToBuy = (int) (balance/stock.getAsk());
			updateBalance(-(sharesToBuy*stock.getAsk()));
			((Stock) this.stocks[index]).setStockQuantity(((Stock) this.stocks[index]).getStockQuantity()+sharesToBuy);	
		}
		else {
			if((quantity*stock.getAsk() <= balance)){
				updateBalance(-(quantity*stock.getAsk()));
				((Stock) this.stocks[index]).setStockQuantity(((Stock) this.stocks[index]).getStockQuantity()+quantity);	
			}
			else{
				throw new BalanceException();
			}
		}
	}
	
	/**
	 * This method returns the value of all shares of stocks in the portfolio 
	 * @return   res   value of the portfolio
	 */
	public float getStockValue(){
		float res = 0;
		for(int i = 0; i < portfolioSize ;i++) {
			res = res + ( this.stocks[i].getBid()*((Stock) this.stocks[i]).getStockQuantity() );
		}
		return res;
	}

	/**
	 * This method returns the amount of money left in portfolio account to buy stock with
	 * @return  balance  value of potfolio account
	 */
	public float getBalance() { 
		return balance;
	}
	
	/**
	 * This methods returns the Total amount of capital in portfolio
	 * @return  res  value of stocks + portfolios balance
	 */
	public float getTotalValue(){
		float res = getBalance()+ getStockValue();
		return res;
	}
	
	/**
	 * This method is used to find the stocks position in the portfolio array
	 * @param   symbol            stock to find
	 * @return	StockInterface    the stock with the symbol
	 */
	public StockInterface findStock(String symbol) {
		for(int i = 0; i < portfolioSize ;i++){
			if( this.stocks[i].getSymbol().equals(symbol)){
				StockInterface stocks = this.stocks[i];
				return stocks;
			}	
		}
		return null;
	}
	
	/** 
	 * Method that returns a string of all stocks in stock array
	 * @return  res  string of stocks
	 */
	public String getHtmlString() {
		String res = new String( "<h1>" + getTitle() + "</h1>" );
		res += ( "Total Portfolio Value: "  +getTotalValue() + "$<br> Total Stocks value: " +getStockValue()+ "$<br>Balance: " +getBalance()+ "$<p>");
		for(int i = 0; i < portfolioSize ;i++) {
			if (this.stocks[i]!= null)
			    res += ((Stock) this.stocks[i]).getHtmlDescription() + "<br>";
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
		return (Stock[]) stocks;
	}
	public void setStocks(Stock[] stocks) {
		this.stocks = (StockInterface[]) stocks;
	}
	public static int getMaxSize() {
		return MAX_PORTFOLIO_SIZE;
	}
}
