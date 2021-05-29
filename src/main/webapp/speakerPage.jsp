<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 27.05.2021
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Speaker Page</title>
</head>
<body>
<%="Speaker page"%>
<hr>
<form action="speakerMeetings" method="get">
    <input type="submit" value="get your meetings"/>
</form>
<hr>
<form action="getFreeTopics" method="get">
    <input type="submit" value="show free meetings"/>
</form>
<hr>
<form action="suggestATopic.jsp" method="get">
    <input type="submit" value="suggest a topic"/>
</form>
<hr>
</body>
</html>
