# Loads data from <insertfilename> file into the table Items.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Items
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Bids.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Bids
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Users.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Users
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Loads data from <insertfilename> file into the table Categories.
LOAD DATA LOCAL INFILE '<insert filename>' INTO TABLE Categories
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';