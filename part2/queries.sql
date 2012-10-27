# Find the number of users in the database.
SELECT COUNT(*) FROM User;

# Find the number of sellers from "New York", (i.e., sellers whose location is 
# exactly the string "New York"). Pay special attention to case sensitivity. 
# You should match the sellers from "New York" but not from "new york".
SELECT COUNT(*) FROM
  (SELECT COUNT(*)
  FROM User
  INNER JOIN Item
  ON User.UserID = Item.UserID
  WHERE BINARY Location = "New York"
  GROUP BY User.UserID) AS TMP;

# Find the number of auctions belonging to exactly four categories.
SELECT COUNT(*)
FROM ( SELECT ItemID, Count(DISTINCT Category)
       FROM ItemCategory
       GROUP BY ItemID
       HAVING Count(DISTINCT Category) = 4 ) AS ItemCat;

# Find the ID(s) of current (unsold) auction(s) with the highest bid.
# Remember that the data was captured at the point in time December 20th, 2001,
# one second after midnight, so you can use this time point to decide which 
# auction(s) are current. Pay special attention to the current auctions without
# any bid.
SELECT ItemID
FROM ( SELECT ItemID, MAX(Currently)
       FROM Item 
       GROUP BY ItemID) AS MaxItem
WHERE Item.Ends > UNIX_TIMESTAMP('2001-12-20 00:00:01');

# Find the number of sellers whose rating is higher than 1000.
SELECT COUNT(*) FROM
  (SELECT COUNT(*)
  FROM User
  INNER JOIN Item
  ON User.UserID = Item.UserID
  GROUP BY User.UserID, User.Rating
  HAVING User.Rating > 1000) AS TMP;

# Find the number of users who are both sellers and bidders.
SELECT COUNT(*) FROM 
  (SELECT COUNT(*)
  FROM Bid
  INNER JOIN Item
  ON Bid.UserID = Item.UserID
  GROUP BY Bid.UserID) AS TMP;

# Find the number of categories that include at least one item with a bid of 
# more than $100.
SELECT COUNT(DISTINCT Category)
FROM Bid
INNER JOIN ItemCategory
ON ItemCategory.ItemID = Bid.ItemID
GROUP BY ItemCategory.Category;
HAVING Bid.amount > 100;
