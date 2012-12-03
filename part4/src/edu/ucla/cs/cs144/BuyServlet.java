package edu.ucla.cs.cs144;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.Utilities;

public class BuyServlet extends HttpServlet implements Servlet {
       
    public BuyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      if (Utilities.toHTTPifNotSecure(request, response)) {
        return;
      }
      // request.setAttribute("url", request.getRequestURI());
      request.getRequestDispatcher("/buy_now.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    }
}
