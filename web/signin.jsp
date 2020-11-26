<%-- 
    Document   : login
    Created on : Oct 4, 2020, 2:48:37 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign In Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/mystyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style>
            #usernameBox, #passwordBox{
                width: 339px;
            }
            .loginBox{
                font-family: Tahoma;
                font-weight: bold;
            }
            .login{
                width: 500px;
                margin: auto;  
                padding: 80px;  
                background: white;  
                border-radius: 15px;  
            }
            h1{
                text-align: center;
                font-size: 80px;
                color: yellow;
            }
        </style>
    </head>
    <body class="bg">
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="GetProduct">Yellow Moon Shop</a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="cart.jsp">Shopping Cart</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </ul>
            </div>
        </nav>
        <h1>Login</h1>
        <div class="login">
            <form action="Login" method="POST" name="loginForm" onsubmit="return validateLogin()">
                <div class="loginBox"> 
                    <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
                    ${requestScope.SUCCESS}
                    </font>
                    <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
                    ${requestScope.ERRORNOTE}
                    </font>
                    <input type="email" name="txtUsername" placeholder="Email" value="${param.txtUsername}" id="usernameBox" required/>
                    <input type="password" name="txtPassword" placeholder="Password" id="passwordBox" required/></br>
                </div>
                <input type="submit" value="Sign In" class="btn btn-block btn-primary" style="display: inline; float: inside; font-family: Tahoma; font-weight: bold;"/>
            </form>
    </body>
</html>
