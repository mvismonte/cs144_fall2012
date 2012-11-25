<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basepage>
  <jsp:attribute name="title">
    CS144 eBay search
  </jsp:attribute>
  <jsp:attribute name="extraScripts">
    <script src="https://maps.googleapis.com/maps/api/js?sensor=false">
    </script>
    <script src="/eBay/js/search.js"></script>
  </jsp:attribute>
  <jsp:body>
    <div id="search-body" class="row">
      <div class="span4">
        <div class="alert alert-error" style="display: none;" data-bind="visible: results().length == 0 && current_index() == 0">
          Could not find any results for: <span data-bind="text: query"></span>
        </div>
        <div class="alert alert-error" style="display: none;" data-bind="visible: results().length == 0 && current_index() != 0">
          No more results
        </div>
        <table class="table table-striped" data-bind="if: results().length > 0">
          <thead>
            <tr><th>Item Name</th></tr>
          </thead>
          <tbody data-bind="foreach: results">
            <tr><td><a href="#" data-bind="text: name, click: $root.selectItem"></a></td></tr>
          </tbody>
        </table>
        <ul class="pager">
          <li class="previous" data-bind="css: { disabled: current_index() == 0 }">
            <a href="#" data-bind="click: previousResults">&larr; Previous Results</a>
          </li>
          <li class="next" data-bind="css: { disabled: !hasMoreResults() }">
            <a href="#" data-bind="click: nextResults">Next Results &rarr;</a>
          </li>
        </ul>
      </div>
      <div class="span8">
        <div data-bind="visible: currentItem() == null" style="display: none;" class="alert alert-info">
          Select an item
        </div>
        <div data-bind="if: currentItem() != null">
          <dl class="dl-horizontal">
            <dt>Name</dt>
            <dd data-bind="text: currentItem().name"></dd>
            <dt>Description</dt>
            <dd data-bind="text: currentItem().description"></dd>
            <dt>Currently</dt>
            <dd data-bind="text: currentItem().currently"></dd>
            <dt>Buy Price</dt>
            <dd data-bind="text: currentItem().buy_price"></dd>
            <dt>First_Bid</dt>
            <dd data-bind="text: currentItem().first_bid"></dd>
            <dt>Number of Bids</dt>
            <dd data-bind="text: currentItem().num_of_bids"></dd>
            <dt>Location</dt>
            <dd data-bind="text: currentItem().location"></dd>
            <dt>Started</dt>
            <dd data-bind="text: currentItem().started"></dd>
            <dt>Ends</dt>
            <dd data-bind="text: currentItem().ends"></dd>
            <dt>Bids</dt>
            <dd>
              <table class="table table-striped" data-bind="if: currentItem().bidArray.length > 0">
                <thead>
                  <tr><th>Bidder</th><th>Rating</th><th>Time</th><th>Amount</th></tr>
                </thead>
                <tbody data-bind="foreach: currentItem().bidArray">
                  <tr>
                    <td data-bind="text: userObj.userId"></td>
                    <td data-bind="text: userObj.rating"></td>
                    <td data-bind="text: time"></td>
                    <td data-bind="text: amount"></td>
                  </tr>
                </tbody>
              </table>
            </dd>
            <dt>Categories</dt>
            <dd data-bind="foreach: currentItem().categoryArray">
                <li><span data-bind="text: $data"></span></li>
            </dd>
            <dt>Seller</dt>
            <dd data-bind="text: currentItem().seller.userId"></dd>
            <dt>Rating</dt>
            <dd data-bind="text: currentItem().seller.rating"></dd>

            <dt id="map"><dt>
              <dd data-bind="lattude:searchViewModel.Lat, longitude:searchViewModel.Lng, map:map"></dd>
          </dl>
        </div>
      </div>
    </div>
  </jsp:body>
</t:basepage>