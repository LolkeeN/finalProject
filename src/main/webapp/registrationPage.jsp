<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 26.05.2021
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        button:hover {
            background-color: silver;
        }
    </style>
    <title>Registration</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div>
    <nav class="nav navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="loginPage.jsp" class="navbar-brand">Meetings</a>
            </div>
        </div>
    </nav>
</div>
<br/>
<div class="container-fluid">
<form action="registration" method="post">
    <b>Email -> </b><input name="email"/><br/><br/>
    <b>Last name -> </b><input name="lastName"/><br/><br/>
    <b>First Name -> </b><input name="firstName"/><br/><br/>
    <b>Password -> </b><input type="password" name="password"/><br/><br/>
    <b>Role -> </b><input name="role" value="user"/> user or speaker <br/><br/>
    <button type="submit" class="btn btn-primary">Register me!</button>
</form>
</div>
</body>
</html>
