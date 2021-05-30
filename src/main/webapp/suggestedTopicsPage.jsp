<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Suggested Topics</title>
</head>
<body>
<jsp:useBean id="suggestedTopics" scope="request" type="java.util.List"/>
<c:forEach var="topic" items="${suggestedTopics}">
    ${topic.id} |
    ${topic.name} |
    ${topic.date} |
    ${topic.description} |
    <br/>
</c:forEach>
<form action="bindSuggestedTopicWithMeeting" method="get">
    <b>Choose topic id -> </b><input name="topic_id"/><br/>
    <b>Choose meeting name -> </b><input name="meeting_name"/><br/>
    <input type="submit" value="Bind topic to meeting"/>
</form>
</body>
</html>
