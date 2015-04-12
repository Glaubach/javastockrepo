package com.myorg.javacourse;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.Math;
@SuppressWarnings("serial")
public class Exercise_3 extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException
		{
			resp.setContentType("text/html");
			int radius = 50;
			int circle_area = (int) (java.lang.Math.PI*(java.lang.Math.pow(radius, 2)));
			resp.getWriter().println("Area of circle with radius: "+radius+"  is: "+circle_area+" square cm  || ");
			
			int angle = 30;
			int hypotenuse = 50;
			double opposite = java.lang.Math.sin(java.lang.Math.toRadians(angle))*hypotenuse;
			resp.getWriter().println("Length of opposite where angle B is 30 degrees and Hypotenuse length is 50 cm is: "+opposite+" cm  || ");
			int base = 20;
			int exp = 13;
			resp.getWriter().println("Power of "+base+" with exp of "+exp+" is: "+java.lang.Math.pow(base, exp)+"");	
		}
	
}	
