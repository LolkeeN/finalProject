<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meetings</title>

    <style>
        button:hover {
            background-color: silver;
        }
    </style>

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
                    <li><a href="CheckRoleAndRedirectServlet">Main page</a> </li>
                    <li><a href="aboutUs.jsp">About us</a></li>
                </ul>
            </div>
        </div>
    </nav>
</div>
<br/>
<div class="container-fluid">
    <form action="login" method="post">
        Your email -> <input name="email"/><br/><br/>
        Your password -> <input type="password" name="password"/><br/><br/>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
<br/>
    <form action="registrationPage.jsp" method="get">
        <button type="submit" class="btn btn-secondary">Registration</button>
    </form>
</div>
</body>
</html>