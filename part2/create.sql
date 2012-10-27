# Selects database CS144
USE CS144;

# Creates a table called Item that will store all of the data related to an
# auctioned item.
CREATE TABLE Item (
  ItemID int NOT NULL,
  Name varchar(40), 
  SellerID varchar(40),
  Buy_Price int,
  First_Bid int,
	Started timestamp,
  Ends timestamp,
  Description varchar(4000),
  PRIMARY KEY (ItemID)
  #FOREIGN KEY (SellerID)
);

# Creates a table called Bid that keeps track of each bid that gets placed on
# an item.
CREATE TABLE Bid (
  BidID int NOT NULL AUTO_INCREMENT,
  ItemID int,
  UserID varchar(40),
  Time timestamp,
  Amount int,
  PRIMARY KEY (BidID)
  #FOREIGN KEY (ItemID, UserID)
);

# Creates a table called User that stores a User's information.
CREATE TABLE User (
  UserID varchar(40) NOT NULL,
  Rating int, 
	Location varchar(80),
  Country varchar(40),
  PRIMARY KEY (UserID)
);

# Creates a table called ItemCategory that is used for the many to many
# relationship between Items and categories.
CREATE TABLE ItemCategory (
  ItemID int,
  Category varchar(40)
  #FOREIGN KEY (ItemID)
);