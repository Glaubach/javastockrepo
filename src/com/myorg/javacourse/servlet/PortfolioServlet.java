package com.myorg.javacourse.servlet;

import java.io.IOException;

import com.myorg.javacourse.service.PortfolioManager;
import com.myorg.javacourse.model.Portfolio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * This class is a servlet that prints out porfolio names and the stocks
 * in every portfolio in HTTP
 * @param   portfolio    this is an object of class Portfolio that holds an array of stocks and a string 
 * @param   portfolio2   a copy of class portfolio 
 */
public class PortfolioServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PortfolioManager portfolioManager = new PortfolioManager();
		Portfolio myPortfolio = portfolioManager.getPortfolio();
		resp.getWriter().println(myPortfolio.getHtmlString());
	}
}
