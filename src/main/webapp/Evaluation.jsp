<%-- 
    Document   : Evaluation
    Created on : Apr 2, 2015, 7:26:01 AM
    Author     : abhishek_rohatgi
--%>

<%@page import="com.cedarsoftware.util.io.JsonReader"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.nuance.expertassistant.db.QueryDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (request.getParameter("projectID") != null && request.getParameter("DATE") != null) {
        
        String DATE = request.getParameter("DATE");
        DATE = DATE.replaceAll("%20", " ");
        
        
        
        String Result = QueryDB.fetchResult4date(DATE);
        System.out.println(" The Result fetched is : " + Result );
        ArrayList<HashMap<String, String>> EvalArray = (ArrayList<HashMap<String, String>>) JsonReader.jsonToJava(Result);
        
%>

<html>
    <head>
        <title>Evaluation</title>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
        <script src='/ExpertAssistant/js/Chart.min.js'></script>
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
                    <li class="active"><a href="">Analysis</a></li>
                    <li><a href="#">Docs</a></li>
                    <li><a href="EAResult.jsp?status=logout">Logout</a></li>
                </ul>
                <h3 class="text-muted">Nina Answers</h3>
            </div>
        </div>

        <div class="jumbotron">

            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Query </th>
                        <th>Expected Answer</th>
                        <th>       Passage Found</th>
                        <th>       Passage Rank</th>
                        <th>       Passage Length</th>
                    </tr>
                </thead>
                <tbody>
                    <%                        int Total = EvalArray.size();
                        int highestRank = 0;
                        int numFound = 0;
                        int numR1 = 0;
                        int numR2 = 0;
                        int numR3 = 0;
                        int numR6 = 0;
                        int numR11 = 0;
                        int numR16 = 0;

                        for (int i = 0; i < EvalArray.size(); i++) {
                            /*System.out.println("" + EvalArray.get(i).get("Query"));
                             System.out.println("" + EvalArray.get(i).get("ExpectedResult"));
                             System.out.println("" + EvalArray.get(i).get("Found"));
                             System.out.println("" + EvalArray.get(i).get("Rank"));
                             System.out.println("" + EvalArray.get(i).get("PassageLength"));*/

                            String PassageRank = "";
                            String PassageLength = "";

                            System.out.println("Outside Eval Array FOUND");

                            if (EvalArray.get(i).get("Found").contains("YES")) {

                                System.out.println("Inside Eval Array FOUND");
                                numFound++;
                            }

                            if (EvalArray.get(i).get("Rank").equalsIgnoreCase("-1")) {
                                PassageRank = "-";
                            } else {
                                int Rank = Integer.parseInt(EvalArray.get(i).get("Rank").toString()) + 1;
                                PassageRank = Integer.toString(Rank);
                                if (highestRank < Rank) {
                                    highestRank = Rank;
                                }

                                if (Rank == 1) {
                                    numR1++;
                                } else if (Rank == 2) {
                                    numR2++;
                                } else if (Rank >= 3 && Rank <= 5) {
                                    numR3++;
                                } else if (Rank >= 6 && Rank <= 10) {
                                    numR6++;
                                } else if (Rank >= 11 && Rank <= 15) {
                                    numR11++;
                                } else {
                                    numR16++;
                                }

                            }

                            if (EvalArray.get(i).get("PassageLength").equalsIgnoreCase("-1")) {
                                PassageLength = "-";
                            } else {
                                PassageLength = EvalArray.get(i).get("PassageLength");
                            }


                    %>

                    <tr>
                        <td align="left"><%=i + 1%></td>
                        <td align="left"><%=EvalArray.get(i).get("Query")%></td>
                        <td align="left"><%=EvalArray.get(i).get("ExpectedResult")%></td>
                        <td align="center"><%=EvalArray.get(i).get("Found")%></td>
                        <td align="center"><%=PassageRank%></td>
                        <td align="center"><%=PassageLength%></td>
                    </tr>
                    <%                        }
                    %>
                </tbody>
            </table>
        </div>

        <%
            System.out.println("The numFound : " + numFound);
            System.out.println("The total : " + Total);

            float correctPer = (numFound * 100) / Total;
            System.out.println("The correctPer : " + correctPer);

            float incorrectPer = 100 - correctPer;
            System.out.println("The incorrectPer : " + incorrectPer);


        %>          


        <!-- pie chart canvas element -->
    <canvas id="countries" width="600" height="400"></canvas>
    <!-- bar chart canvas element -->
    <canvas id="income" width="600" height="400"></canvas>
    <script>

        // pie chart data
        var pieData = [
            {
                value: <%=incorrectPer%>,
                color: "#F7464A",
                highlight: "#FF5A5E",
                label: " Percentage Incorrect "
            },
            {
                value: <%=correctPer%>,
                color: "#46BFBD",
                highlight: "#5AD3D1",
                label: "Percentage Correct "
            }
        ];
        // pie chart options
        var pieOptions = {
            segmentShowStroke: false,
            animateScale: true
        }
        // get pie chart canvas
        var countries = document.getElementById("countries").getContext("2d");
        // draw pie chart
        new Chart(countries).Pie(pieData, pieOptions);
        // bar chart data
        var barData = {
            labels: ["Rank 1", "Rank 2", "Rank 3to5", "Rank 6to10", "Rank 11to15", "Rank above16"],
            datasets: [
                {
                    fillColor: "#48A497",
                    strokeColor: "#48A4D1",
                    data: [<%=numR1%>, <%=numR2%>, <%=numR3%>, <%=numR6%>, <%=numR11%>, <%=numR16%>]
                }
            ]
        }
        // get bar chart canvas
        var income = document.getElementById("income").getContext("2d");
        // draw bar chart
        new Chart(income).Bar(barData);
    </script>

</body>
</html>

<%} else {
%>

<jsp:forward page="EAResult.jsp?status=analysisFailed"/>

<%
    }
%>