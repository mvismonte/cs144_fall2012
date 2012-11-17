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
      <c:choose>
        <c:when test="${fn:length(results) > 0}">
          <table class="table table-striped">
            <thead>
              <tr>
                <th>ItemID</th>
                <th>Name</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="result" items="${results}">
                </tr>
                  <td>${result.itemId}</td>
                  <td><a href="#${result.itemId}" class="itemDetail">${result.name}</a></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
            No results found.
        </c:otherwise>
      </c:choose>
      <div class="modal hide fade" id="modal-item-info">
        <div class="modal-header">
          <button class="close" data-dismiss="modal">×</button>
          <h3 data-bind="text: modalTitleAnimal()"></h3>
        </div>
        <div class="modal-body">
          <h4>Staff responsible for these animals</h4>
          <div data-bind="foreach: staffs">
            <span data-bind="text: full_name" class="label"></span>&nbsp;
          </div>
        </div>
        <div class="modal-footer">
          <a href="#" class="btn" data-dismiss="modal">Close</a>
          <a href="#" class="btn btn-primary" data-bind="">Observe</a>
        </div>
      </div>
    </div>
  </jsp:body>
</t:basepage>