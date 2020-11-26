<%-- 
    Document   : signup
    Created on : Oct 4, 2020, 2:54:51 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up Page</title>
    <body background="../img/mooncake.jpg"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/mystyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <style>
        .singUpBox{
            font-family: Tahoma;
            font-weight: bold;
        }
        .usernameReg, .passwordReg, .confirmReg, .fullnameReg, .phoneReg, .addressReg{
            width: 339px;
        }
        .signUp{
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
    <script>
        function validationSignUp() {
            var x = document.forms["signupForm"]["txtPassword"].value;
            var y = document.forms["signupForm"]["confirmPassword"].value;
            if (x !== y) {
                alert("Incorrect Password and Re-Password");
                return false;
            }
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
                <li class="active"><a href="cart.jsp">Shopping Cart</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                <li><a href="signin.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
            </ul>
        </div>
    </nav>
    <h1>Sign Up</h1>
    <div class="signUp">
        <form action="Register" method="POST" name="signupForm" onsubmit="return validationSignUp()">
            <font style="color: red; font-family: Tahoma; font-weight: bold; text-align: center;">
            ${requestScope.ERROR}
            </font>
            <div class="singUpBox"> 
                <input type="email" name="txtUsername" placeholder="Email" value="${param.txtUsername}" class="usernameReg" pattern="([a-zA-Z0-9]{3,30})(@)([a-zA-Z0-9-]{3,30})([.])([a-zA-Z0-9]{2,5})([.][vn]{2})?" required/></br>
                <input type="password" name="txtPassword" placeholder="Password" class="passwordReg" required/></br>
                <input type="password" name="confirmPassword"  placeholder="Re-password" class="confirmReg" required/></br>
                <input type="text" name="txtFullname" placeholder="Fullname" value="${param.txtFullname}" class="fullnameReg" maxlength="50" required/></br>
                <input type="text" name="txtPhone" placeholder="Phone Number(10 digits)" value="${param.txtPhone}" class="phoneReg" pattern="[0-9]{10}" minlength="10" maxlength="10" required/></br>
                <input type="text" name="txtAddress" placeholder="Address" value="${param.txtAddress}" class="addressReg" maxlength="50" required/></br>
                <input type="submit" value="Sign Up" class="btn btn-block btn-primary" style="display: inline; text-align: center; font-family: Tahoma; font-weight: bold;"/>
            </div>
        </form>
    </div>
</body>
</html>
