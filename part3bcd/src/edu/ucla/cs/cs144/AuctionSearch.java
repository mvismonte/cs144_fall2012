package edu.ucla.cs.cs144;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchConstraint;
import edu.ucla.cs.cs144.SearchResult;


/*
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;*/
/*
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
*/


public class AuctionSearch implements IAuctionSearch {

  /* 
   * You will probably have to use JDBC to access MySQL data
   * Lucene IndexSearcher class to lookup Lucene index.
   * Read the corresponding tutorial to learn about how to use these.
   *
   * Your code will need to reference the directory which contains your
   * Lucene index files.  Make sure to read the environment variable 
   * $LUCENE_INDEX with System.getenv() to build the appropriate path.
   *
   * You may create helper functions or classes to simplify writing these
   * methods. Make sure that your helper functions are not public,
   * so that they are not exposed to outside of this class.
   *
   * Any new classes that you create should be part of
   * edu.ucla.cs.cs144 package and their source files should be
   * placed at src/edu/ucla/cs/cs144.
   *
   */
  
  public SearchResult[] basicSearch(String query, int numResultsToSkip, 
      int numResultsToReturn) {
    // TODO: Your code here!
    SearchResult[] results = new SearchResult[numResultsToReturn];
    for (int i = 0; i < results.length; i++) {
      results[i] = new SearchResult("123", "hi");
    }

    try {
      IndexSearcher searcher = new IndexSearcher(
        System.getenv("LUCENE_INDEX") + "/index-directory"
      );
    } catch (CorruptIndexException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return results;
  }

  public SearchResult[] advancedSearch(SearchConstraint[] constraints, 
      int numResultsToSkip, int numResultsToReturn) {
    // TODO: Your code here!
    return new SearchResult[0];
  }

  public String getXMLDataForItemId(String itemId) {
    // TODO: Your code here!
    Connection conn = null;
    String name = "hi";
    String userId;
    Double currently;
    Double buy_price;
    Double first_bid;
    String started;
    String ends;
    String description; 

    try {
      // create a connection to the database to retrieve Items from MySQL
      conn = DbManager.getConnection(true);

      /*You can think of a JDBC Statement object as a channel
      sitting on a connection, and passing one or more of your
      SQL statements (which you ask it to execute) to the DBMS*/

      itemId = "1043374545";

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Item WHERE ItemID='" + itemId + "'");
/*
      ItemID int NOT NULL,
  Name varchar(40), 
  UserID varchar(40),
  Currently decimal(8,2),
  Buy_Price decimal(8,2),
  First_Bid decimal(8,2),
  Started timestamp,
  Ends timestamp,
  Description varchar(4000),
*/

      while (rs.next()) {
      //     bar = rs.getString("bar");
      //     beer = rs.getString("beer");
       //    price = rs.getFloat("price");
      //     System.out.println(bar + " sells " + beer + " for " + price + " dollars.");
        name = rs.getString("Name");
        userId = rs.getString("UserID");
        currently = rs.getDouble("Currently");
        buy_price = rs.getDouble("Buy_Price");
        first_bid = rs.getDouble("First_Bid");
        started = rs.getString("Started");
        ends = rs.getString("Ends");
        description = rs.getString("Description");
        System.out.println("ItemID: " + itemId +
                            "\nName: " + name + 
                            "\nUserID: " + userId +
                            "\nCurrently: " + currently +
                            "\nBuy_Price: " + buy_price +
                            "\nFirst_Bid: " + first_bid +
                            "\nStarted: " + started +
                            "\nEnds: " + ends +
                            "\nDesc: " + description);
      }

      /* Convert to XML */

 /*     DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
    // root elements
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("company");
    doc.appendChild(rootElement);
 
    // staff elements
    Element staff = doc.createElement("Staff");
    rootElement.appendChild(staff);
 
    // set attribute to staff element
    Attr attr = doc.createAttribute("id");
    attr.setValue("1");
    staff.setAttributeNode(attr);
 
    // shorten way
    // staff.setAttribute("id", "1");
 
    // firstname elements
    Element firstname = doc.createElement("firstname");
    firstname.appendChild(doc.createTextNode("yong"));
    staff.appendChild(firstname);
 
    // lastname elements
    Element lastname = doc.createElement("lastname");
    lastname.appendChild(doc.createTextNode("mook kim"));
    staff.appendChild(lastname);
 
    // nickname elements
    Element nickname = doc.createElement("nickname");
    nickname.appendChild(doc.createTextNode("mkyong"));
    staff.appendChild(nickname);
 
    // salary elements
    Element salary = doc.createElement("salary");
    salary.appendChild(doc.createTextNode("100000"));
    staff.appendChild(salary);
 
    // write the content into xml file
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File("C:\\file.xml"));
 
    // Output to console for testing
    // StreamResult result = new StreamResult(System.out);
 
    transformer.transform(source, result);
 
    System.out.println("File saved!");

*/





      /* Close the resultset, statement and connection */
      rs.close();
      stmt.close();
      conn.close();
//    } catch (ClassNotFoundException ex) {
      //System.out.println(ex);
    } catch (SQLException ex) {
      System.out.println("SQLException caught");
      System.out.println("---");
      while (ex != null) {
        System.out.println("Message   : " + ex.getMessage());
        System.out.println("SQLState  : " + ex.getSQLState());
        System.out.println("ErrorCode : " + ex.getErrorCode());
        System.out.println("---");
        ex = ex.getNextException();
      }
    //} catch (IOException ex) {
//      System.out.println(ex);
    }

    return name;
  }
  
  public String echo(String message) {
    return message;
  }

}
