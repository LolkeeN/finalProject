<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 27.05.2021
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Topic</title>
</head>
<body>
<form action="createTopic" method="get">
    <b>Name -> </b><input name="name"/><br/>
    <b>Date -> </b><input name="date"/><br/>
    <b>language -> </b><input name="language"/><br/>
    <b>description -> </b><input name="description"/><br/>
    <b>meeting name -> </b><input name="meeting_name"/><br/>
    <input type="submit" value="Create topic"/>
</form>
<form action="getSuggestedTopics" method="get">
    <input type="submit" value="get from suggested topics"/>
</form>
</body>
</html>
