<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create meeting</title>
</head>
<body>
<form action="createMeeting" method="get">
    <b>Name -> </b><input name="name"/><br/>
    <b>Date -> </b><input name="date"/><br/>
    <b>Location id -> </b><input name="location_id"/><br/>
    <b>language -> </b><input name="language"/><br/>
    <input type="submit" value="Create meeting"/>
</form>
</body>
</html>
