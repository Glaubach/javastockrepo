package com.myorg.javacourse.servlet;

import java.io.IOException;

import com.myorg.javacourse.service.PortfolioManager;

import org.algo.service.ServiceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
/**
 * @author Glaubach
 *	This is a servlet class that initializes a portfolio
 */
public class InitServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");	
	}
	
	public void init() throws ServletException {
		PortfolioManager pm = new PortfolioManager();
		ServiceManager.setPortfolioManager(pm); 
	}
}

