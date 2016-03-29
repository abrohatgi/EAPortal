<%-- 
    Document   : EAPortal.jsp
    Created on : Feb 17, 2015, 10:23:36 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.nuance.expertassistant.InvokeQACoreAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<meta http-equiv="refresh" content="5;url=EAPortal.jsp" />
<html>
    <head>
        <title>Expert Assistant</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js" type="text/javascript"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
        <style type="text/css">
            /* Space out content a bit */
            body {
                padding-top: 20px;
                padding-bottom: 20px;
            }

            /* Everything but the jumbotron gets side spacing for mobile first views */
            .header,
            .marketing,
            .footer {
                padding-left: 15px;
                padding-right: 15px;
            }

            /* Custom page header */
            .header {
                border-bottom: 1px solid #e5e5e5;
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
            @media (min-width: 768px) {
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
            @media screen and (min-width: 768px) {
                /* Remove the padding we set earlier */
                .header,
                .marketing,
                .footer {
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
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <ul class="nav nav-pills pull-right">
                    <li class="active"><a href="EASignIn.jsp">Home</a></li>
                    <li><a href="#">Docs</a></li>
                    <li><a href="EAResult.jsp?status=logout">Logout</a></li>
                </ul>
                <h3 class="text-muted">Nina Answers by Nuance</h3>
            </div>

            <div class="jumbotron">
                <h1>Developer Center</h1>
                <p class="lead"> Simply create your own project by entering relevant configuration or if you have already created one launch the Query Engine from below. Currently Expert Assistant only takes the Website URL as input. More coming soon... </p>
                <p><a class="btn btn-lg btn-success" href="EACreateProject.jsp">Launch</a></p>
            </div>

            <div class="row marketing">
                <div class="col-lg-6">
                    <%

                        JSONObject object = InvokeQACoreAPI.getProjectList();

                        if (object != null) {

                            JSONArray projList = object.getJSONArray("response");

                            for (int i = 0; i < projList.length(); i++) {

                                String projectName = projList.getJSONObject(i).get("name").toString().toUpperCase();
                                String projectType = projList.getJSONObject(i).get("type").toString().toUpperCase();
                                String projectID = projList.getJSONObject(i).get("projectId").toString();
                                String projectURL = projList.getJSONObject(i).getJSONObject("documentBase").get("url").toString();
                                if(projectType.equalsIgnoreCase("LOCAL_WEB"))
                                {
                                    //if(projList.getJSONObject(i).getJSONObject("documentBase").get("resourceBaseUrl")!=null)
                                    if(projList.getJSONObject(i).getJSONObject("documentBase").has("resourceBaseUrl"))
                                    {
                                    projectURL = projList.getJSONObject(i).getJSONObject("documentBase").get("resourceBaseUrl").toString();
                                    }
                                    else if(projectName.contains("USAA"))
                                    {
                                     projectURL = "https://www.usaa.com/inet/pages/our-products-main?wa_ref=pub_global_products_viewall&akredirect=true";   
                                    }
                                    else
                                    {
                                        projectURL = "http://www.nuance.com/index.htm";   
                                    }
                                }
                                
                                String projectStage = InvokeQACoreAPI.getProjectStatus(projectID);
                                
                    %>

                    <h4><%out.println("Project : " + projList.getJSONObject(i).get("name").toString().toUpperCase());%></h4>
                    <p><%out.println("Type : " + projList.getJSONObject(i).get("type").toString().toUpperCase());%></p> 
                    <p><%out.println("ID : " + projList.getJSONObject(i).get("id").toString().toUpperCase());%></p> 
                    <p><%out.println("Status : " + projectStage); %></p> 

                    <%
                        if (projectStage.contains("READY")) {
                    %>
                    <p><a class="btn btn-lg btn-success" href="EAResult.jsp?status=deleteProject&projectID=<%=projectID%>">Delete</a>   
                     <a class="btn btn-lg btn-success" href="NiKQueryUI.jsp?projectID=<%=projectID%>&AMP;projectName=<%=projectName%>&AMP;projectURL=<%=projectURL%>">Query</a></p>

                    <%
                    } else if (projectStage.contains("ERROR") || projectStage.contains("?")  ) {
                    %>
                    <p><a class="btn btn-lg btn-success" href="EAResult.jsp?status=deleteProject&projectID=<%=projectID%>">Delete</a>  </p>
                    <%
                                }
                            }
                        }


                    %>

                </div>
            </div>

            <div class="footer">
                <p>&copy; Nuance 2015</p>
            </div>

        </div> <!-- /container -->
    </body>
</html>
