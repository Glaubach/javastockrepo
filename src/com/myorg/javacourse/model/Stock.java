package com.myorg.javacourse.model;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;

import org.algo.model.StockInterface;

import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMENDATION;
/**
 * This class hold all stock parameters and prints the stock 
 * @param symbol           stock name
 * @param ask              stock buy price
 * @param bid              stock sell price
 * @param date             date of buy/sell
 * @param recommendation   according to static final constant parameters
 * @param stockQuantity    ammount of stock shares
 */
public class Stock implements StockInterface{
	
	private String symbol;
	private Float ask;
	private Float bid;
	private Date date;
	private ALGO_RECOMMENDATION recommendation;
	private int stockQuantity;
	
	//private transient DateFormat df = new SimpleDateFormat();
	
	public Stock(){}
	/**
	 *constructor for Stock class 
	 */
	public Stock(String string, float f, float g, Date date) {
		this.symbol = string;
		this.ask = f;
		this.bid = g;
		this.date = date;
	}
	/**
	 * copy constructor for stock class 
	 */
	public Stock(Stock stock) {
		this.symbol = stock.symbol;
		this.ask = stock.ask;
		this.bid = stock.bid;
		this.date = new Date(stock.date.getTime());
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public float getAsk() {
		return ask;
	}
	public void setAsk(Float ask) {
		this.ask = ask;
	}
	public float getBid() {
		return bid;
	}
	public void setBid(Float bid) {
		this.bid = bid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Method that returns stock's details
	 * @return ret  string of stock details
	 */
	@SuppressWarnings("deprecation")
	public String getHtmlDescription() {
		String ret = "<b> Stock Symbol: </b>"+ getSymbol() + ",<b> Ask: </b>" + getAsk()+ ",<b> Bid: </b>" + getBid()+ ",<b> Date: </b>" + getDate().getMonth() + "/" + getDate().getDate() + "/" + (1900+ getDate().getYear() +"  " +getDate().getTime())  ;
		return ret;
	}
	public ALGO_RECOMMENDATION getRecommendation() {
		return recommendation;
	}
	public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
		this.recommendation = recommendation;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
}
