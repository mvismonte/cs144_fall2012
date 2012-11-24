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
  this.hasMoreResults = false;
  this.currentItem = ko.observable(null);
  this.RESULT_CHUNK = 50;
  this.query = "";
  var self = this;

  this.addResults = function(results) {
    this.results(results);
    this.hasMoreResults = results.length == this.RESULT_CHUNK;
  };

  this.moreResults = function(results) {
  };

  this.selectItem = function(item) {
    console.log("Selected item!");
    console.log(item.itemId);

    // Don't actually do this.
    // Instead, make a call to get the XML by the item id.  Then create an item,
    // then set that item as the current item.
    settings = {
      url: '/eBay/item',
      data: {
        'itemId': item.itemId
      },
      success: function(xmlData) {
        // Create xml data.
        console.log(xmlData);
        itemObj = new Item(xmlData);
        self.currentItem(itemObj);
      }
    };
    $.ajax(settings);

    // Prevents default action.
    return false;
  };
}


var searchViewModel = new SearchViewModel();
$.getJSON(window.location.href, function(results) {
  searchViewModel.query = $.url().param('q');
  searchViewModel.addResults(results);
  ko.applyBindings(searchViewModel, document.getElementById('search-body'));
});