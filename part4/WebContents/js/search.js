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
