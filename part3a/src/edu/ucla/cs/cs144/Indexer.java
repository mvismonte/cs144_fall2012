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
        System.getenv("LUCENE_INDEX") + "/index-directory",
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



      // TODO: Index each item.  Need to add item and name field.
      // Need to look over name, category and name fields.
      int i = 0;
      while (rs.next()) {
        int itemID;
        String name, categories, description, content;
        itemID = rs.getInt("ItemID");
        name = rs.getString("Name");
        description = rs.getString("Description");

        // Fetch the category names also!
        Statement stmt2 = conn.createStatement();
        ResultSet categoryResult = stmt2.executeQuery(
          "SELECT Category FROM ItemCategory WHERE ItemID = " + itemID
        );
        categories = "";
        while (categoryResult.next()) {
          categories += categoryResult.getString("Category") + " ";
        }

        //System.out.println(itemID + " " + name + ": " + categories /*+ "\n" + description*/);
        content = name + " " + categories + " " + description;

        Document doc = new Document();
        doc.add(new Field("ItemID", Integer.toString(itemID), Field.Store.YES, Field.Index.NO));
        doc.add(new Field("Name", name, Field.Store.YES, Field.Index.NO));
        doc.add(new Field("Categories", categories, Field.Store.YES, Field.Index.NO));
        // doc.add(new Field("Description", description, Field.Store.NO, Field.Index.NO));
        doc.add(new Field("content", content, Field.Store.NO, Field.Index.TOKENIZED));
        indexWriter.addDocument(doc);
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
