<%-- 
    Document   : CrawlerResult
    Created on : Feb 6, 2015, 8:40:22 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="com.nuance.expertassistant.ContentCrawler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>In-Process</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js" type="text/javascript"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="container"><div class="alert alert-warning alert-dismissable">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <b>Crawling!</b> Please be patient ...
            </div></div>
    </body>
</html>

<%
    
ContentCrawler.crawlWebSite(session.getAttribute("weburl").toString(), session.getAttribute("depth").toString(),
        session.getAttribute("pattern").toString(),session.getAttribute("limit").toString(), 
        session.getAttribute("title").toString());
%>

