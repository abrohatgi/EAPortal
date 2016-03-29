<%-- 
    Document   : SignIn
    Created on : Feb 17, 2015, 10:24:21 AM
    Author     : abhishek_rohatgi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>

<%

    if (session.getAttribute("isLoggedIn")!=null && session.getAttribute("isLoggedIn").toString().equalsIgnoreCase("true"))
            {%>
                 <jsp:forward page="EAPortal.jsp"/>
           <% }
    else if (request.getParameter("email") == null || request.getParameter("password") ==null) {

%>

<html>
  <head>
    <title>Sign In</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js" type="text/javascript"></script>
    <script src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #eee;
      }
      
      .form-signin {
        max-width: 330px;
        padding: 15px;
        margin: 0 auto;
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin .checkbox {
        font-weight: normal;
      }
      .form-signin .form-control {
        position: relative;
        font-size: 16px;
        height: auto;
        padding: 10px;
        -webkit-box-sizing: border-box;
           -moz-box-sizing: border-box;
                box-sizing: border-box;
      }
      .form-signin .form-control:focus {
        z-index: 2;
      }
      .form-signin input[type="text"] {
        margin-bottom: -1px;
        border-bottom-left-radius: 0;
        border-bottom-right-radius: 0;
      }
      .form-signin input[type="password"] {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
      }
    </style>
  </head>
  <body>
    <div class="container">

      <form class="form-signin">
        <h2 class="form-signin-heading"> Nina Answers - Login</h2>
        <input type="text" name="email" class="form-control" placeholder="Email address">
        <input type="password" name="password" class="form-control" placeholder="Password">
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign In</button>
      </form>

    </div> <!-- /container -->

  </body>
</html>
<%

    }
    else
    {
        if(request.getParameter("email").equalsIgnoreCase("eauser@nuance.com") && request.getParameter("password").equalsIgnoreCase("eauser"))
        {
            session.setAttribute("isLoggedIn", "true");
            response.sendRedirect("EAPortal.jsp");
        }
        else
        {
            %>
            <jsp:forward page="EAResult.jsp?status=invalidaccess"/>
            <%
        }
        
    }

%>