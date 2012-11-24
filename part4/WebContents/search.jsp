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
        <table class="table table-striped">
          <thead>
            <tr><th>Item Name</th></tr>
          </thead>
          <tbody data-bind="foreach: results">
            <tr><td><a data-bind="text: name, attr: { href: '#'}, click: $root.selectItem"></a></td></tr>
          </tbody>
        </table>
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