package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.Utilities;

public class HomeServlet extends HttpServlet implements Servlet {
       
  public HomeServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (Utilities.toHTTPifSecure(request, response)) {
      return;
    }
    request.getRequestDispatcher("/home.jsp").forward(request, response);
  }
}
