<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Simple Notes</title>
    <!--
    Bootstrap
    -->
    <link rel="manifest" href="manifest.json">
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
  </body>
</html>
