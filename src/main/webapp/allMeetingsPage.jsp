<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>All meetings</title>
</head>
<body>
<jsp:useBean id="meetings" scope="request" type="java.util.List"/>
<c:forEach var="meeting" items="${meetings}">
    ${meeting.id} |
    ${meeting.name} |
    ${meeting.date} |
    ${meeting.language} |
    regis ${meeting.registeredUsers} |
    partis ${meeting.participantsCount} |
    <br/>
</c:forEach>
<form action="getMeetingRegisteredAndParticipantsCount" method="get">
    <b>Choose meeting id to get registered and participant users count -> </b><input name="meeting_id"/><br/>
    <input type="submit" value="get registered and participants count"/>
</form>
<form action="getMeetingsTopics" method="get">
    <b>Choose meeting id change it's topic -> </b><input name="meeting_id"/><br/>
    <input type="submit" value="get all meeting's topics"/>
</form>
<form action="sortMeetingsByParticipantsCount" method="get">
    <input type="submit" value="sort meetings by participants"/>
</form>
<form action="sortMeetingsByRegisteredCount" method="get">
    <input type="submit" value="sort meetings by registered count"/>
</form>
<form action="sortMeetingsByDate" method="get">
    <input type="submit" value="sort meetings by date"/>
</form>
<form action="adminPage.jsp" method="get">
    <input type="submit" value="go back"/>
</form>
</body>
</html>
