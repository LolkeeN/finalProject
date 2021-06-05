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
    <title>Registration</title>
</head>
<body>
<form action="registration" method="post">
    <b>Email -> </b><input name="email"/><br/>
    <b>Last name -></b><input name="lastName"/><br/>
    <b>First Name -> </b><input name="firstName"/><br/>
    <b>Password -> </b><input type="password" name="password"/><br/>
    <b>Role -> </b><input name="role" value="user"/><br/>
    <input type="submit" value="Registration!"/>
</form>
</body>
</html>
