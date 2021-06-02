<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%if ((int)request.getSession().getAttribute("role") != 2){
%>
<jsp:forward page="accessDeniedPage.jsp"></jsp:forward>
<% }
%>
<html>
<head>
    <title>adminPage</title>
</head>
<body>
<%=("Welcome, admin")%>
<hr>
<a href="createMeetingPage.jsp"> Create meeting </a>
<hr>
<a href="createTopicPage.jsp"> Create topic </a>
<hr>
<a href="getAllMeetings"> Change topic by speaker </a>
<hr>
<a href="changeMeetingTimeAndPlacePage.jsp"> Change meeting time and place </a>
<hr>
<a href="createLocation.jsp"> Create Location </a>
<hr>
<a href="setTopicSpeaker.jsp"> Set topic's speaker </a>
<hr>
<a href="getAllMeetings"> Get all meetings </a>
<hr>
<a href="getFutureMeetings"> Get future meetings </a>
<hr>
<a href="getPastMeetings"> Get past meetings </a>
<hr>

</body>
</html>
