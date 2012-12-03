
BuyNowViewModel = function () {
  this.id = $.cookie('item_id');
  this.name = $.cookie('item_name');
  this.buy_price = $.cookie('item_buy_price');
  this.is_buying_item = ko.observable(true);
  this.credit_card = ko.observable(null);
  this.buy_time = ko.observable(null);
}

BuyNowViewModel.prototype.buy = function() {
  // Make an AJAX call sending credit_card.
  this.buy_time('Now');
  this.is_buying_item(false);
};

var buyNowViewModel = new BuyNowViewModel();
$(document).ready(function () {
  ko.applyBindings(buyNowViewModel, document.getElementById('buy-now-body'));
});