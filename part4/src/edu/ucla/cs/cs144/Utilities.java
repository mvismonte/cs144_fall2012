package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utilities {

  public static boolean toHTTPifSecure(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    if (request.isSecure()) {
      String protocol = "http";
      String serverName = request.getServerName();
      String port = "8080";
      String path = request.getRequestURI();
      redirect(protocol, serverName, port, path, response);
      return true;
    }

    return false;
  }

  public static boolean toHTTPifNotSecure(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    if (!request.isSecure()) {
      String protocol = "https";
      String serverName = request.getServerName();
      String port = "8443";
      String path = request.getRequestURI();
      redirect(protocol, serverName, port, path, response);
      return true;
    }

    return false;
  }

  private static void redirect(String protocol, String serverName,
      String port, String path, HttpServletResponse response)
      throws ServletException, IOException {
    String url = protocol + "://" + serverName + ":" + port + path;
    response.sendRedirect(response.encodeRedirectURL(url));
  }
}