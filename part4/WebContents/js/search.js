// This is a simple *viewmodel* - JavaScript that defines the data and behavior of your UI
/*SearchViewModel = function() {
    this.firstName = "Bert";
}

// Activates knockout.js
ko.applyBindings(new AppViewModel());*/

/*
$('.itemDetail').click(function(ev) {
  // console.log('hi');
  console.log(window.location.hash);
  itemID = window.location.hash.slice(1, window.location.hash.length);
  $.get('/eBay/item?itemId=' + itemID, function(data) {
    a = $(data);
    console.log(data);
  });
});*/

Bid = function(xmlDocument) {
  var xmlDoc = $(xmlDocument);

  // Bidder
  var userObj = User(xmlDoc.find('Bidder'));

  // Time
  var time = xmlDoc.find('Time').text();

  // Amount
  var amount = xmlDoc.find('Amount').text();

}

User = function(xmlDocument) {
  var xmlDoc = $(xmlDocument);

  // UserID
  var userId = xmlDoc.attr('UserID');

  // Rating
  var rating = xmlDoc.attr('Rating');

  // Location
  var location = xmlDoc.find('Location').text();

  // Country
  var country = xmlDoc.find('Country').text();
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
  var name = xmlDoc.find('Name').text();

  // Categories
  var categories = xmlDoc.find('Category');
  var categoryArray = new Array();
  for (var i = 0; i < categories.length; i++) {
    categoryArray.push($(categories[i]).text());
  }

  // Currently
  var currently = xmlDoc.find('Currently').text();

  // Buy_Price
  var buy_price = xmlDoc.find('Buy_Price').text();

  // First_Bid
  var first_bid = xmlDoc.find('First_Bid').text();

  // Number_of_Bids
  var num_of_bids = xmlDoc.find('Number_of_Bids').text();

  // Bids
  var bids = xmlDoc.find('Bids').find('Bid');
  var bidArray = new Array();
  
  for (var i = 0; i < bids.length; i++) {
    bidObj = new Bid(bid[i]);
    bidArray.push(bidObj);
  }

  // Location
  var location = xmlDoc.find('Location').text();

  // Country
  var country = xmlDoc.find('Country').text();

  // Started
  var started = xmlDoc.find('Started').text();

  // Ends
  var ends = xmlDoc.find('Ends').text();

  // Seller
  var seller = new User(xmlDoc.find("Seller"));

  // Description
  var description = xmlDoc.find('Description').text();
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
