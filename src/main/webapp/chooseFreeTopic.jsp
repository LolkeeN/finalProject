<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Choose free topic</title>
</head>
<body>
<jsp:useBean id="freeTopics" scope="request" type="java.util.List"/>
<c:forEach var="topic" items="${freeTopics}">
    ${topic.id} |
    ${topic.name} |
    ${topic.date} |
    ${topic.description} |
    <br/>
</c:forEach>
</body>
<form action="bindFreeTopic" method="get">
<b>Choose topic id -> </b><input name="topic_id"/><br/>
<input type="submit" value="Get a topic"/>
</form>
</html>
