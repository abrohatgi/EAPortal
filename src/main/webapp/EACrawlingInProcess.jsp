<%-- 
    Document   : CrawlingInProcess
    Created on : Feb 6, 2015, 6:39:28 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="org.json.JSONObject"%>
<%@page import="com.nuance.expertassistant.InvokeQACoreAPI"%>
<%@page import="com.nuance.expertassistant.ContentCrawler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<!-- Built with Divshot - http://www.divshot.com -->





<%

    String projectID = request.getParameter("projectID");
    String percentage = "";
    String stage = "";
    
    if(request.getParameter("stage")==null)
    {
        stage = "SCRAPING";
    }
    else
    {
        stage = request.getParameter("stage").toString();
    }
    
    if(request.getParameter("percentage")==null)
    {
        percentage = "0";
    }
    else
    {
        percentage = request.getParameter("percentage").toString();
    }
         
         
        stage = InvokeQACoreAPI.getProjectStatus(projectID);
         
        if(stage.contains("SCRAPING") )
        {
            percentage = "25";
        }
        else if(stage.contains("ANNOTATING") )
        {
            percentage = "40";
        } 
        else if(stage.contains("INDEXING") )
        {
            percentage = "70";
        } 
        else if(stage.contains("CREATED") )
        {
            percentage = "90";
        } 
        else if(stage.contains("READY") )
        {
            percentage = "100";
        }
        else
        {
            percentage = "0";
        }

         if(Integer.valueOf(percentage)>=100)
         {
             response.sendRedirect("EAQuery.jsp?projectID="+projectID);
         }
         
         System.out.println("The percentage is :" + percentage);
             
    
    String paramsStr = "projectID="+projectID+"&percentage="+percentage+"&stage="+stage;
    
%>
<meta http-equiv="refresh" content="3;url=EACrawlingInProcess.jsp?<%=paramsStr%>"/>
<html>
    <head>
        <title>Status</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js" type="text/javascript"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>
         <div class="container"><h1><%out.println(stage); %></h1><div class="progress">
                <div class="progress-bar" style="width: <%=percentage%>%;"></div>
            </div>
             <a class="btn btn-primary" href="EAPortal.jsp">Abort</a>
            
       
    </body>
</html>
        