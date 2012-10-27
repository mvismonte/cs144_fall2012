# Loads data from <insertfilename> file into the table Item.
LOAD DATA LOCAL INFILE 'items.csv' INTO TABLE Item
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Users.
LOAD DATA LOCAL INFILE '<users.csv>' INTO TABLE User
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Bids.
LOAD DATA LOCAL INFILE 'bids.csv' INTO TABLE Bid
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Categories.
LOAD DATA LOCAL INFILE '<itemCategories.csv>' INTO TABLE ItemCategory
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';