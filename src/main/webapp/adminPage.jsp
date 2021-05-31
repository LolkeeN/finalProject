<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 27.05.2021
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<a href="changeTopicBySpeakerPage.jsp"> Change topic by speaker </a>
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
<a href="getPastMeetings"> Get pst meetings </a>
<hr>

</body>
</html>
