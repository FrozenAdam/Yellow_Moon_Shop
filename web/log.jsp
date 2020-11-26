<%-- 
    Document   : log
    Created on : Oct 17, 2020, 12:46:32 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body class="bg">
        <c:if test="${sessionScope.USER.role != 2}">
            <c:redirect url="GetProduct"/>
        </c:if>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="GetProduct">Yellow Moon Shop</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="SearchProductForAdmin">Manage Product</a></li>
                    <li class="active"><a href="GetLogs">Product Log</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">Welcome, ${sessionScope.USER.fullname}</a></li>
                    <li><a href="Logout">Logout</a></li>
                </ul>
            </div>
        </nav>
        <h1>All Product Log</h1>
        <c:if test="${not empty requestScope.LOG}" var="checkLog">
            <table border="1" style="background-color: white; font-family: Tahoma; font-weight: bold; text-align: center;" class="productTable">
                <thead>
                    <tr>
                        <th>Admin Name</th>
                        <th>Product Name</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="logList" items="${requestScope.LOG}">
                        <tr>
                            <td>${logList.name}</td>
                            <td>${logList.productName}</td>
                            <td>${logList.date}</td>
                            <td>${logList.action}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${!checkLog}">
            <p style="font-family: Tahoma; font-weight: bold; color: yellow; text-align: center;">No action found</p>
        </c:if>
    </body>
</html>
