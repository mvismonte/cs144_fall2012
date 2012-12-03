package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.Utilities;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      //if (Utilities.toHTTPifSecure(request, response)) {
      //  return;
      //}
      String itemId = request.getParameter("itemId");
      String pageBody = AuctionSearchClient.getXMLDataForItemId(itemId);
      PrintWriter out = response.getWriter();
      out.println(pageBody);
      out.close();
      response.setContentType("text/xml");
    }
}
