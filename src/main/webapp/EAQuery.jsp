<%-- 
    Document   : EAQuery
    Created on : Feb 18, 2015, 4:27:20 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html lang="en">

    <head>

        <title>Nina Answers</title>
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
        <style type="text/css">
            /* Space out content a bit */
            body {
                padding-top: 20px;
                padding-bottom: 20px;
            }
            /* Everything but the jumbotron gets side spacing for mobile first views */
            .header, .marketing, .footer {
                padding-left: 15px;
                padding-right: 15px;
            }
            /* Custom page header */
            .header {
                // border-bottom: 1px solid #e5e5e5;
            }
            /* Make the masthead heading the same height as the navigation */
            .header h3 {
                margin-top: 0;
                margin-bottom: 0;
                line-height: 40px;
                padding-bottom: 19px;
            }
            /* Custom page footer */
            .footer {
                padding-top: 19px;
                color: #777;
                border-top: 1px solid #e5e5e5;
            }
            /* Customize container */
            @media(min-width: 768px) {
                .container {
                    max-width: 730px;
                }
            }
            .container-narrow > hr {
                margin: 30px 0;
            }
            /* Main marketing message and sign up button */
            .jumbotron {
                text-align: center;
                border-bottom: 1px solid #e5e5e5;
            }
            .jumbotron .btn {
                font-size: 21px;
                padding: 14px 24px;
            }
            /* Supporting marketing content */
            .marketing {
                margin: 40px 0;
            }
            .marketing p + h4 {
                margin-top: 28px;
            }
            /* Responsive: Portrait tablets and up */
            @media screen and(min-width: 768px) {
                /* Remove the padding we set earlier */
                .header, .marketing, .footer {
                    padding-left: 0;
                    padding-right: 0;
                }
                /* Space out the masthead */
                .header {
                    margin-bottom: 30px;
                }
                /* Remove the bottom border on the jumbotron for visual effect */
                .jumbotron {
                    border-bottom: 0;
                }
            }

            #navi, 
            #infoi {
                width: 100%;
                height: 20px;
                position: absolute;
                top: 0;
                left: 0;
                padding-left: 10px;

            }

            #infoi {
                z-index: -10;
            }

        </style>

        <script language="javascript">

            function access() {
            <% String projectID = request.getParameter("projectID");%>
                var projID = "<%=projectID%>";
                return projID;
            }

        </script> 

    </head>
    <div class="container">

        <div id="navi">
            <ul class="nav nav-pills pull-right">


                <li><a href="EASignIn.jsp">Home</a></li>
                <li class="active"><a href="">Query Engine</a></li>
                <li><a href="#">Docs</a></li>
                <li><a href="EAResult.jsp?status=logout">Logout</a></li>
            </ul>
        </div>
        <div id="infoi" align="left">
            <h4 class="text-muted"> Nina Answers : <%=request.getParameter("projectName")%> </h4>
        </div>

    </div>
    <body id="home" onload="register_handlers()">
        <div class="container">
            <div class="searchLayer">
                <div class="searchResults"></div>
                <div class="searchBar">
                    <form>
                        <input type="text" autocomplete="off" name="field" id="textInput"
                               value="   Hi I'm Nina. What can i help you with ?">
                        <button class="searchClear" id="searchButton" >&nbsp;</button>  
                    </form>
                </div>
            </div>
        </div>
        
        <%
            String siteURL = request.getParameter("projectURL");
            
            if(siteURL.contains("homedepot.com"))
            {
                siteURL = "http://www.homedepot.ca/";
            }
            else if (siteURL.contains("/enterprise/usaa/htmlfiles"))
            {
                siteURL = "https://www.usaa.com/inet/pages/our-products-main?wa_ref=pub_global_products_viewall";
            }
             System.out.println(" The base is :" + siteURL);
        %>

        <iframe width="100%" height=900px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="<%=siteURL%>" id="proxy_frame" frameborder="0"></iframe>

    </body>
</html>

