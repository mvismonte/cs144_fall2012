
// Hack around making console.log work when it doesn't exist.
if (!console.log) {
  console.log = function() {};
}

BuyNowViewModel = function () {
  this.id = $.cookie('item_id');
  this.name = $.cookie('item_name');
  this.buy_price = $.cookie('item_buy_price');
  this.is_buying_item = ko.observable(true);
  this.credit_card = ko.observable(null);
  this.buy_time = ko.observable(null);

  var self = this;
  this.buy = function() {
    // Make an AJAX call sending credit_card.
    var settings = {
      'url': '/eBay/buy_now',
      'data': {
        'credit_card': self.credit_card
      },
      'type': 'POST',
      'dataType': 'json'
    };
    settings['success'] = function(data) {
      // var obj = jQuery.parseJSON(data);
      console.log(data);
      if (data.valid) {
        var currentTime = new Date()
        var year = currentTime.getFullYear();
        var month = currentTime.getMonth() + 1;
        var day = currentTime.getDate();
        var hour = currentTime.getHours();
        var minute = currentTime.getMinutes();
        var second = currentTime.getSeconds();
        self.buy_time(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
        self.is_buying_item(false);
      }
    };
    // Initiate the request.
    $.ajax(settings);
  };
}



var buyNowViewModel = new BuyNowViewModel();
$(document).ready(function () {
  ko.applyBindings(buyNowViewModel, document.getElementById('buy-now-body'));
});