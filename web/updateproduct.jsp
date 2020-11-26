<%-- 
    Document   : updateproduct
    Created on : Oct 13, 2020, 7:22:02 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Product Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <style>
        .product{
            width: 500px;
            margin: auto;  
            padding: 80px;  
            background: white;  
            border-radius: 15px;  
        }
    </style>
    <body class="bg">
        <c:if test="${sessionScope.USER.role != 2}">
            <c:redirect url="GetProduct"/>
        </c:if>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="cart.jsp">Yellow Moon Shop</a>
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
        <h1>Update Product</h1>
        <div class="product">
            <form action="UpdateProduct" method="POST" class="updateProduct" enctype="multipart/form-data">
                <input type="text" name="txtProductID" value="${requestScope.DTO.id}" readonly="true" style="width: 339px;"/>
                <input type="text" name="txtProductName" value="${requestScope.DTO.name}" maxlength="20" placeholder="Product Name" required style="width: 339px;"/>
                <input type="text" name="txtDescription" value="${requestScope.DTO.description}" placeholder="Product Description" style="width: 339px;"/>
                <input type="number" name="txtPrice" value="${requestScope.DTO.price}" min="0" placeholder="Product Price" required style="width: 339px;"/>
                <input type="number" name="txtQuantity" value="${requestScope.DTO.quantity}" min="0" placeholder="Product Quantity" required style="width: 339px;"/>
                Old Image: <img src="img/${requestScope.DTO.image}" name="txtProductImage" value="${requestScope.DTO.image}" style="width: 200px; height: 200px;"/> 
                <input type="file" name="txtProductImage" accept="image/*"/>
                Created Date: <input type="date" name="txtCreatedDate" min="2019-01-01" max="2023-12-31" value="${requestScope.DTO.createdDate}" required style="width: 237px;"/></br>
                Expired Date: <input type="date" name="txtExpiredDate" min="2019-01-01" max="2025-12-31" value="${requestScope.DTO.expiredDate}" required style="width: 239px;"/></br>
                Category: <select name="txtCategory" style="width: 266px; display: inline;">
                    <c:forEach var="categoryName" items="${requestScope.CATEGORY}">
                        <c:if test="${requestScope.DTO.categoryID eq categoryName.id}" var="categoryCheck"> 
                            <option selected="selected" value="${categoryName.id}">${categoryName.name}</option>
                        </c:if>
                        <c:if test="${!categoryCheck}">
                            <option value="${categoryName.id}">${categoryName.name}</option>
                        </c:if>
                    </c:forEach>
                </select> </br>
                Status: <select name="txtStatus" style="width: 270px; float: right; display: inline;">
                    <c:forEach var="statusName" items="${requestScope.STATUS}">
                        <c:if test="${requestScope.DTO.statusID eq statusName.id}" var="statusCheck">
                            <option selected="selected" value="${statusName.id}">${statusName.name}</option>
                        </c:if>
                        <c:if test="${!statusCheck}">
                            <option value="${statusName.id}">${statusName.name}</option> 
                        </c:if>
                    </c:forEach>
                </select>
                <input type="submit" class="btn btn-block btn-primary" value="Update Product"/>
            </form>
        </div>
    </body>
</html>