<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  </head>
  <body>


  <div class="jumbotron text-center">
    <h2>Login</h2>
      <form action="login" method="POST">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" value=""><br><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" value=""><br><br>
        <input type="submit" value="Submit">
      </form>
    <a href="addUser.jsp">Register</a>
  </div>

  </body>
</html>