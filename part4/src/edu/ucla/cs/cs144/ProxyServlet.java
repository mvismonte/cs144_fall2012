package edu.ucla.cs.cs144;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.Utilities;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      //if (Utilities.toHTTPifSecure(request, response)) {
      //  return;
      //}
      String q = request.getParameter("q");
      if (q == null) {
        return;
      }

      URL url;
      InputStream is = null;
      DataInputStream dis;
      String line;

      try {
        url = new URL("http://www.google.com/complete/search?output=toolbar&q=" + URLEncoder.encode(q));
        is = url.openStream();  // throws an IOException
        dis = new DataInputStream(new BufferedInputStream(is));
        PrintWriter out = response.getWriter();

        while ((line = dis.readLine()) != null) {
          out.println(line);
        }
        out.close();
        response.setContentType("text/xml");
      } catch (MalformedURLException mue) {
        mue.printStackTrace();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      } finally {
        try {
          is.close();
        } catch (IOException ioe) {
            // nothing to see here
        }
      }
    }
}
