package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      // id: 1043374545
    //  AuctionSearchClient.getXMLDataForItemId("1043374545");
      String itemId = request.getParameter("itemId");
      String pageTitle = "My Page Title";
      //String pageBody = AuctionSearchClient.getXMLDataForItemId("1043374545");
      //String pageBody = AuctionSearchClient.getXMLDataForItemId(itemId);
      //String pageBody = "THIS IS THE BODY.";
      String pageBody = AuctionSearchClient.echo("HI");
      request.setAttribute("title", pageTitle);
      request.setAttribute("body", pageBody);
      request.getRequestDispatcher("/item.jsp").forward(request, response);
    }
}
