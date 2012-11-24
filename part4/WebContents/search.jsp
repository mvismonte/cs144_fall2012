<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basepage>
  <jsp:attribute name="title">
    CS144 eBay search
  </jsp:attribute>
  <jsp:attribute name="extraScripts">
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
        <div data-bind="ifnot: currentItem" style="display: none;">
          Select an item
        </div>
        <div data-bind="if: currentItem" style="display: none;">
          Item selected
        </div>
    </div>
  </jsp:body>
</t:basepage>