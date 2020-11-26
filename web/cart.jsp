<%-- 
    Document   : cart
    Created on : Oct 17, 2020, 3:16:53 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            .cartBox{
                width: 650px;
                margin: auto;  
                padding: 80px;  
                background: white;  
                border-radius: 15px;  
            }
            .name, .phone, .address{
                width: 490px;
            }
        </style>
        <script>
            function updateAmount(val) {
                document.getElementById('amount').value = val;
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
                    <li class="active"><a href="cart.jsp">Shopping Cart - (Total: $${sessionScope.CART.total})</a></li>
                        <c:if test="${sessionScope.USER.role == 2}">
                        <li class="active"><a href="SearchProductForAdmin">Manage Product</a></li>
                        <li class="active"><a href="GetLogs">Product Log</a></li>
                        </c:if>
                        <c:if test="${sessionScope.USER.role == 1}">
                        <li class="active"><a href="cartdetails.jsp">Track Order</a></li>
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
        <h1>Your Cart</h1>
        <c:if test="${not empty sessionScope.CART}" var="checkCart">
            <div class="cartBox">
                <p style="font-family: Tahoma; font-weight: bold; margin: -50px 0px 10px 0px; text-align: center; color: red;">${requestScope.WARN}</p>
                <table border="1" style="font-family: Tahoma; font-weight: bold; margin: auto 200px 15px -30px;">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>In Stock</th>
                            <th>In Cart</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cart" items="${sessionScope.CART.cart}">
                            <tr>
                                <td>${cart.value.name}</td>
                                <td>${cart.value.quantity}</td>
                        <form action="UpdateCart" method="POST">
                            <td><input type="number" value="${cart.value.cartQuantity}" min="1" name="txtCartQuantity"/></td>
                            <td>$${cart.value.price}</td>
                            <td>$${cart.value.price * cart.value.cartQuantity}</td>
                            <td>
                                <input type="hidden" name="txtProductID" value="${cart.value.id}"/>
                                <input type="hidden" name="txtQuantity" value="${cart.value.quantity}"/>
                                <input type="submit" value="Update"/>
                            </td>
                        </form>
                        <td>
                            <form action="DeleteCart" method="POST">
                                <input type="hidden" name="txtProductID" value="${cart.value.id}"/>
                                <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this ${cart.value.name} product ?')"/>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3"></td>
                        <td>Total:</td>
                        <td>${sessionScope.CART.total}$</td>
                    </tr>
                    </tbody>
                </table>
                <c:if test="${not empty sessionScope.USER}" var="checkLogin">
                    <form action="ConfirmCart" method="POST" style="font-family: Tahoma; font-weight: bold;">
                        Payment Method:<select name="txtPaymentMethod">
                            <option value="Cash On Delivery">Cash On Delivery</option>
                        </select>
                        <input type="submit" value="Confirm" class="btn btn-primary" style="float: right;"/>
                    </form>
                </c:if>
                <c:if test="${!checkLogin}">
                    <p style="font-family: Tahoma; font-weight: bold; text-align: center;">Delivery Information</p>
                    <form action="ConfirmCart" method="POST" style="font-family: Tahoma; font-weight: bold;">
                        <input type="text" name="txtFullname" placeholder="Receive Name" value="${param.txtFullname}" class="name" required/><br/>
                        <input type="text" name="txtPhone" value="${param.txtPhone}" placeholder="Phone Number(10 digits)" class="phone" pattern="[0-9]{10}" minlength="10" maxlength="10" required/><br/>
                        <input type="text" name="txtAddress" value="${param.txtAddress}" placeholder="Address" class="address" maxlength="50" required/><br/>
                        Payment Method:<select name="txtPaymentMethod">
                            <option value="Cash On Delivery">Cash On Delivery</option>
                        </select>
                        <input type="submit" value="Confirm" class="btn btn-primary" style="float: right;"/>
                    </form>
                </c:if>
            </c:if>
        </div>
        <c:if test="${!checkCart}">
            <p style="color: yellow; text-align: center; font-family: Tahoma; font-weight: bold;">Your Cart is Empty</p>
        </c:if>
    </body>
</html>
