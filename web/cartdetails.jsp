<%-- 
    Document   : cartdetails
    Created on : Oct 18, 2020, 1:33:21 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Detail Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            .searchDetails{
                width: 500px;
                margin: auto;  
                padding: 30px;  
                background: white;  
                border-radius: 15px;  
            }
        </style>
    </head>
    <body class="bg">
        <c:if test="${sessionScope.USER.role != 1}">
            <c:redirect url="GetProduct"/>
        </c:if>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="GetProduct">Yellow Moon Shop</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="cart.jsp">Shopping Cart - (Total: $${sessionScope.CART.total})</a></li>
                    <li class="active"><a href="cartdetails.jsp">Track Order</a></li>
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
        <h1>Tracking Order</h1>
        <form action="SearchOrderDetail" method="POST" class="searchDetails">
            <input type="text" name="txtOrderID" placeholder="Enter your Order Code here" style="width: 440px" required/>
            <input type="submit" class="btn btn-block btn-primary" value="Search Order"/>
        </form>
        <c:if test="${not empty requestScope.ORDER}">
            <table border="1" class="productTable" style="background-color: white; font-family: Tahoma; font-weight: bold; text-align: center;">
                <thead>
                    <tr>
                        <th>OrderID</th>
                        <th>Username</th>
                        <th>Total Price</th>
                        <th>Order Date</th>
                        <th>Fullname</th>
                        <th>Phone</th>
                        <th>Order Address</th>
                        <th>Payment Method</th>
                        <th>Order Status</th>
                        <th>View Details</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${requestScope.ORDER.uid}</td>
                        <td>${requestScope.ORDER.username}</td>
                        <td>${requestScope.ORDER.price}</td>
                        <td>${requestScope.ORDER.date}</td>
                        <td>${requestScope.ORDER.fullname}</td>
                        <td>${requestScope.ORDER.phone}</td>
                        <td>${requestScope.ORDER.address}</td>
                        <td>${requestScope.ORDER.paymentMethod}</td>
                        <td>
                            <c:if test="${requestScope.ORDER.status eq 'False'}" var="statusCheck">
                                Incompleted
                            </c:if>
                            <c:if test="${!statusCheck}">
                                Completed
                            </c:if>
                        </td>
                        <td>
                            <form action="ViewOrderDetail" action="POST">
                                <input type="hidden" value="${requestScope.ORDER.uid}" name="txtOrderID"/>
                                <input type="submit" class="btn btn-block btn-primary" value="View"/>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h1>Order Details</h1>
            <table border="1" class="productTable" style="background-color: white; font-family: Tahoma; font-weight: bold; text-align: center;">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Product Quantity</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="productDetails" items="${requestScope.DETAIL}">
                        <tr>
                            <td>${productDetails.productName}</td>
                            <td>${productDetails.quantity}</td>
                            <td>${productDetails.totalPrice}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty requestScope.ORDER}">
            <p style="color: yellow; text-align: center; font-family: Tahoma; font-weight: bold;">No Order Found</p>
        </c:if>
    </body>
</html>
