<%-- 
    Document   : WebCrawlerUI
    Created on : Feb 6, 2015, 5:48:17 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="com.nuance.expertassistant.WebSiteDownloader"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.nuance.expertassistant.InvokeQACoreAPI"%>
<%@page import="com.nuance.expertassistant.HTTPConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.nuance.expertassistant.ContentCrawler"%>

<!DOCTYPE html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%

    if (request.getParameter("projectName") == null || request.getParameter("projectName").isEmpty() || request.getParameter("projectName").equalsIgnoreCase("")) {

%>

<html>

    <div class="container">
        <div class="header">
            <ul class="nav nav-pills pull-right">
                <li><a href="EASignIn.jsp">Home</a></li>
                <li class="active"><a href="">Create Project</a></li>
                <li><a href="#">Docs</a></li>
                <li><a href="EAResult.jsp?status=logout">Logout</a></li>
            </ul>
            <h3 class="text-muted">Nina Answers</h3>
        </div>


        <head>
            <title>New Page</title>
            <meta name="viewport" content="width=device-width">
            <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
            <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">
            <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
            <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
        </head>

        <body>
            <div class="container">
                <h1><b class="text-left">   Create Project </b></h1>
                <p>Note : Is its a LOCAL_WEB type project you might have to wait a while on this page while crawling takes place in the background. 
                    After the crawler has finished crawling you will automatically be re-directed !</p>
            </div>
            <div class="container">
                <form class="form-signin" action='EACreateProject.jsp' method='POST'>
                    <div class="form-group">
                        <h1>Project Configuration :</h1>
                        <label class="control-label">Project Name :</label>
                        <div class="controls">
                            <input name="projectName" type="text" class="form-control">
                            <label class="control-label">Project ID :</label>
                            <div class="controls">
                                <input name="projectID" type="text" class="form-control">
                                <label class="control-label">Project Type :</label>
                                <div class="controls">
                                    <input name="projectType" type="text" class="form-control">
                                    <h1>Crawler Configuration :</h1>
                                    <label class="control-label">URL Name :</label>
                                    <div class="controls">
                                        <input name="weburl" type="text" class="form-control">
                                        <label class="control-label"> Crawl Pattern :</label>
                                        <div class="controls">
                                            <input name="pattern" type="text" class="form-control">
                                            <label class="control-label">Crawl Depth :</label>
                                            <div class="controls">
                                                <input name="depth" type="text" class="form-control">
                                                <label class="control-label">Max Pages :</label>
                                                <div class="controls">
                                                    <input name="limit" type="text" class="form-control">
                                                   
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary">Create</button>
                            </form></div>
                        </body>

                        </html>

                        <%} else {
                            //ContentCrawler.crawlWebSite(request.getParameter("weburl"), request.getParameter("depth"),request.getParameter("pattern"),request.getParameter("limit"), request.getParameter("title"));

                            /*String content = request.getParameter("weburl") +"|"+ request.getParameter("pattern")+"|"
                             +request.getParameter("depth")+"|"+request.getParameter("limit") +"|"+ request.getParameter("title") ;
                             HTTPConnection.sendPUT(content);*/
                            String ProjectName = request.getParameter("projectName");
                            String ProjectID = request.getParameter("projectID");
                            String ProjectType = request.getParameter("projectType");
                            String URL = request.getParameter("weburl");
                            String CrawlPattern = request.getParameter("pattern");
                            String CrawlDepth = request.getParameter("depth");
                            String URLLimit = request.getParameter("limit");

                            if (CrawlDepth == null || CrawlDepth.equalsIgnoreCase("")) {
                                CrawlDepth = "0";
                            }

                            if (URLLimit == null || URLLimit.equalsIgnoreCase("")) {
                                URLLimit = "1";
                            }

                            //WebSiteDownloader wsdl = new WebSiteDownloader();

                            
                       
                           // wsdl.initDownloading(ProjectID, URL, CrawlPattern, Integer.parseInt(CrawlDepth), Integer.parseInt(URLLimit));
                            String ACK = InvokeQACoreAPI.createNewProject(ProjectName, ProjectID, ProjectType, URL, CrawlPattern, CrawlDepth, URLLimit);

                            if (ACK.equalsIgnoreCase("OK")) {


                        %>
                        <jsp:forward page="EAPortal.jsp"/>     
                       
                        <%

                                }
                            }
                        %>
