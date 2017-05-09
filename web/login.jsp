<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<>
<form action="/login" method="POST">
    Benutzername: <input type="text" name="username"/><br/>
    Passwort: <input type="password" name="password"/>
    <input type="submit" value="Absenden"/>
</form>


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
