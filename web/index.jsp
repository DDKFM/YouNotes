<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Simple Notes</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" />
    <link rel="apple-touch-icon" href="media/icon-mobile.png"/>
    <!--<link rel="icon" type="image/png" href="media/favicon.png" sizes="32x32">-->

    <!-- WebApp settings -->
      <meta name="mobile-web-app-capable" content="no">
      <meta name="apple-mobile-web-app-capable" content="no">
      <link rel="manifest" href="manifest.json">
      <meta name="YouNote" content="YouNote">

    <!-- Apple iPhone Safari blockieren --->
      <script>(function(a,b,c){if(c in b&&b[c]){var d,e=a.location,f=/^(a|html)$/i;a.addEventListener("click",function(a){d=a.target;while(!f.test(d.nodeName))d=d.parentNode;"href"in d&&(d.href.indexOf("http")||~d.href.indexOf(e.host))&&(a.preventDefault(),e.href=d.href)},!1)}})(document,window.navigator,"standalone")</script>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script type="text/javascript" src="index.jsp"></script>
    <%
      if(!LoginServlet.isLoggedIn(request))
          response.sendRedirect("login.jsp");
      else
          response.sendRedirect("dash.jsp");
    %>
  </head>
  <body>
  </body>
</html>
