<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 29.05.2021
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change meeting place and time</title>
</head>
<body>
<form action="changeMeetingTimeAndPlace" method="post">
    <b>Meeting name -> </b><input name="name"/><br/>
    <b>Date -> </b><input name="date"/><br/>
    <b>Location id -> </b><input name="location_id"/><br/>
    <input type="submit" value="Change meeting date"/>
</form>
</body>
</html>
