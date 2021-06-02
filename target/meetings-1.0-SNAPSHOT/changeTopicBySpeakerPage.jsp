<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Change topic by speaker</title>
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
<form action="changeTopicBySpeaker" method="get">
    <b>Choose new topic id -> </b><input name="topic_id"/><br/>
    <input type="submit" value="Change topic"/>
</form>
</body>
</html>
