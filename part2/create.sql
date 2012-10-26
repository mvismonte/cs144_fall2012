# Selects database CS144
USE CS144;

# Creates a table called Items all the external data.
CREATE TABLE Items(ItemID int NOT NULL PRIMARY KEY, Name varchar(40), 
	Currently int, Buy_Price int, First_Bid int, Number_of_Bids int, 
	Started timestamp, Ends timestamp, Description varchar(4000));

# Creates a table called Bids that stores ItemID, UserID, time, and amount.
CREATE TABLE Bids(ItemID int, UserID varchar(40), Time timestamp, Amount int);

# Creates a table called Users that stores UserID, Rating, location, and country.
CREATE TABLE Users(UserID varchar(40) NOT NULL PRIMARY KEY, Rating int, 
	Location varchar(80), country varchar(40));

# Creates a table called categories that stores ItemID and category.
CREATE TABLE Categories(ItemID int, Category varchar(40));