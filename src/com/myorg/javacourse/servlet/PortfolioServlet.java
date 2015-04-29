package com.myorg.javacourse.servlet;

import java.io.IOException;

import com.myorg.javacourse.service.PortfolioManager;
import com.myorg.javacourse.model.Portfolio;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortfolioServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		
		PortfolioManager portfolioManager = new PortfolioManager();
		Portfolio portfolio = portfolioManager.getPortfolio();
		resp.getWriter().println(portfolio.getHtmlString());
	}
}
