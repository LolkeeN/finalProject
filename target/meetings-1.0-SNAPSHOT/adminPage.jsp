<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if ((int) request.getSession().getAttribute("role") != 2) {
%>
<jsp:forward page="accessDeniedPage.jsp"></jsp:forward>
<% }
%>
<html>
<head>
    <style>
        button:hover {
            background-color: silver;
        }
    </style>
    <title>adminPage</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
</head>
<body>
<div>
    <nav class="nav navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="loginPage.jsp" class="navbar-brand">Meetings</a>
            </div>

            <div>
                <ul class="nav navbar-nav">
                    <li><a href="CheckRoleAndRedirectServlet">Main page</a></li>
                    <li><a href="aboutUs.jsp">About us</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br/>
<div class="container-fluid">
    <%=("Welcome, admin")%>
    <hr>
    <form action="createMeetingPage.jsp" method="get">
        <button type="submit" class="btn btn-secondary">Create meeting</button>
    </form>
    <form action="createTopicPage.jsp" method="get">
        <button type="submit" class="btn btn-secondary">Create topic</button>
    </form>
    <form action="getAllMeetings" method="get">
        <button type="submit" class="btn btn-secondary">Change topic by speaker</button>
    </form>
    <form action="changeMeetingTimeAndPlacePage.jsp" method="get">
        <button type="submit" class="btn btn-secondary">Change meeting time and place</button>
    </form>
    <form action="createLocation.jsp" method="get">
        <button type="submit" class="btn btn-secondary"> Create Location</button>
    </form>
    <form action="setTopicSpeaker.jsp" method="get">
        <button type="submit" class="btn btn-secondary"> Set topic's speaker</button>
    </form>
    <form action="getAllMeetings" method="get">
        <button type="submit" class="btn btn-secondary"> Get all meetings</button>
    </form>
    <form action="getFutureMeetings" method="get">
        <button type="submit" class="btn btn-secondary"> Get future meetings</button>
    </form>
    <form action="getPastMeetings" method="get">
        <button type="submit" class="btn btn-secondary"> Get past meetings</button>
    </form>
</div>
</body>
</html>