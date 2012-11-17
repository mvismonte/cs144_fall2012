package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.AuctionSearchClient;
import edu.ucla.cs.cs144.SearchResult;

public class SearchServlet extends HttpServlet implements Servlet {
       
  public SearchServlet() {}

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String q = request.getParameter("q");
    int startIndex = 0;
    int numItems = 0;
    try {
      startIndex = Integer.parseInt(request.getParameter("numResultsToSkip"));
    } catch (java.lang.NumberFormatException e) {
      // This probably means it was null, so just default to the value 0.
    }
    try {
      numItems = Integer.parseInt(request.getParameter("numResultsToReturn"));
    } catch (java.lang.NumberFormatException e) {
      // This probably means it was null, so just default to the value 0.
    }
    SearchResult[] results = AuctionSearchClient.basicSearch(q,
        startIndex, numItems);
    request.setAttribute("results", results);
    request.setAttribute("hasResults", new Boolean(results.length > 0));
    request.getRequestDispatcher("/search.jsp").forward(request, response);
  }
}
