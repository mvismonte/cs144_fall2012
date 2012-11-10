# Selects database CS144
USE CS144;

# Create Indexes for Bidder, Seller, Buy_Price, and Ending Time
CREATE INDEX BidderIndex ON Bid(UserID);
CREATE INDEX SellerIndex ON Item(UserID);
CREATE INDEX Buy_PIndex ON Item(Buy_Price);
CREATE INDEX EndsIndex ON Item(Ends);