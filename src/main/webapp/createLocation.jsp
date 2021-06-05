<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 27.05.2021
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create location</title>
</head>
<body>
<form action="createLocation" method="post">
    <b>Country -> </b><input name="country"/><br/>
    <b>City -> </b><input name="city"/><br/>
    <b>Street -> </b><input name="street"/><br/>
    <b>house -> </b><input name="house"/><br/>
    <b>room -> </b><input name="room"/><br/>
    <b>language -> </b><input name="language"/><br/>
    <input type="submit" value="Create location"/>
</form>
</body>
</html>
