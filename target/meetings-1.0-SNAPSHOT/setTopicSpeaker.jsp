<%--
  Created by IntelliJ IDEA.
  User: vasyl
  Date: 29.05.2021
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Set topic's speaker</title>
</head>
<body>
<form action="bindSpeakerWithTopic" method="post">
    <b>Speaker id -> </b><input name="speaker_id"/><br/>
    <b>Topic id -> </b><input name="topic_id"/><br/>
    <input type="submit" value="Bind them"/>
</form>
</body>
</html>
