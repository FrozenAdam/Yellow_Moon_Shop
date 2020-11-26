<%-- 
    Document   : manage
    Created on : Oct 12, 2020, 4:06:18 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Product Page</title>
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
        <h1>Manage Product</h1>
        <div class="controlButton">
            <form action="GetCategoryList" method="POST" style="display:inline;">
                <button class="btn btn-primary">Create Product</button>
            </form>
            <form action="SearchProductForAdmin" method="POST" style="display: inline;">
                <input type="text" name="txtSearch" value="${param.txtSearch}" placeholder="Search product name"/>
                <button class="btn btn-primary">Search Product</button>
            </form>
        </div>
        <p style="font-family: Tahoma; font-weight: bold; color: yellow; text-align: center;">${requestScope.SUCCESS}</p>
        <div class="searchValue">
            <c:if test="${not empty requestScope.PRODUCTINFO}" var="searchResult">
                <table border="1" style="background-color: white; font-family: Tahoma; font-weight: bold; text-align: center" class="productTable">
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Product Description</th>
                            <th>Product Price</th>
                            <th>Product Quantity</th>
                            <th>Product Image</th>
                            <th>Created Date</th>
                            <th>Expired Date</th>
                            <th>Category</th>
                            <th>Status</th>
                            <th style="text-align: center;">Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="productList" items="${requestScope.PRODUCTINFO}">
                            <tr>
                                <td>${productList.id}</td>
                                <td>${productList.name}</td>
                                <td>${productList.description}</td>
                                <td>$${productList.price}</td>
                                <td>${productList.quantity}</td>
                                <td><img src="img/${productList.image}" style="width: 200px; height: 200px;"/></td>
                                <td>${productList.createdDate}</td>
                                <td>${productList.expiredDate}</td>
                                <td>${productList.categoryName}</td>
                                <td>${productList.statusName}</td>
                                <td>
                                    <form action="GetProductUpdate" method="POST">
                                        <input type="hidden" name="txtProductID" value="${productList.id}"/>
                                        <button class="btn btn-primary">Update Product</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${!searchResult}">
                <p style="font-family: Tahoma; font-weight: bold; color: yellow">No Result Found</p>
            </c:if>
            <form action="SearchProductForAdmin" method="POST" style="display:inline;">
                <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                <input type="hidden" name="txtPage" value="${requestScope.CURRENT}"/>
                <input type="hidden" name="movePage" value="prev"/>
                <input type="submit" class="btn btn-primary" value="Previous"/>
            </form>
            <p style="display:inline; text-align: center; color: yellow; font-family: Tahoma; font-weight: bold;">Page ${requestScope.CURRENT} / ${requestScope.MAX}</p>
            <form action="SearchProductForAdmin" method="POST" style="display:inline;">
                <input type="hidden" name="txtSearch" value="${param.txtSearch}"/>
                <input type="hidden" name="txtPage" value="${requestScope.CURRENT}"/>
                <input type="hidden" name="movePage" value="next"/>
                <input type="submit" class="btn btn-primary" value="Next"/>
            </form>
        </div>
    </body>
</html>
