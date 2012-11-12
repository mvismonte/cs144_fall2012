package edu.ucla.cs.cs144;

import java.util.Calendar;
import java.util.Date;

import edu.ucla.cs.cs144.AuctionSearch;
import edu.ucla.cs.cs144.SearchResult;
import edu.ucla.cs.cs144.SearchConstraint;
import edu.ucla.cs.cs144.FieldName;

public class AuctionSearchTest {
	public static void main(String[] args1)
	{
		AuctionSearch as = new AuctionSearch();

		String message = "Test message";
		String reply = as.echo(message);
		System.out.println("Reply: " + reply);
		
		String query = "superman";
		SearchResult[] basicResults = as.basicSearch(query, 0, 20);
		System.out.println("Basic Search Query: " + query);
		System.out.println("Received " + basicResults.length + " results");
		for(SearchResult result : basicResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}
		
		SearchConstraint constraint =
		    new SearchConstraint(FieldName.BuyPrice, "5.99"); 
		SearchConstraint[] constraints = {constraint};
		SearchResult[] advancedResults = as.advancedSearch(constraints, 0, 20);
		System.out.println("Advanced Search 1");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchConstraint[] constraints2 = {
			new SearchConstraint(FieldName.ItemName, "pan"),
			new SearchConstraint(FieldName.Category, "kitchenware")
		};
		advancedResults = as.advancedSearch(constraints2, 0, 20);
		System.out.println("Advanced Search 2");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		// temName:"Precious Moments", SellerId:waltera317a (2)
		SearchConstraint[] constraints3 = {
			new SearchConstraint(FieldName.ItemName, "Precious Moments"),
			new SearchConstraint(FieldName.SellerId, "waltera317a")
		};
		advancedResults = as.advancedSearch(constraints3, 0, 20);
		System.out.println("Advanced Search 3");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchConstraint[] constraints4 = {
			new SearchConstraint(FieldName.EndTime, "Dec-14-01 21:00:05"),
		};
		advancedResults = as.advancedSearch(constraints4, 0, 20);
		System.out.println("Advanced Search 4");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		String itemId = "1497595357";
		String item = as.getXMLDataForItemId(itemId);
		System.out.println("XML data for ItemId: " + itemId);
		//System.out.println(item);

		// Add your own test here
		SearchConstraint[] constraints5 = {
			new SearchConstraint(FieldName.ItemName, "Poppins"),
			new SearchConstraint(FieldName.Category, "Movie")
		};
		advancedResults = as.advancedSearch(constraints5, 0, 20);
		System.out.println("Advanced Search 5");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchConstraint[] constraints6 = {
			new SearchConstraint(FieldName.SellerId, "ddkingolin"),
			new SearchConstraint(FieldName.Description, "Tucker")
		};
		advancedResults = as.advancedSearch(constraints6, 0, 20);
		System.out.println("Advanced Search 6");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchConstraint[] constraints7 = {
			new SearchConstraint(FieldName.BuyPrice, "46.49"),
			new SearchConstraint(FieldName.EndTime, "Dec-18-01 21:16:38")
		};
		advancedResults = as.advancedSearch(constraints7, 0, 20);
		System.out.println("Advanced Search 7");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}

		SearchConstraint[] constraints8 = {
			new SearchConstraint(FieldName.BidderId, "zippershark")
		};
		advancedResults = as.advancedSearch(constraints8, 0, 20);
		System.out.println("Advanced Search 8");
		System.out.println("Received " + advancedResults.length + " results");
		for(SearchResult result : advancedResults) {
			System.out.println(result.getItemId() + ": " + result.getName());
		}
	}
}
