<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basepage>
  <jsp:attribute name="title">
    CS144 eBay - Home
  </jsp:attribute>
  <jsp:attribute name="extraScripts">
    <script src="/eBay/js/jquery.cookie.js"></script>
    <script src="/eBay/js/buy_now.js"></script>
  </jsp:attribute>
  <jsp:body>
    <div id="buy-now-body" class="span4 offset4">
      <h1>Buy Item</h1>
      <div data-bind="visible: is_buying_item()">
        <dl class="dl-horizontal">
          <dt>Item ID</dt>
          <dd data-bind="text: id"></dd>
          <dt>Item Name</dt>
          <dd data-bind="text: name"></dd>
          <dt>Buy Price</dt>
          <dd data-bind="text: buy_price"></dd>
          <dt>Credit Card</dt>
          <dd><input type="text" data-bind="value: credit_card"></dd>
        </dl>
        <button class="btn btn-large btn-primary offset1" type="button" data-bind="click: buy">Buy</button>
      </div>
      <div data-bind="visible: !is_buying_item()">
        <h2>Order Received</h2>
        <dl class="dl-horizontal">
          <dt>Item ID</dt>
          <dd data-bind="text: id"></dd>
          <dt>Item Name</dt>
          <dd data-bind="text: name"></dd>
          <dt>Buy Price</dt>
          <dd data-bind="text: buy_price"></dd>
          <dt>Credit Card</dt>
          <dd data-bind="text: credit_card"></dd>
          <dt>Time</dt>
          <dd data-bind="text: buy_time"></dd>
          <dt>
        </dl>
      </div>
    </div>
  </jsp:body>
</t:basepage>
