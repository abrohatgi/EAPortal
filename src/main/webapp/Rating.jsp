<%-- 
    Document   : Rating
    Created on : Apr 2, 2015, 1:13:21 AM
    Author     : abhishek_rohatgi
--%>


<%@page import="com.nuance.expertassistant.db.QueryDB"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.nuance.expertassistant.InvokeQACoreAPI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>

<%

    if (request.getParameter("projectID") != null && request.getParameter("questionText") != null) {
        Map<String, String[]> parameters = request.getParameterMap();
        String question = request.getParameter("questionText");
        String projectID = request.getParameter("projectID");

        System.out.println(" The respone count is :" + request.getParameter("responseCount"));

        int responseCount = Integer.parseInt(request.getParameter("responseCount"));
        for (int i = 1; i < responseCount + 1; i++) {
            if (!request.getParameter("rating" + i).contains("No Rating") && request.getParameter("rating" + i) != null) {
                QueryDB.insertQARatingData(question, request.getParameter("response" + i), request.getParameter("rating" + i), projectID, null, null);
            }
        }
    }
    /*    
     Map<String, String[]> parameters = request.getParameterMap();
     for(String parameter : parameters.keySet()) {
    
     System.out.println(" ********** ");
     System.out.println(" The paremeter : " + parameter);
     System.out.println(" The paremeter value : " + request.getParameter(parameter));
     System.out.println(" ********** ");
    
     /*if(parameter.toLowerCase().startsWith("question")) {
     String[] values = parameters.get(parameter);
     //your code here
     }*/
//}

%>

<html>

    <head>
        <title>Rating</title>
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
            <ul class="nav nav-pills pull-right">
                <li>
                    <a href="EASignIn.jsp">Home</a>
                </li>
                <li class="active">
                    <a href>Rate</a>
                </li>
                <li>
                    <a href="#">Docs</a>
                </li>
                <li>
                    <a href="EAResult.jsp?status=logout">Logout</a>
                </li>
            </ul>
            <h3 class="text-muted">Nina Answers</h3>
        </div>
        <div class="container">
            <form action='Rating.jsp' method='POST'>
                <div class="form-group">
                    <label class="control-label">Ask a Question and Rate its response.</label>

                    <div class="controls">
                        <input name="question" type="text" class="form-control">
                         <input type="hidden" name="projectID" value="<%=request.getParameter("projectID")%>">
                    </div>

                    <dl>
                        <dt>No Rating</dt>
                        <dd>The Response is not at rated yet.</dd>
                        <dt>Irrelevant</dt>
                        <dd>The Response is not at all related to Question.</dd>
                        <dt>Relevant</dt>
                        <dd>The Response is somewhat related to the Question.</dd>
                        <dt>Good</dt>
                        <dd>Perfect response for the Question. </dd>
                    </dl>
                    <button type="submit" class="btn btn-primary">Ask</button>
                </div>
            </form>

        </div>

        <form action='Rating.jsp' method='POST'>

            <%    if (request.getParameter("question") == null) {

                } else {
                    String contextID = InvokeQACoreAPI.getContextID(request.getParameter("projectID")).replaceAll("\n", "");
                    String question = request.getParameter("question");
                    String answerString = InvokeQACoreAPI.getAnswer(contextID, request.getParameter("projectID"), question);

                    System.out.println(" the Answer String is : " + answerString);

                    if (answerString.contains("HTTP ERROR 500")) {
            %>
            <jsp:forward page="EAResult.jsp?status=serverError"/>
            <%
                }

            %>

            <ul class="list-group">
                <%            JSONArray jsonArray = new JSONArray(answerString);

                    int ResponseCount = jsonArray.length();
                    
                    if(ResponseCount>5)
                    {
                      ResponseCount = 5;  
                    }

                    for (int i = 0; i < ResponseCount; i++) {
                        JSONObject job = jsonArray.getJSONObject(i);
                        String answerText = job.getJSONObject("answer").getString("text");
                        String evidence = job.getJSONObject("answer").getJSONObject("evidence").getString("text");
                        int confidence = job.getJSONObject("answer").getInt("confidence");
                        Double score = job.getJSONObject("answer").getDouble("score");

                        System.out.println("ANSWER TEXT (" + i + "):[" + answerText + "]");
                        System.out.println("EVIDENCE TEXT (" + i + "):[" + evidence + "]");
                        System.out.println("CONFIDENCE (" + i + "):[" + confidence + "]");
                        System.out.println("SCORE (" + i + "):[" + score + "]");
                        String currentRating = null;

                %>
                <li class="list-group-item">

                    <h3>Answer [<%=i + 1%>]</h3>  <%=answerText%>
                    <h3>Confidence</h3>  <%=confidence%>
                    <h3>Score</h3>  <%=score%>
                    <h3>Evidence</h3>  <%=evidence%>
                    <%
                    
                    currentRating = QueryDB.fetchRating(request.getParameter("question"), answerText, request.getParameter("projectID"));
                    
                    if(currentRating==null)
                    {
                        currentRating = "Not Currently Rated";
                    }
                    
                    %>
                    <h3>Current Rating </h3> <%=currentRating%>
                    
                    
                    <h3>Rate </h3>
                    <input type="hidden" name="response<%=i + 1%>" value="<%=answerText%>">
                    <select name = "rating<%=i + 1%>" type="text" class="form-control">
                        <option>No Rating</option>
                        <option>Good</option>
                        <option>Relevant</option>
                        <option>Irrelevant</option>
                    </select>

                </li>
                <%                       System.out.println("*********************************************");
                    }

                %>

            </ul>
            <input type="hidden" name="projectID" value="<%=request.getParameter("projectID")%>">
            <input type="hidden" name="responseCount" value="<%=ResponseCount%>">
            <input type="hidden" name="questionText" value="<%=request.getParameter("question")%>">
            <button type="submit" class="btn btn-block btn-success btn-lg">Register</button>
        </form>
        <%            }


        %>

    </body>
</html>