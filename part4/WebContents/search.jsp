<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head><title>eBay Search</title></head>
  <body>
    <h1>eBay Search Web Site</h1>
    <table>
      <thead>
        <tr>
          <th>ItemID</th>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="result" items="${results}">
          </tr>
            <td><c:out value="${result.itemId}"/></td>
            <td><c:out value="${result.name}"/></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </body>
</html>