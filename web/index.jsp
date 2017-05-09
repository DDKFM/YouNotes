<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Simple Notes</title>
    <!--
    Bootstrap
    -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <!--
    jQuery
    -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="index.jsp"></script>
    <%
      if(!LoginServlet.isLoggedIn(request))
          response.sendRedirect("login.jsp");
    %>
  </head>
  <body>
  <h1>HelloWorld</h1>
  <div id="divAddUser">
    <form action="/register" method="POST">
      <div class="form-group">
        <label for="fieldUsername">Benutzername:</label>
        <input type="text" class="form-control" id="fieldUsername">
      </div>

      <div class="form-group">
        <label for="fieldEMail">Email Adresse:</label>
        <input type="email" class="form-control" id="fieldEMail">
      </div>

      <div class="form-group">
        <label for="fieldPassword">Password:</label>
        <input type="password" class="form-control" id="fieldPassword">
      </div>

      <div class="form-group">
        <label for="fieldPasswordRepeat">Password wiederholen:</label>
        <input type="password" class="form-control" id="fieldPasswordRepeat">
      </div>
      <button type="submit" class="btn btn-default">
        Registrieren
      </button>

    </form>
  </div>
  </body>
</html>