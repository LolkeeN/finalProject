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
<%if (request.getParameter("firstName") == null && request.getParameter("lastName") == null){
out.println("Welcome, speaker " + request.getAttribute("firstName") + " " + request.getAttribute("lastName"));
}else{
out.println("Welcome, speaker " + request.getParameter("firstName") + " " + request.getParameter("lastName"));
}%>
<hr>
<a href="speakerMeetings.jsp">My meetings</a>
<hr>
<a href="chooseFreeTopic.jsp">Choose free topic</a>
<hr>
<a href="suggestTopic.jsp">Suggest my topic</a>
<hr>
</body>
</html>
