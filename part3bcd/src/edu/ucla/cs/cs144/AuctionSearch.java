package edu.ucla.cs.cs144;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

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
import org.apache.lucene.search.TopDocCollector;

import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.FieldName;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
<<<<<<< HEAD
import org.w3c.dom.Document;
import org.w3c.dom.Element;*/
=======
//import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import java.util.ArrayList;

>>>>>>> part3_logan

public class AuctionSearch implements IAuctionSearch {
  class Bid {
    private String userId;
    private int rating;
    private String location;
    private String country;
    private String time;
    private double amount;

    public Bid() {}

    public void setUserId(String userId) { this.userId = userId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setLocation(String location) { this.location = location; }
    public void setCountry(String country) { this.country = country; }
    public void setTime(String time) { this.time = time; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getUserId() { return userId; }
    public int getRating() { return rating; }
    public String getLocation() { return location; }
    public String getCountry() { return country; }
    public String getTime() { return time; }
    public double getAmount() { return amount; }
  }


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

  private SearchResult[] luceneSearch(String query, int numResultsToSkip, 
      int numResultsToReturn, String fieldToSearch) {
    try {
      IndexSearcher searcher = new IndexSearcher(
        System.getenv("LUCENE_INDEX") + "/index-directory"
      );

      QueryParser qp = new QueryParser(fieldToSearch, new StandardAnalyzer());
      qp.setDefaultOperator(QueryParser.Operator.OR);
      Query q = qp.parse(query);
      Hits hits = searcher.search(q);

      System.out.println("Hits: " + hits.length());
      for (int i = 0; i < hits.length(); i++) {
        Document doc = hits.doc(i);
        //System.out.println("HERE " + doc.get("ItemID") + ": " + doc.get("Name"));
      }

      int start = numResultsToSkip;
      int size = numResultsToReturn;
      if (numResultsToReturn == 0) {
        start = 0;
        size = hits.length();
      }

      SearchResult[] results = new SearchResult[size];
      for (int i = start; i < size; i++) {
        Document doc = hits.doc(i);
        results[i] = new SearchResult(doc.get("ItemID"), doc.get("Name"));
      }

      return results;
    } catch (CorruptIndexException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  private void dbSearch(String field, String table, String value, 
    HashSet<SearchResult> results) {

    Connection conn = null;
    try {
      conn = DbManager.getConnection(true);
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT ItemID, Name FROM " + table + 
        " WHERE " + field + "=" + value);
      while (rs.next()) {
        int id = rs.getInt("ItemID");
        String name = rs.getString("Name");
        results.add(new SearchResult(Integer.toString(id), name));
      }
    } catch (SQLException ex) {
      System.out.println(ex);
    }
  }

  public SearchResult[] basicSearch(String query, int numResultsToSkip, 
      int numResultsToReturn) {
    return luceneSearch(query, numResultsToSkip, 
      numResultsToReturn, "content");
  }

  public SearchResult[] advancedSearch(SearchConstraint[] constraints, 
      int numResultsToSkip, int numResultsToReturn) {

    ArrayList<HashSet<SearchResult>> resultSets = 
      new ArrayList<HashSet<SearchResult>>(constraints.length);

    for (int i = 0; i < constraints.length; i++) {
      SearchConstraint sc = constraints[i];
      String field = sc.getFieldName();
      String val = sc.getValue();
      HashSet<SearchResult> results = new HashSet<SearchResult>();

      // ItemName search.
      if (field == FieldName.ItemName) {
        for (SearchResult sr: luceneSearch(val, 0, 0, "Name")) {
          results.add(sr);
        }

      // Category search.
      } else if (field == FieldName.Category) {
        for (SearchResult sr: luceneSearch(val, 0, 0, "Categories")) {
          results.add(sr);
        }

      // SellerId search.
      } else if (field == FieldName.SellerId) {
        dbSearch("UserID", "Item", val, results);
      // BuyPrice search.
      } else if (field == FieldName.BuyPrice) {
        dbSearch("Buy_Price", "Item", val, results);
      // BidderId search.
      } else if (field == FieldName.BidderId) {
        // dbSearch("UserID", "Bid", val, results);
      // EndTime search.
      } else if (field == FieldName.EndTime) {
        dbSearch("Ends", "Item", val, results);
      // Description search.
      } else if (field == FieldName.Description) {
        for (SearchResult sr: luceneSearch(val, 0, 0, "Description")) {
          results.add(sr);
        }
      }

      resultSets.add(results);
    }

    if (constraints.length == 0) {
      return new SearchResult[0];
    }

    HashSet<SearchResult> results = resultSets.get(0);
    for (int i = 1; i < resultSets.size(); i++) {
      HashSet<SearchResult> currentResults = resultSets.get(i);
      if (currentResults != null) {
        results.retainAll(currentResults);
      }
    }

    return results.toArray(new SearchResult[0]);
  }

  public String getXMLDataForItemId(String itemId) {
    // TODO: Your code here!
    Connection conn = null;
    
    // Item table
    String name = null;
    String userId = null;
    double currently = 0;
    double buy_price = 0;
    double first_bid = 0;
    String started = null;
    String ends = null;
    String description = null;
    
    // Use userId to get this info
    String location = null;
    String country = null;
    int rating = 0;
    
    // Bids
    ArrayList<Bid> bidArray = new ArrayList<Bid>();
    int num_of_bids = 0;

    // Catagories
    ArrayList<String> categoryArray = new ArrayList<String>();

    try {
      // create a connection to the database to retrieve Items from MySQL
      conn = DbManager.getConnection(true);

      /*You can think of a JDBC Statement object as a channel
      sitting on a connection, and passing one or more of your
      SQL statements (which you ask it to execute) to the DBMS*/

      itemId = "1043374545"; // rig item for now

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Item WHERE ItemID='" + itemId + "'");

      while (rs.next()) {
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

      // Get location, country, rating of seller
      rs = stmt.executeQuery("SELECT * FROM User WHERE UserID='" + userId + "'");

      while (rs.next()) {
        location = rs.getString("Location");
        country = rs.getString("Country");
        rating = rs.getInt("Rating");
        System.out.println("UserID: " + userId +
                            "\nRating: " + rating + 
                            "\nLocation: " + location +
                            "\nCountry: " + country);
      }

      // Get Bids
      rs = stmt.executeQuery("SELECT * FROM Bid WHERE ItemID='" + itemId + "'");
      while (rs.next()) {
        Bid bid = new Bid();
        bid.setUserId(rs.getString("UserID"));
        bid.setTime(rs.getString("Time"));
        bid.setAmount(rs.getDouble("Amount"));

        bidArray.add(bid);
      }

      Iterator iterator = bidArray.iterator();

      while (iterator.hasNext()) {
        System.out.println("UserID: " + iterator.next().getUserId() +
                            "\nTime: " + iterator.next().getTime() + 
                            "\nAmount: " + iterator.next().getAmount());
      }

      /* Convert to XML */

    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
    // root element Items
    org.w3c.dom.Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("Items");
    doc.appendChild(rootElement);

    // Element node Item
    Element itemElement = doc.createElement("Item");
    itemElement.setAttribute("ItemID", itemId);
    rootElement.appendChild(itemElement);

    Element nameElement = doc.createElement("Name");
    nameElement.appendChild(doc.createTextNode(name));
    itemElement.appendChild(nameElement);

    Element currentlyElement = doc.createElement("Currently");
    currentlyElement.appendChild(doc.createTextNode(Double.toString(currently)));
    itemElement.appendChild(currentlyElement);

    Element buy_priceElement = doc.createElement("Buy_Price");
    buy_priceElement.appendChild(doc.createTextNode(Double.toString(buy_price)));
    itemElement.appendChild(buy_priceElement);

    Element first_bidElement = doc.createElement("First_Bid");
    first_bidElement.appendChild(doc.createTextNode(Double.toString(first_bid)));
    itemElement.appendChild(first_bidElement);

    Element startedElement = doc.createElement("Started");
    startedElement.appendChild(doc.createTextNode(started));
    itemElement.appendChild(startedElement);

    Element endsElement = doc.createElement("Ends");
    endsElement.appendChild(doc.createTextNode(ends));
    itemElement.appendChild(endsElement);

    Element descriptionElement = doc.createElement("Description");
    descriptionElement.appendChild(doc.createTextNode(description));
    itemElement.appendChild(descriptionElement);

    // set attribute for root
    //Attr itemId_attr = doc.createAttribute("ItemID");
    //itemId_attr.setValue(itemId);
/*
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
 */

    String xml_result = getStringFromDoc(doc);

    // write the content into xml file
    /*TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(xml_result);
 
    // Output to console for testing
    // StreamResult result = new StreamResult(System.out);
 
    transformer.transform(source, result);*/
 
    //System.out.println("File saved!");







      /* Close the resultset, statement and connection */
      rs.close();
      stmt.close();
      conn.close();
      //System.out.println(xml_result);
      return xml_result;

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
    } catch (ParserConfigurationException ex) {
      System.out.println(ex);
    //} catch (TransformerException ex) {
//      System.out.println(ex);
    }

    return name;
  }
  
  public String echo(String message) {
    return message;
  }

  public String getStringFromDoc(org.w3c.dom.Document doc)    {
    DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
    LSSerializer lsSerializer = domImplementation.createLSSerializer();
    return lsSerializer.writeToString(doc);   
  }
}
