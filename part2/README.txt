Part B:

1.) List your relations. Please specify all keys that hold on each relation. 
You need not specify attribute types at this stage.

//////////////////// Items Table ////////////////////

Item (
  ItemID #PRIMARY_KEY,
  Name, 
  Buy_Price,
  First_Bid,
  Started,
  Ends,
  Description,
)
    
    If you know the ItemID, you can get all the other fields.


//////////////////// Bids Table ////////////////////

Bid (
  BidID #KEY,
  ItemID,
  UserID
  Time,
  Amount,
)

  If you know the ItemID, you can get all of the bids on a certain item
  If you know the UserID, you can get all of the bids a user has entered.
  

//////////////////// Users Table ////////////////////

User (
  UserID #KEY,
  Rating,
  Location,
  Country
)

    If you know the UserID, you can get the Rating, Location, & Country.

//////////////////// Categories Table ////////////////////

Categories (
  ItemID,
  Category
) 

    Items can have multiple categories.
    Categories can have multiple items.
    The category is a string which is also the key for a category.
    Many-to-Many relation.


2.) List all completely nontrivial functional dependencies that hold on each
relation, excluding those that effectively specify keys.

ItemID --> Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Started, 
           Ends, Description
UserID --> Rating, Location, Country


// Am I missing any functional dependencies in the Many-to-Many Relations?


3.) Are all of your relations in Boyce-Codd Normal Form (BCNF)? If not, either
redesign them and start over, or explain why you feel it is advantageous to use
non-BCNF relations.

No, all the talbes are in BCNF. // I think. Not sure about Many-to-Many tables.

