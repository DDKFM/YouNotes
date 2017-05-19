<%@ page import="de.mschaedlich.servlets.LoginServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
      <head>
        <meta charset="UTF-8">
        <title>youNOTE</title>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.js"></script>
        <link rel="stylesheet" href="css/materialize.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0" />
        <link rel="apple-touch-icon" href="media/icon-mobile.png"/>
        <!--<link rel="icon" type="image/png" href="media/favicon.png" sizes="32x32">-->

     	<!-- WebApp settings -->
         <meta name="mobile-web-app-capable" content="yes">
         <meta name="apple-mobile-web-app-capable" content="yes">
         <link rel="manifest" href="manifest.json">
         <meta name="YouNote" content="YouNote">

       <!-- Apple iPhone Safari blockieren --->
       <script>(function(a,b,c){if(c in b&&b[c]){var d,e=a.location,f=/^(a|html)$/i;a.addEventListener("click",function(a){d=a.target;while(!f.test(d.nodeName))d=d.parentNode;"href"in d&&(d.href.indexOf("http")||~d.href.indexOf(e.host))&&(a.preventDefault(),e.href=d.href)},!1)}})(document,window.navigator,"standalone")</script>

        <script>
                $(document).ready(function(){
                $('.modal').modal();
                $(".button-collapse").sideNav();
                loadNotices();
            });
            function loadNotices() {
                $("#notePanel").empty();
                console.log("LoadNotices");
                $.ajax({
                    type: "POST",
                    url: "notice",
                    data: "requested=all",
                    error: function(data) {
                        console.log("Error: " + data);
                    },
                    success: function(data) {
                        console.log(data);
                        var array = data.data;
                        for(var noticeIndex = 0 ; noticeIndex < array.length ; noticeIndex++) {
                            var notice = array[noticeIndex];
                            console.log(notice);
                            var div = "<div class=\"col s12 m6 13\">";
                            div += "<div class=\"noteContainer\" id=\"noteContainer_" + notice.id + "\">";
                            div += "<a class=\"btn-floating btn-small teal del\"><i class=\"material-icons\" id=\"" + notice.id + "\">delete</i></a>";
                            div += "<h5 id=\"NoteTitle\">" + notice.title + "</h5>";
                            div += "<div id=\"NoteContent\">";
                            div += "<p>" + notice.content + "</p>";
                            div += "</div></div></div>";

                            $("#notePanel").append(div);
                            $("#noteContainer_" + notice.id + " .material-icons").click(function(event) {
                                var noticeId = $(event.target).attr("id");
                                $.ajax({
                                    type: "POST",
                                    url: "notice",
                                    data: "requested=remove&noticeID=" + noticeId,
                                    complete: function(data) {
                                        $("#noteContainer_" + noticeId).fadeOut("slow");
                                        //loadNotices();
                                    }
                                });
                            })
                        }
                    }
                })
            }
        </script>
    <script type="text/javascript" src="login.jsp"></script>
        <%
          if(!LoginServlet.isLoggedIn(request))
              response.sendRedirect("login.jsp");
        %>
  </head>

  <body>

    <div class="navbar-fixed">
      <nav>
        <div class="nav-wrapper teal">
          <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
          <a href="logout" class="brand-logo right"><i class="material-icons">power_settings_new</i></a>
          <ul class="left hide-on-med-and-down">
            <li class="active"><a href="dash.jsp">DASH</a></li>
            <li><a href="#modal1">NEU</a></li>
    </ul>
    <ul class="side-nav" id="mobile-demo">
        <li>Menü</li>
        <li><a href="dash.jsp">DASH</a></li>
        <li><a href="#modal1">NEU</a></li>
        <li><a href="logout"><i class="material-icons">power_settings_new</i> LOGOUT</a></li>
    </ul>
    </div>
    </nav>
    </div>


<!-- Max kannst es mal anschauen ... sieht auch gut aus
  <div class="fixed-action-btn toolbar">
    <a class="btn-floating btn-large teal">
      <i class="large material-icons">mode_edit</i>
    </a>
    <ul>
      <li class="waves-effect waves-light"><a href="#!"><i class="material-icons">insert_chart</i></a></li>
      <li class="waves-effect waves-light"><a href="#!"><i class="material-icons">format_quote</i></a></li>
      <li class="waves-effect waves-light"><a href="#!"><i class="material-icons">publish</i></a></li>
      <li class="waves-effect waves-light"><a href="#!"><i class="material-icons">attach_file</i></a></li>
    </ul>
  </div> -->

<div id="container1">
    <div id="SpaceTopDash">

        <div class="row">
          <div class="col s12"><h4 id="header">DASHBOARD</h4></div>
        </div>

        <!--Content, hier sollen die Notizen stehen-->
        <div class="row" id="notePanel"> <!--noteContainer soll dynamisch mit Daten aus der DB erzeugt werden-->
          <!-- MenueBar
          <div id="modalMenueBar" class="modal">
              <div class="modal-content horizontal">
                <a class="btn-flat">löschen</a>
                <a class="btn-flat">bearbeiten</a>
              </div>
          </div>-->

        </div>

   </div>
</div>

<a class="btn-floating btn-large teal" id="add" href="#modal1">
    <i class="large material-icons">mode_edit</i>
</a>

  <div id="modal1" class="modal">
      <div id="addnote" class="modal-content">
        <h4 id="loginHeader">NOTIZ</h4>

          <form action="notice?requested=add" method="POST">

              <label for="fieldTitleNote">Titel</label>
              <input type="text" name="title"/><br/>
              <label for="fieldNote">Notiz</label>
              <textarea id="NoteField" class="materialize-textarea" name="content"></textarea>
              <button type="submit" class="btn btn-default" onclick="Materialize.toast('GESPEICHERT', 4000)">speichern</button>
              <a class="btn btn-default" href="dash.jsp">zurück</a>
          </form>
      </div>
    </div>


  </body>
</html>
