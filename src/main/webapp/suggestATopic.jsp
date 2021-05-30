<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 29.05.2021
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Suggest a topic</title>
</head>
<body>
<form action="createSuggestedTopic" method="get">
  <b>Name -> </b><input name="name"/><br/>
  <b>Date -> </b><input name="date"/><br/>
  <b>language -> </b><input name="language"/><br/>
  <b>description -> </b><input name="description"/><br/>
  <b></b><input hidden name="meeting_name" value="suggested_topics"/><br/>
  <input type="submit" value="Suggest topic"/>
</form>
</body>
</html>
