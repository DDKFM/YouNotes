<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<form action="/login" method="POST">
    Benutzername: <input type="text" name="username"/><br/>
    Passwort: <input type="password" name="password"/>
    <input type="submit" value="Absenden"/>
</form>

</body>
</html>