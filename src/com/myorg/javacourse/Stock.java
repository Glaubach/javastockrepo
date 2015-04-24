package com.myorg.javacourse;

import java.util.Date;

public class Stock {
	private String symbol;
	private Float ask;
	private Float bid;
	private Date date;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Float getAsk() {
		return ask;
	}
	public void setAsk(Float ask) {
		this.ask = ask;
	}
	public Float getBid() {
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
	
	public String getHtmlDescription() {
		String ret = "<b> Stock Symbol: </b>"+ getSymbol() + ",<b> Ask: </b>" + getAsk()+ ",<b> Bid: </b>" + getBid()+ ",<b> Date: </b>" + getDate().getMonth() + "/" + getDate().getDate() + "/" + (1900+ getDate().getYear())  ;
		return ret;
	}

}