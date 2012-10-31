package edu.ucla.cs.cs144;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;

public class Indexer {
  
  /** Creates a new instance of Indexer */
  public Indexer() {
  }
 
  public void rebuildIndexes() {

    Connection conn = null;

    // create a connection to the database to retrieve Items from MySQL
    try {
      conn = DbManager.getConnection(true);

      // Create an index writer.
      IndexWriter indexWriter = new IndexWriter(
        "index-directory",
        new StandardAnalyzer(),
        true
      );


      /*
       * Add your code here to retrieve Items using the connection
       * and add corresponding entries to your Lucene inverted indexes.
       *
       * You will have to use JDBC API to retrieve MySQL data from Java.
       * Read our tutorial on JDBC if you do not know how to use JDBC.
       *
       * You will also have to use Lucene IndexWriter and Document
       * classes to create an index and populate it with Items data.
       * Read our tutorial on Lucene as well if you don't know how.
       *
       * As part of this development, you may want to add 
       * new methods and create additional Java classes. 
       * If you create new classes, make sure that
       * the classes become part of "edu.ucla.cs.cs144" package
       * and place your class source files at src/edu/ucla/cs/cs144/.
      **/
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Item");

      int itemID;
      String name, description;

      // TODO: Index each item.  Need to add item and name field.
      // Need to look over name, category and name fields.
      int i = 0;
      while (rs.next()) {
        itemID = rs.getInt("itemID");
        name = rs.getString("Name");

        // Fetch the category names also!

        System.out.println(itemID + " " + name);
        if (i++ > 10) {
          break;
        }
      }


        indexWriter.close();
        // Close the database connection.
        conn.close();
    } catch (SQLException ex) {
      System.out.println(ex);
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  public static void main(String agrs[]) {
    Indexer idx = new Indexer();
    idx.rebuildIndexes();
  }   
}
