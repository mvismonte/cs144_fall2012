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

SearchViewModel = function() {
  this.results = ko.observableArray([]);
  this.hasMoreResults = false;
  this.currentItem = ko.observable(null);
  this.RESULT_CHUNK = 50;
  this.query = "";
}

SearchViewModel.prototype.addResults = function(results) {
  this.results(results);
  this.hasMoreResults = results.length == this.RESULT_CHUNK;
};

SearchViewModel.prototype.moreResults = function(results) {
};

SearchViewModel.prototype.selectItem = function(item) {
  console.log("Selected item!");
  console.log(item.itemId);

  // Prevents default action.
  return false;
};


var searchViewModel = new SearchViewModel();
$.getJSON(window.location.href, function(results) {
  searchViewModel.query = $.url().param('q');
  searchViewModel.addResults(results);
  ko.applyBindings(searchViewModel, document.getElementById('search-body'));
});