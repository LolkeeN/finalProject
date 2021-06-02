<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Meeting topics</title>
</head>
<body>
<jsp:useBean id="topics" scope="request" type="java.util.List"/>
<c:forEach var="topic" items="${topics}">
    ${topic.id} |
    ${topic.name} |
    ${topic.date} |
    ${topic.language} |
    <br/>
</c:forEach>
<form action="getAllSpeakerTopics" method="get">
    <b>Choose topic id to replace -> </b><input name="topic_id"/><br/>
    <input type="submit" value="get speaker's topics"/>
</form>
</body>
</html>
