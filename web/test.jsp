<%-- 
    Document   : test
    Created on : Nov 2, 2020, 10:42:26 AM
    Author     : theFrozenAdam
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List User Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER.role != 2}">
            <c:redirect url="GetProduct"/>
        </c:if>
        <h1>User List</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Role</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.TEST}" var="test">
                    <tr>
                        <td>${test.username}</td>
                        <td>${test.fullname}</td>
                        <td>${test.phone}</td>
                        <td>${test.address}</td>
                        <td>${test.roleName}</td>
                        <td>${test.statusName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
