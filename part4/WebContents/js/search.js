// This is a simple *viewmodel* - JavaScript that defines the data and behavior of your UI
/*SearchViewModel = function() {
    this.firstName = "Bert";
}

// Activates knockout.js
ko.applyBindings(new AppViewModel());*/

$('.itemDetail').click(function(ev) {
  // console.log('hi');
  console.log(window.location.hash);
  itemID = window.location.hash.slice(1, window.location.hash.length);
  $.get('/eBay/item?itemId=' + itemID, function(data) {
    a = $(data);
    console.log(data);
  });
});