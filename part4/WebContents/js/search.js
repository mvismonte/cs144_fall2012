// Copyright UCLA 2012
// CS 144 Fall 2012
// Author: Mark Vismonte
// Author: Logan Chang
// Date: 11/23/2012
// search.js

var map;

Bid = function(xmlDocument) {
  var xmlDoc = $(xmlDocument);
  this.userObj = new User(xmlDoc.find('Bidder')); // Bidder
  this.time = xmlDoc.find('Time').text(); // Time
  this.amount = xmlDoc.find('Amount').text(); // Amount
};

User = function(xmlDocument) {
  var xmlDoc = $(xmlDocument);
  this.userId = xmlDoc.attr('UserID'); // UserID 
  this.rating = xmlDoc.attr('Rating'); // Rating
  this.location = xmlDoc.find('Location').text(); // Location
  this.country = xmlDoc.find('Country').text(); // Country
};


Item = function(xmlDocument) {
  // Set fields.
  // name
  // description
  // seller
  // bids <- array of bids.
  // etc.
  var xmlDoc = $(xmlDocument);
  this.name = xmlDoc.find('Name').text(); // Name

  // Categories
  var categories = xmlDoc.find('Category');
  this.categoryArray = new Array();
  for (var i = 0; i < categories.length; i++) {
    this.categoryArray.push($(categories[i]).text());
  }

  this.currently = xmlDoc.find('Currently').text(); // Currently
  var bp = xmlDoc.find('Buy_Price').text(); // Buy_Price
  if (bp == "") {
    this.buy_price = "None";
  } else {
    this.buy_price = bp;
  }

  this.first_bid = xmlDoc.find('First_Bid').text(); // First_Bid
  this.num_of_bids = xmlDoc.find('Number_of_Bids').text(); // Number_of_Bids

  // Bids
  var bids = xmlDoc.find('Bids').find('Bid');
  this.bidArray = new Array();
  for (var i = 0; i < bids.length; i++) {
    bidObj = new Bid(bids[i]);
    this.bidArray.push(bidObj);
  }
  var locations = xmlDoc.find('Location')
  this.location = $(locations[locations.length-1]).text(); // Location
  var countries = xmlDoc.find('Country')
  this.country = $(countries[countries.length-1]).text(); // Country
  this.started = xmlDoc.find('Started').text(); // Started
  this.ends = xmlDoc.find('Ends').text(); // Ends
  this.seller = new User(xmlDoc.find("Seller")); // Seller
  this.description = xmlDoc.find('Description').text(); // Description
};

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
    if (!self.hasMoreResults()) {
      return;
    }
    self.current_index(self.current_index() + 1);
    console.log('Next results');
    self.loadResults();
  }

  this.previousResults = function() {
    if (self.current_index() == 0) {
      return;
    }
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
        a = xmlData;
        var itemObj = new Item(xmlData);
        console.log(itemObj);
        self.currentItem(itemObj);
        var addressObj = new Address(itemObj);

        self.Lat = ko.observable(addressObj.lat);
        self.Lng = ko.observable(addressObj.lng);
      }
    };
    $.ajax(settings);

    // Prevents default action.
    return false;
  };
}

function createMap() {
    var latlng = new google.maps.LatLng(34.0522, -118.2428)
    var mapOptions = {
        zoom: 8,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map($('#map')[0], mapOptions);
}

Address = function codeAddress(item) {
  var geocoder = new google.maps.Geocoder();
  var address = item.location;
  geocoder.geocode( { 'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
//      map.setCenter(results[0].geometry.location);
//      var marker = new google.maps.Marker({
//          map: map,
          //position: results[0].geometry.location
      this.lat = results[0].geometry.location.lat;
      this.lng = results[0].geometry.location.lng;
      //});
    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}

ko.bindingHandlers.map = {
  init: function (element, valueAccessor, allBindingsAccessor, viewModel) {
    var position = new google.maps.LatLng(allBindingsAccessor().latitude(), allBindingsAccessor().longitude());
    var marker = new google.maps.Marker({
        map: allBindingsAccessor().map,
        position: position,
        title: name
    });
    viewModel._mapMarker = marker;
  },
  update: function (element, valueAccessor, allBindingsAccessor, viewModel) {
      var latlng = new google.maps.LatLng(allBindingsAccessor().latitude(), allBindingsAccessor().longitude());
      viewModel._mapMarker.setPosition(latlng);
  }
}

var searchViewModel = new SearchViewModel();
searchViewModel.query = $.url().param('q');
searchViewModel.loadResults();

$(document).ready(function () {
  createMap();
  ko.applyBindings(searchViewModel, document.getElementById('search-body'));
});
//createMap();
//ko.applyBindings(searchViewModel, document.getElementById('search-body'));
