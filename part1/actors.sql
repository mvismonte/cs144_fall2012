# Selects database TEST.
USE TEST;

# Creates a table called Actors with 4 entries: Name, Movie, Year, and Role.
CREATE TABLE Actors(Name varchar(40), Movie varchar(80), Year int, Role varchar(40));

# Loads data from actors.csv file into the table Actors.
LOAD DATA LOCAL INFILE '~/ebay-data/actors.csv' INTO TABLE Actors
FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '"';

# Query that returns the name of actors from the movie "Die Another Day".
SELECT Name FROM Actors WHERE Movie='Die Another Day'; 

# Drops the table Actors from the database.
DROP TABLE Actors;
