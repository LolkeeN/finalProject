<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%if ((int)request.getSession().getAttribute("role") != 3){
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
    <title>Speaker Page</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
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
                    <li><a href="CheckRoleAndRedirectServlet">Main page</a> </li>
                    <li><a href="aboutUs.jsp">About us</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br/>
<%="Speaker page"%>
<hr>
<div class="container-fluid">

<form action="speakerMeetings" method="get">
    <button type="submit" class="btn btn-secondary">get your topics</button>
</form>
<hr>
<form action="getFreeTopics" method="get">
    <button type="submit" class="btn btn-secondary">show free topics</button>
</form>
<hr>
<form action="suggestATopic.jsp" method="get">
    <button type="submit" class="btn btn-secondary">suggest a topic</button>
</form>
<hr>
</div>
</body>
</html>
