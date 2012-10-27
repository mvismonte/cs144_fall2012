# Loads data from <insertfilename> file into the table Item.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Item
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Bids.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Bid
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Users.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE User
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Categories.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE ItemCategory
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';