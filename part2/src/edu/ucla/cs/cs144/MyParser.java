/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire directory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
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
import org.xml.sax.ErrorHandler;

import au.com.bytecode.opencsv.CSVWriter;

class MyParser {
  
  static final String columnSeparator = "|*|";
  static DocumentBuilder builder;
  
  static final String[] typeName = {
 "none",
 "Element",
 "Attr",
 "Text",
 "CDATA",
 "EntityRef",
 "Entity",
 "ProcInstr",
 "Comment",
 "Document",
 "DocType",
 "DocFragment",
 "Notation",
  };
  
  static class MyErrorHandler implements ErrorHandler {
    
    public void warning(SAXParseException exception)
    throws SAXException {
      fatalError(exception);
    }
    
    public void error(SAXParseException exception)
    throws SAXException {
      fatalError(exception);
    }
    
    public void fatalError(SAXParseException exception)
    throws SAXException {
      exception.printStackTrace();
      System.out.println("There should be no errors " +
                "in the supplied XML files.");
      System.exit(3);
    }
    
  }
  
  /* Non-recursive (NR) version of Node.getElementsByTagName(...)
   */
  static Element[] getElementsByTagNameNR(Element e, String tagName) {
    Vector< Element > elements = new Vector< Element >();
    Node child = e.getFirstChild();
    while (child != null) {
      if (child instanceof Element && child.getNodeName().equals(tagName))
      {
        elements.add( (Element)child );
      }
      child = child.getNextSibling();
    }
    Element[] result = new Element[elements.size()];
    elements.copyInto(result);
    return result;
  }
  
  /* Returns the first subelement of e matching the given tagName, or
   * null if one does not exist. NR means Non-Recursive.
   */
  static Element getElementByTagNameNR(Element e, String tagName) {
    Node child = e.getFirstChild();
    while (child != null) {
      if (child instanceof Element && child.getNodeName().equals(tagName))
        return (Element) child;
      child = child.getNextSibling();
    }
    return null;
  }
  
  /* Returns the text associated with the given element (which must have
   * type #PCDATA) as child, or "" if it contains no text.
   */
  static String getElementText(Element e) {
    if (e.getChildNodes().getLength() == 1) {
      Text elementText = (Text) e.getFirstChild();
      return elementText.getNodeValue();
    }
    else
      return "";
  }
  
  /* Returns the text (#PCDATA) associated with the first subelement X
   * of e with the given tagName. If no such X exists or X contains no
   * text, "" is returned. NR means Non-Recursive.
   */
  static String getElementTextByTagNameNR(Element e, String tagName) {
    Element elem = getElementByTagNameNR(e, tagName);
    if (elem != null)
      return getElementText(elem);
    else
      return "";
  }
  
  /* Returns the amount (in XXXXX.xx format) denoted by a money-string
   * like $3,453.23. Returns the input if the input is an empty string.
   */
  static String strip(String money) {
    if (money.equals(""))
      return money;
    else {
      double am = 0.0;
      NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
      try { am = nf.parse(money).doubleValue(); }
      catch (ParseException e) {
        System.out.println("This method should work for all " +
                  "money values you find in our data.");
        System.exit(20);
      }
      nf.setGroupingUsed(false);
      return nf.format(am).substring(1);
    }
  }
  
  /* Process one items-???.xml file.
   */
  static void processFile(File xmlFile) {
    Document doc = null;
    try {
      doc = builder.parse(xmlFile);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(3);
    }
    catch (SAXException e) {
      System.out.println("Parsing error on file " + xmlFile);
      System.out.println("  (not supposed to happen with supplied XML files)");
      e.printStackTrace();
      System.exit(3);
    }
    
    /* At this point 'doc' contains a DOM representation of an 'Items' XML
     * file. Use doc.getDocumentElement() to get the root Element. */
    System.out.println("Successfully parsed - " + xmlFile);
    
    /* Fill in code here (you will probably need to write auxiliary
      methods). */

    // Process all of the elements.
    for (Element e: getElementsByTagNameNR(doc.getDocumentElement(), "Item")) {
      processItem(e);
    }

    try {
      // Write items.
      CSVWriter writer = new CSVWriter(new FileWriter("items.csv"), ',');
      for (String[] fields: items) {
        writer.writeNext(fields);
      }
      writer.close();
      // Write bids.
      writer = new CSVWriter(new FileWriter("bids.csv"), ',');
      for (String[] fields: bids) {
        writer.writeNext(fields);
      }
      writer.close();
      // Write users.
      writer = new CSVWriter(new FileWriter("users.csv"), ',');
      for (String[] fields: users.values()) {
        writer.writeNext(fields);
      }
      writer.close();
      // Write itemCategories.
      writer = new CSVWriter(new FileWriter("itemCategories.csv"), ',');
      for (String[] fields: itemCategories) {
        writer.writeNext(fields);
      }
      writer.close();
    } catch (IOException ex) {

    }
    /**************************************************************/
    
  }

  // Populate all of these, then iterate through them in the end and write a csv file.
  static HashSet<String[]> items;
  static HashSet<String[]> bids;
  static HashMap<String, String[]> users;
  static HashSet<String[]> itemCategories;

  // Get the text of an element with a certain tag in e.
  static String getTextFromElementTagName(Element e, String tag) {
    try {
      return getElementText(getElementByTagNameNR(e, tag));
    } catch (NullPointerException ex) {
      return "";
    }
  }

  // Get the text of an attribute with a certain tag in e.
  static String getAttributeText(Element e, String tag) {
    return e.getAttributes().getNamedItem(tag).getNodeValue();
  }

  static void addUser(String userID, String rating, String location, String country) {
    // Only add the user if he/she isn't in the map already.
    if (!users.containsKey(userID)) {
      String[] sellerFields = new String[4];
      sellerFields[0] = userID;
      sellerFields[1] = rating;
      sellerFields[2] = location;
      sellerFields[3] = country;

      // Actually add.
      users.put(userID, sellerFields);
    }
  }

  // Takes time String from xml and returns UNIX TIMESTAMP
  static String parseTime(String time) {

    String unix_time = "";
    int unix_time_int = 0;
    int month_int = 0;
    String month = time.substring(0, 3);
    String day = time.substring(4, 6);
    String year = time.substring(7, 9);
    String hour = time.substring(10, 12);
    String minute = time.substring(13, 15);
    String second = time.substring(16, 18);

    if (month.equals("Jan")) {
      month_int = 0;
    }  else if (month.equals("Feb")) {
      month_int = 1;
    }  else if (month.equals("Mar")) {
      month_int = 2;
    }  else if (month.equals("Apr")) {
      month_int = 3;
    }  else if (month.equals("May")) {
      month_int = 4;
    }  else if (month.equals("Jun")) {
      month_int = 5;
    }  else if (month.equals("Jul")) {
      month_int = 6;
    }  else if (month.equals("Aug")) {
      month_int = 7;
    }  else if (month.equals("Sep")) {
      month_int = 8;
    }  else if (month.equals("Oct")) {
      month_int = 9;
    }  else if (month.equals("Nov")) {
      month_int = 10;
    }  else if (month.equals("Dec")) {
      month_int = 11;
    } else {
      month_int = -1;
    }

    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, 2000 + Integer.parseInt(year));
    c.set(Calendar.MONTH, month_int);
    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
    c.set(Calendar.HOUR, Integer.parseInt(hour));
    c.set(Calendar.MINUTE, Integer.parseInt(minute));
    c.set(Calendar.SECOND, Integer.parseInt(second));
    c.set(Calendar.MILLISECOND, 0);

    unix_time_int = (int)(c.getTimeInMillis() / 1000L);

    unix_time = Integer.toString(unix_time_int);

    return unix_time;
  }


  static void processItem(Element e) {
    String itemID = getAttributeText(e, "ItemID");
    String started = getTextFromElementTagName(e, "Started");
    String ends = getTextFromElementTagName(e, "Ends");
    String location = getTextFromElementTagName(e, "Location");
    String country = getTextFromElementTagName(e, "Country");
    Element seller = getElementByTagNameNR(e, "Seller");
    String sellerUserID = getAttributeText(seller, "UserID");
    String sellerRating = getAttributeText(seller, "Rating");

    // There are 8 fields according to our schema.
    String[] fields = new String[9];

    // ItemID, Name, UserID, Currently, Buy_Price, First_Bid, Started, Ends,
    // Description.
    fields[0] = itemID;
    fields[1] = getTextFromElementTagName(e, "Name");
    fields[2] = sellerUserID;
    fields[3] = getTextFromElementTagName(e, "Currently");
    fields[4] = getTextFromElementTagName(e, "Buy_Price");
    fields[5] = strip(getTextFromElementTagName(e, "First_Bid"));
    fields[6] = parseTime(started);
    fields[7] = parseTime(ends);
    fields[8] = getTextFromElementTagName(e, "Description");

    //System.out.println(fields[0] + " " + fields[1] + " " + fields[5]);

    // System.out.println(getElementsByTagNameNR(getElementByTagNameNR(e, "Bids"), "Bid").length);
    for (Element bid: getElementsByTagNameNR(getElementByTagNameNR(e, "Bids"), "Bid")) {
      processBid(itemID, bid);
    }

    // Try adding the user.
    addUser(sellerUserID, sellerRating, location, country);

    // Push to the items HashSet here.
    items.add(fields);

    // Push All categories to the itemCategories HashSet.
    for (Element category: getElementsByTagNameNR(e, "Category")) {
      String[] itemCategoryFields = new String[2];
      itemCategoryFields[0] = itemID;
      itemCategoryFields[1] = getElementText(category);
      itemCategories.add(itemCategoryFields);
    }
  }

  static void processBid(String itemID, Element e) {
    Element bidder = getElementByTagNameNR(e, "Bidder");
    String userID = getAttributeText(bidder, "UserID");
    String rating = getAttributeText(bidder, "Rating");
    String location = getTextFromElementTagName(bidder, "Location");
    String country = getTextFromElementTagName(bidder, "Country");
    String time = getTextFromElementTagName(e, "Time");
    String amount = strip(getTextFromElementTagName(e, "Amount"));
    //System.out.println(userID + " " + rating + " " + amount);

    // Try adding the user to the map.
    addUser(userID, rating, location, country);

    // Push to the items HashSet here.
    String[] fields = new String[4];
    fields[0] = itemID;
    fields[1] = userID;
    fields[2] = parseTime(time);
    fields[3] = amount;

    // Add to bids!
    bids.add(fields);
  }
  
  public static void main (String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: java MyParser [file] [file] ...");
      System.exit(1);
    }
    
    /* Initialize parser. */
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(false);
      factory.setIgnoringElementContentWhitespace(true);      
      builder = factory.newDocumentBuilder();
      builder.setErrorHandler(new MyErrorHandler());
    }
    catch (FactoryConfigurationError e) {
      System.out.println("unable to get a document builder factory");
      System.exit(2);
    } 
    catch (ParserConfigurationException e) {
      System.out.println("parser was unable to be configured");
      System.exit(2);
    }
    
    // Initialize all of these to empty.
    items = new HashSet<String[]>();
    bids = new HashSet<String[]>();
    users = new HashMap<String, String[]>();
    itemCategories = new HashSet<String[]>();
    
    /* Process all files listed on command line. */
    for (int i = 0; i < args.length; i++) {
      File currentFile = new File(args[i]);
      processFile(currentFile);
    }
  }
}
