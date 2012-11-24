// Copyright UCLA 2012
// CS 144 Fall 2012
// Author: Mark Vismonte
// Author: Logan Chang
// Date: 11/23/2012
// search.js

Bid = function(xmlDocument) {
  this.xmlDoc = $(xmlDocument);

  // Bidder
  this.userObj = new User(xmlDoc.find('Bidder'));

  // Time
  this.time = xmlDoc.find('Time').text();

  // Amount
  this.amount = xmlDoc.find('Amount').text();

}

User = function(xmlDocument) {
  this.xmlDoc = $(xmlDocument);

  // UserID
  this.userId = xmlDoc.attr('UserID');

  // Rating
  this.rating = xmlDoc.attr('Rating');

  // Location
  this.location = xmlDoc.find('Location').text();

  // Country
  this.country = xmlDoc.find('Country').text();
}


Item = function(xmlDocument) {
  // Set fields.
  // name
  // description
  // seller
  // bids <- array of bids.
  // etc.
  xmlDoc = $(xmlDocument);
  console.log(xmlDoc);
  console.log(xmlDoc.find('Name'));
  console.log(xmlDoc.find('Category'));

  // Name
  this.name = xmlDoc.find('Name').text();

  // Categories
  categories = xmlDoc.find('Category');
  this.categoryArray = new Array();
  for (var i = 0; i < categories.length; i++) {
    this.categoryArray.push($(categories[i]).text());
  }

  // Currently
  this.currently = xmlDoc.find('Currently').text();

  // Buy_Price
  this.buy_price = xmlDoc.find('Buy_Price').text();

  // First_Bid
  this.first_bid = xmlDoc.find('First_Bid').text();

  // Number_of_Bids
  this.num_of_bids = xmlDoc.find('Number_of_Bids').text();

  // Bids
  var bids = xmlDoc.find('Bids').find('Bid');
  this.bidArray = new Array();
  
  for (var i = 0; i < bids.length; i++) {
    bidObj = new Bid(bids[i]);
    this.bidArray.push(bidObj);
  }

  // Location
  this.location = xmlDoc.find('Location').text();

  // Country
  this.country = xmlDoc.find('Country').text();

  // Started
  this.started = xmlDoc.find('Started').text();

  // Ends
  this.ends = xmlDoc.find('Ends').text();

  // Seller
  this.seller = new User(xmlDoc.find("Seller"));

  // Description
  this.description = xmlDoc.find('Description').text();
}

SearchViewModel = function() {
  this.results = ko.observableArray([]);
  this.hasMoreResults = ko.observable(false);
  this.currentItem = ko.observable(null);
  this.RESULT_CHUNK = 10;
  this.current_index = ko.observable(0);
  this.query = "";
  var self = this;

  this.loadResults = function() {
    var data = {
      'q': self.query,
      'numResultsToSkip': self.current_index() * self.RESULT_CHUNK,
      'numResultsToReturn': self.RESULT_CHUNK
    };
    $.getJSON('/eBay/search', data, function(results) {
      self.results(results);
      self.hasMoreResults(results.length == self.RESULT_CHUNK);
    });
  };

  this.nextResults = function() {
    self.current_index(self.current_index() + 1);
    console.log('Next results');
    self.loadResults();
  }

  this.previousResults = function() {
    if (self.current_index() > 0) {
      self.current_index(self.current_index() - 1);
    }
    console.log('Prev results');
    self.loadResults();
  }

  this.selectItem = function(item) {
    console.log("Selected item!");
    console.log(item.itemId);

    // Don't actually do this.
    // Instead, make a call to get the XML by the item id.  Then create an item,
    // then set that item as the current item.
    var settings = {
      url: '/eBay/item',
      data: {
        'itemId': item.itemId
      },
      success: function(xmlData) {
        // Create xml data.
        console.log(xmlData);
        var itemObj = new Item(xmlData);
        self.currentItem(itemObj);
      }
    };
    $.ajax(settings);

    // Prevents default action.
    return false;
  };
}


var searchViewModel = new SearchViewModel();
searchViewModel.query = $.url().param('q');
searchViewModel.loadResults();
ko.applyBindings(searchViewModel, document.getElementById('search-body'));
