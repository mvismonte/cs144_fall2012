

AutoCompleteSuggestions = function () {
}

/**
 * Request suggestions for the given autosuggest control. 
 * @scope protected
 * @param oAutoSuggestControl The autosuggest control to provide suggestions for.
 */
AutoCompleteSuggestions.prototype.requestSuggestions = function (oAutoSuggestControl /*:AutoSuggestControl*/) {
  var sTextboxValue = oAutoSuggestControl.textbox.value;
  
  if (sTextboxValue.length > 0){
    settings = {
      url: '/eBay/suggest',
      data: {
        'q': sTextboxValue
      },
      success: function(data) {
        var suggestions = $(data).find('suggestion');
        var aSuggestions = [];
        for (var i = 0; i < suggestions.length; i++) {
          aSuggestions.push($(suggestions[i]).attr('data'));
        }
        oAutoSuggestControl.autosuggest(aSuggestions);
      }
    };
    $.ajax(settings);
  }

  //provide suggestions to the control
  
};

$(function() {
  var oTextbox = new AutoSuggestControl(document.getElementById("search-bar"), new AutoCompleteSuggestions());
});
