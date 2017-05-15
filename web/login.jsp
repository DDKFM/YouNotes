<!--<%@ page import="de.mschaedlich.servlets.LoginServlet" %>-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.js"></script>
    <link rel="stylesheet" href="css/materialize.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
     <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script>
        $(document).ready(function(){
            $('.modal').modal();
        });
    </script>
    <%
        if(LoginServlet.isLoggedIn(request))
            response.sendRedirect("index.jsp");
    %>
</head>
<body>
<div class="container">
<div id="content">

<h4 id="loginHeader">LOGIN</h4>

<form action="login" method="POST">
    Benutzername: <input type="text" name="username"/><br/>
    Passwort: <input type="password" name="password"/>
    <button type="submit" class="btn btn-default">Absenden</button>
</form>

<a class="btn-floating btn-large waves-effect waves-light teal" id="register" href="#modal1">
  <i class="material-icons">add</i>
</a>

<!-- Modal Structure -->
<div id="modal1" class="modal">
<div id="divAddUser" class="modal-content">
  <form action="register" method="POST">
    <h3>NEU?!</h3>
    <div class="form-group">
      <label for="fieldUsername">Benutzername:</label>
      <input type="text" class="form-control" id="fieldUsername" name="username">
    </div>

    <div class="form-group">
      <label for="fieldEMail">Email Adresse:</label>
      <input type="email" class="form-control" id="fieldEMail" name="email">
    </div>

    <div class="form-group">
      <label for="fieldPassword">Password:</label>
      <input type="password" class="form-control" id="fieldPassword" name="password">
    </div>

    <div class="form-group">
      <label for="fieldPasswordRepeat">Password wiederholen:</label>
      <input type="password" class="form-control" id="fieldPasswordRepeat" name="repeatPassword">
    </div>
    <button type="submit" class="btn btn-default">
      Registrieren
    </button>

  </form>
</div>

</div>
</div>
</div>
</body>
</html>
