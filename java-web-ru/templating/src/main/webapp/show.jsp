<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
    <table>
    <c:forEach var="entry" items="${user}">
        <tr>
        <td>
        <c:out value= "${entry.key}" />: <c:out value= "${entry.value}" /></td>
        </tr>
    </c:forEach>
    </table>
    <p><a href='/users/delete?id=${user.get("id")}'>Delete</a></p>
    <p><a href='/users'>Show all users</a></p>
    </body>
</html>
