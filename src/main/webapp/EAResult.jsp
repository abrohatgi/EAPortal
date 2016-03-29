<%-- 
    Document   : Result
    Created on : Apr 30, 2014, 3:17:01 AM
    Author     : abhishekrohatgi
--%>

<%@page import="com.nuance.expertassistant.InvokeQACoreAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    String status = request.getParameter("status");
    String resultMessage = "Re-directing...";
    
    if(status.equalsIgnoreCase("createknowledge"))
    {
        resultMessage = "Creating knowledge-Base ! Redirecting back ....";
    }
    if(status.equalsIgnoreCase("invalidaccess"))
    {
        resultMessage = "Login Un-successful ! Please try Again ....";
    }
     if(status.equalsIgnoreCase("deleteProject"))
    {
        InvokeQACoreAPI.deleteProject(request.getParameter("projectID"));
        resultMessage = "Deleting Project. Please wait ...";
    }
    else if (status.equalsIgnoreCase("logout"))
    {
        session.setAttribute("isLoggedIn", "false");
        response.sendRedirect("EASignIn.jsp");
    }
    else if (status.equalsIgnoreCase("analysisFailed"))
    {
        resultMessage = "Evaluation Failed! please make sure your project exist and XLS format is acceptable ... ";
    }
     else if (status.equalsIgnoreCase("noRatedQuestionFound"))
    {
        resultMessage = "Evaluation Failed! Looks like we could not retrieve any rated questions from the DB  ... ";
    }
     else if (status.equalsIgnoreCase("serverError"))
    {
        resultMessage = "A server error has occured please try again ! ";
    }
      else if (status.equalsIgnoreCase("evalemailsent"))
    {
        resultMessage = "Email Sent Successfully ! ";
    }

    %>
    <meta http-equiv="refresh" content="3;url=EASignIn.jsp" />
<html>
    <head>
        <title></title>
    </head>
    <body>
        <p>
            <span style="color:#008000;"><%=resultMessage%></span></p>
        
    </body>
    </html>
