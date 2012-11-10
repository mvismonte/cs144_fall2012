This example contains a simple utility class to simplify opening database
connections in Java applications, such as the one you will write to build
your Lucene index. 

To build and run the sample code, use the "run" ant target inside
the directory with build.xml by typing "ant run".

The MySQL indexes chosen are for the Bidder, Seller, Buy_Price, and Ending
Time. These were chosen for the advanced search which returns the itemId and
name of all the items matching the item name, category, seller, buy price, 
bidder, ending time, and description. The name, category, and description were
handled by the Lucene indexes, so it was not necessary to create MySQL indexes
for them.

We used the StandardAnalyze to process the text fields handled by Lucene.  We
found all the categories for each item when we processed it.