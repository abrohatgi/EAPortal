<%-- 
    Document   : EAQuery
    Created on : Feb 18, 2015, 4:27:20 AM
    Author     : abhishek_rohatgi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html lang="en">

    <head>




        <title>Nuance Passage Retrieval</title>
        <meta name="viewport"
              content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="/ExpertAssistant/css/global.css" media="screen, projection"
              rel="stylesheet" type="text/css">
        <link href="/ExpertAssistant/css/global-fonts.css" media="screen, projection"
              rel="stylesheet" type="text/css">
        <link rel="icon" type="image/png" href="/ExpertAssistant/images/favicon.ico" />
        <link rel="shortcut icon" type="image/png" href="/ExpertAssistant/images/favicon.ico" />


        <script src="/ExpertAssistant/js/jquery-1.9.1.js"></script>
        <script src="/ExpertAssistant/js/search.js"></script>
        <script src="/ExpertAssistant/js/app.js"></script>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
      
        
        <style>
  * {
    padding: 0;
    margin: 0;
  }
  .fit { /* set relative picture size */
    max-width: 100%;
    max-height: 100%;
  }
  .center {
    display: block;
    margin-left: auto;
    margin-right: auto;
  }
</style>

<script type="text/javascript" language="JavaScript">
  function set_body_height() { // set body height = window height
    $('body').height($(window).height());
  }
  $(document).ready(function() {
    $(window).bind('resize', set_body_height);
    set_body_height();
  });
</script>

        <script language="javascript">

            function access() {
            <% String projectID = request.getParameter("projectID");%>
                var projID = "<%=projectID%>";
                return projID;
            }

        </script> 

    </head>
    <body id="home" onload="register_handlers()">
          <div id="container">
              <img class="center fit" src="/ExpertAssistant/images/HomeDepotTop.png" alt="" />
</div>
        <div class="searchLayer">
            <div class="container">
                <div class="searchResults"></div>
                <div class="searchBar">
                    <form>
                        <input type="text" autocomplete="off" name="field" id="textInput"
                               value="Hi I'm Nina. What can i help you with ?">
                        <button class="searchClear" id="searchButton" >&nbsp;</button>  
                    </form>
                </div>
            </div>
        </div>
        <div id="container">
        <img class="center fit" src="/ExpertAssistant/images/HomeDepotBottom.png" alt="" />
</div>

    </body>
</html>

