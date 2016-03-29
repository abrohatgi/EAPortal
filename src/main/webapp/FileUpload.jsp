<%-- 
    Document   : FileUpload
    Created on : Feb 23, 2015, 2:47:31 PM
    Author     : abhishek_rohatgi
--%>

<%@page import="com.nuance.expertassistant.EmailAnalysis"%>
<%@page import="com.nuance.expertassistant.ReadExcelFile"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    session.setAttribute("ProjectID", request.getParameter("projectID"));
    
    
    System.out.println(" The project id is : " + session.getAttribute("ProjectID"));
     System.out.println(" The Email id is : " + request.getParameter("emailaddr"));
      System.out.println(" The File Name id is : " + request.getParameter("filename"));
    
    
    if(session.getAttribute("ProjectID")!=null && request.getParameter("emailaddr") !=null && request.getParameter("filename")!=null)
    {
       
        ReadExcelFile ref = new ReadExcelFile();
        
        ref.retrieveAnswersandWrite(session.getAttribute("ProjectID").toString(),request.getParameter("filename"));

        EmailAnalysis.mailFile(request.getParameter("filename"), request.getParameter("emailaddr"));
       %> 
       <jsp:forward page="result.jsp?status=evalemailsent" />
       <%
        
    }
    
    %>


<html>
   <head>
        <title>Evaluation</title>
        <meta name="viewport" content="width=device-width">
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
        </style>
    </head>

    <body>
        <div class="container">
            <div class="header">
                <ul class="nav nav-pills pull-right">
                    <li><a href="EASignIn.jsp">Home</a></li>
                    <li class="active"><a href="">Evaluation</a></li>
                    <li><a href="#">Docs</a></li>
                    <li><a href="EAResult.jsp?status=logout">Logout</a></li>
                </ul>
                <h3 class="text-muted">Nina Answers</h3>
            </div>
        <div>
            <h3> Upload your Excel File to Analyze </h3>
            <form action="upload" method="post" enctype="multipart/form-data">
               
                    <input  class="form-control" type="file" name="file" />
                
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="UPLOAD and ANALYZE" />
            </form>
            
             <h3>Acceptable Format for Excel file: </h3>
             <img width="100%" src="/ExpertAssistant/images/ExampleXLS.png">
            
             <h3> OR </h3>
            
             <a class="btn btn-lg btn-primary btn-block" href="result.jsp">Evaluate from Rating DB</a>  

             <h3> OR </h3>
             
             <form class="form-signin" action='FileUpload.jsp' method='POST'>
                 <div class="form-group">
                        <h3>Generate & Email Report from Rating DB</h3>
                        <label class="control-label">Please provide your Email :</label>
                        <div class="controls">
                            <input name="emailaddr" type="text" class="form-control">
                            <label class="control-label">Provide a Filename (without extension) :</label>
                            <div class="controls">
                                <input name="filename" type="text" class="form-control">
                            </div>
                        </div>
                        <input name="projectID" type="hidden" value="<%=request.getParameter("projectID")%>">
                 </div>
                  <h4>Note : The operation may take upto a minute so please be patient.  </h4>
                  <input class="btn btn-lg btn-primary btn-block" type="submit" value="SEND" />
             </form>
             
             
        </div>
   
        </div>
      
    </body>
</html>
