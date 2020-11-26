<%-- 
    Document   : home
    Created on : Oct 4, 2020, 2:48:14 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            .searchBox{
                width: 500px;
                margin: 15px 50px 15px 690px;  
                padding: 5px;  
                background: white;  
                border-radius: 15px; 
                text-align: center;
            }
        </style>
        <script>
            function updateForMinPrice(val) {
                document.getElementById('minPrice').value = val;
            }
            function updateForMaxPrice(val) {
                document.getElementById('maxPrice').value = val;
            }
        </script>
    </head>
    <body class="bg">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="GetProduct">Yellow Moon Shop</a>
                </div>
                <ul class="nav navbar-nav">
                    <c:if test="${sessionScope.USER.role != 2}">
                        <li class="active"><a href="cart.jsp">Shopping Cart - (Total: $${sessionScope.CART.total})</a></li>
                        </c:if>
                        <c:if test="${sessionScope.USER.role == 1}">
                        <li class="active"><a href="cartdetails.jsp">Track Order</a></li>
                        </c:if>
                        <c:if test="${sessionScope.USER.role == 2}">
                        <li class="active"><a href="SearchProductForAdmin">Manage Product</a></li>
                        <li class="active"><a href="GetLogs">Product Log</a></li>
                        <li class="active"><a href="GetAllUser">User List</a></li>
                        </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${empty sessionScope.USER.username}" var="checkLogin">
                        <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                        <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                        </c:if>
                        <c:if test="${!checkLogin}">
                        <li><a href="#">Welcome, ${sessionScope.USER.fullname}</a></li>
                        <li><a href="Logout">Logout</a></li>
                        </c:if>
                </ul>
            </div>
        </nav>
        <h1>Welcome to our Yellow Moon Shop</h1>
        <form action="GetProduct" method="POST" class="searchBox" style="font-family: Tahoma; font-weight: bold;">
            <input type="text" placeholder="Search Cake Name" name="txtSearchName" value="${param.txtSearchName}" style="display: inline; width: 300px; text-align: center;"/> </br>
            Min Price: <input type="text" name="txtMin" value="0" value="${param.txtMin}" id="minPrice" readonly style="width: 230px; text-align: center;"/> <input type="range" min="0" max="500" step="10" value="0" style="display: inline;" onchange="updateForMinPrice(this.value);"/></br>
            Max Price: <input type="text" name="txtMax" value="500" value="${param.txtMax}" id="maxPrice" readonly style="width: 230px; text-align: center;"/> <input type="range" min="500" max="1000" step="10" value="500" style="display: inline;" onchange="updateForMaxPrice(this.value);"/></br>
            Category: <select name="txtCategory">
                <c:forEach var="categoryName" items="${requestScope.CATEGORY}">
                    <option value="${categoryName.id}">${categoryName.name}</option>
                </c:forEach>
            </select>
            <input type="submit" class="btn btn-block btn-primary" value="Search Product"/>
        </form>
        <p style="font-family: Tahoma; font-weight: bold; margin: auto; text-align: center; color: yellow;">${requestScope.WARN}</p>
        <div class="row">
            <c:if test="${not empty requestScope.LIST}" var="checkSearch">
                <c:forEach var="productInfo" items="${requestScope.LIST}">
                    <div class="column">
                        <div class="card">
                            <img src="img/${productInfo.image}" style="width:20%;"/>
                            <h3>${productInfo.name} - ${productInfo.categoryName}</h3>
                            <p class="price">$${productInfo.price}</p>
                            <p>${productInfo.description}</p>
                            <p>Quantity: ${productInfo.quantity}</p>
                            <p>${productInfo.createdDate} - ${productInfo.expiredDate}</p>
                            <form action="AddToCart" method="POST" style="font-family: Tahoma; font-weight: bold;">
                                <input type="hidden" name="txtProductID" value="${productInfo.id}"/>
                                <input type="submit" class="btn btn-block btn-primary" value="Add To Cart"/>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${!checkSearch}">
                <p style="color: yellow; text-align: center; font-family: Tahoma; font-weight: bold;">No Product Found</p>
            </c:if>
        </div>
        <div style="text-align: center;">
            <form action="GetProduct" method="POST" style="display:inline;">
                <input type="hidden" name="txtSearchName" value="${param.txtSearchName}"/>
                <input type="hidden" name="txtCategory" value="${param.txtCategory}"/>
                <input type="hidden" name="txtPage" value="${requestScope.CURRENT}"/>
                <input type="hidden" name="movePage" value="prev"/>
                <input type="submit" class="btn btn-primary" value="Previous"/>
            </form>
            <p style="display:inline; text-align: center; color: yellow; font-family: Tahoma; font-weight: bold;">Page ${requestScope.CURRENT} / ${requestScope.MAX}</p>
            <form action="GetProduct" method="POST" style="display:inline;">
                <input type="hidden" name="txtSearchName" value="${param.txtSearchName}"/>
                <input type="hidden" name="txtCategory" value="${param.txtCategory}"/>
                <input type="hidden" name="txtSearch" value="${param.txtSearchName}"/>
                <input type="hidden" name="txtPage" value="${requestScope.CURRENT}"/>
                <input type="hidden" name="movePage" value="next"/>
                <input type="submit" class="btn btn-primary" value="Next"/>
            </form>
        </div>
    </body>
</html>
