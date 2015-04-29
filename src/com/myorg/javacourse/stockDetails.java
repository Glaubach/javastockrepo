package com.myorg.javacourse;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class stockDetails extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		
		Date date1 = new Date();
		date1.setYear(114);
		date1.setMonth(11);
		date1.setDate(15);
		
		Date date2 = new Date();
		date2.setYear(2014-1900);
		date2.setMonth(11);
		date2.setDate(15);
		
		Date date3 = new Date();
		date3.setYear(2014-1900);
		date3.setMonth(11);
		date3.setDate(15);
		
		Stock stock1 = new Stock("PIH", 13.1f, 12.4f, date1);
		Stock stock2 = new Stock("AAL", 5.78f, 5.5f, date2);
		Stock stock3 = new Stock("CAAS", 32.2f, 31.5f, date3);
		
		resp.getWriter().println(stock1.getHtmlDescription());
		resp.getWriter().println("<br></br>");
		resp.getWriter().println(stock2.getHtmlDescription());
		resp.getWriter().println("<br></br>");
		resp.getWriter().println(stock3.getHtmlDescription());
	}
}
