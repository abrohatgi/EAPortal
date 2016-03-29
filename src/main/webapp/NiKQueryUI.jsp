<%-- 
    Document   : NiKQueryUI
    Created on : Apr 26, 2015, 4:35:57 PM
    Author     : abhishek_rohatgi
--%>

<%@page import="com.nuance.expertassistant.LoadConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.URL"%>
<%

    String ProjectURL = request.getParameter("projectURL");
    
    if(ProjectURL ==null || ProjectURL.isEmpty() || ProjectURL =="")
    {
        ProjectURL = "https://www.usaa.com/inet/pages/our-products-main?wa_ref=pub_global_products_viewall";
    }
    
    if(ProjectURL.contains("usaa"))
    {
        ProjectURL = "https://www.usaa.com/inet/pages/our-products-main?wa_ref=pub_global_products_viewall";
    }
    
    if(ProjectURL.contains("audi"))
    {
        ProjectURL = "http://www.audihelp.com/";
    }
    
    String NikURL = LoadConfig.getValueFromFile("QACORE_URL", "https://db.tt/hVkpsluN");
    
    

%>


<!DOCTYPE html>
<html lang="en">
    <head>
        <title>NiK Query</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <style>
            .popover-custom {
                position: absolute;
                top: 159px !important;
                left: -100px !important;
                width: 980px;
                height: auto;
                z-index: 1010;
                display: none;
                max-width: 980px;
                padding: 2px;
                text-align: left;
                white-space: normal;
                border: none;
                -webkit-border-radius: 0px;
                -moz-border-radius: 6px;
                border-radius: 0px;
                -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                -webkit-background-clip: padding-box;
                -moz-background-clip: padding;
                background-clip: padding-box;
                background-color: rgba(41, 51, 57, 0.75);

                // background: -moz-linear-gradient(to bottom, #FFFFFF, #e5e5e5);
                // background: linear-gradient(to bottom, #FFFFFF, #e5e5e5);

            }

            .popover-custom2 {
                position: absolute;
                top: 59px !important;
                left: 0px !important;
                width: 980px;
                height: auto;
                z-index: 1010;
                display: none;
                max-width: 980px;
                padding: 2px;
                text-align: left;
                white-space: normal;
                border: none;
                -webkit-border-radius: 6px;
                -moz-border-radius: 0px;
                border-radius: 0px;
                -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
                -webkit-background-clip: padding-box;
                -moz-background-clip: padding;
                background-clip: padding-box;
                background-color: rgba(41, 51, 57, 0.75);

                // background: -moz-linear-gradient(to bottom, #FFFFFF, #e5e5e5);
                // background: linear-gradient(to bottom, #FFFFFF, #e5e5e5);

            }


            .popover-content {
                padding: 0px 14px;
                padding-bottom: 7px;

            }


            .response-div {
                width: 943px;
                /* left: 30px; */
                height: auto;
                box-shadow: 1px 1px 2px 2px #C2C2C2;
                background-color: rgba(255,255,255,1.0);
                // background: -moz-linear-gradient(to bottom, #e5e5e5, #FFFFFF);
                //background: linear-gradient(to bottom, #e5e5e5, #FFFFFF);

            }

            .dropdown-div {
                position: relative;
                /* right: 0px; */
                left: 420px;
            }

            .inner-query
            {
                position: absolute;
                height: 60px;
                left: 50%;
                top: 100px;
                width: 980px;
                margin-left: -490px;
                background: -moz-linear-gradient(to bottom, #006192, #004169);
                background: linear-gradient(to bottom, #006192, #004169);
                opacity: 1.0;
                z-index: 1020;
            }

            .inner-input
            {
                position: absolute;
                left: 115px;
                top: 14px;
                width: 550px;
                opacity: 1.0;
            }

            .inner-ask
            {
                position: absolute;
                top: 14px;

            }

            .inner-cancel
            {
                position: absolute;
                top: 14px;
            }

            .best-match
            {
                padding-left: 10px;
                font-size: 18px;
                font-family: 'Helvetica Neue';
                font-weight: bold;
                color: #005b8b;
                padding-bottom: 10px; 
            }
            .other-matches
            {
                padding-left: 10px;
                font-size: 15px;
                font-family: "Times New Roman", Times, serif;
                color: black;
                padding-bottom: 10px;

            }

            .nina-text
            {
                font-family: 'Helvetica Neue';
                font-weight: bold;
                color: #005b8b;
            }

            .user-text
            {
                font-family: 'Helvetica Neue';
                color: black;
            }

            .response-title
            {
                padding-left: 30px;
                font-size: 18px;
                font-family: 'Helvetica Neue';
                font-weight: bold;
                color: black;
                padding-bottom: 2px;


            }

            .response-summary
            {
                padding-left: 30px;
                font-size: 14px;
                font-family: 'Helvetica Neue';
                color: black;
                padding-bottom: 20px;
            }

            .overlay-spinner {
                position: absolute;
                background: url(https://dl.dropboxusercontent.com/u/19978973/comment_spinner_small.gif) no-repeat;
                width: 100%;
                height: 100%;
                z-index: 2000;
                left: 620px;
                top: 73px;

            }

            .overlay-spinner2 {
                position: absolute;
                background: url(https://dl.dropboxusercontent.com/u/19978973/comment_spinner_small.gif) no-repeat;
                width: 100%;
                height: 100%;
                z-index: 2000;
                left: 520px;
                top: 120px;

            }




        </style>
    </head>
    <body>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <img src="https://dl.dropboxusercontent.com/u/19978973/Nina_Persona.png" class="pull-right">
                </div>
                <div class="col-md-8">
                    <br><br>
                    <div class="row">
                        <form>
                            <div class="row">
                                <div class="col-md-12">
                                    <label id="label1" class="form-label"><div class="nina-text">Hi, I'm Nina. What can I help you with?</div></label>
                                </div>
                            </div>
                            <div class ="overlay-spinner"></div>
                            <div class ="overlay-spinner2"></div>
                            <div class="row">
                                <div class="col-md-10">
                                    <div id="ifYes1" style="display:none">
                                        <label id="label2" class="form-label"><div class="nina-text">Nina : Here is what i found: </div></label>
                                    </div>
                                    <div id="ifYes2" style="display:block">
                                        <input type="text" class="form-control pull-left input-sm" placeholder="Type your question here" id="query">
                                    </div>
                                </div>
                                <div class="col-md-2" rel="popover" data-toggle="popover">
                                    <div id="ifYes3" style="display:block">
                                        <a href="#" class="btn pull-left btn-success btn-sm btn-block" >Ask</a>                                       
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="ifYes4" style="display:none">
            <div class="inner-query">
                <div class="row">
                    <div class="col-md-8">
                        <div class="inner-input">
                            <input type="text" class="form-control pull-left input-sm" placeholder="Type your question here" id="query2">
                        </div> </div>
                    <div class="col-md-1" rel="popover2" data-toggle="popover2">
                        <div class="inner-ask" >
                            <a href="#" class="btn pull-left btn-success btn-sm btn-block" >Ask</a>
                        </div>
                    </div>
                    <div rel="cancelbtn" class="col-md-1 pull-right">
                        <a href="#" >
                            <div class="inner-cancel">
                                <img src="https://dl.dropboxusercontent.com/u/19978973/Close_btn.png">
                            </div>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <iframe width="100%" height=900px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="<%=ProjectURL%>" id="proxy_frame" frameborder="0"></iframe>


        <script>
            var iFrameArray = [];
            var content = '';
            var responseHTML = '';
            var frameopenurl = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
            var framecloseurl = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
            var projectID = '';
            var projectURL = '';

            function getURLParameter(name) {
                return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null
            }


            $(function () {
                $("form").submit(function () {
                    return false;
                });
            });

            $("#query").keyup(function (event) {
                if (event.keyCode == 13) {
                    //$("#id_of_button").click();

                    $('[rel="popover"]').click();

                }
            });

            $("#query2").keyup(function (event) {
                if (event.keyCode == 13) {
                    //$("#id_of_button").click();

                    $('[rel="popover2"]').click();

                }
            });


            $(document).on('click', '[rel="cancelbtn"]', function () {
                $('[rel="popover"]').popover('destroy');
                $('[rel="popover2"]').popover('destroy');

                document.getElementById('ifYes1').style.display = 'none';

                document.getElementById('ifYes2').style.display = 'block';
                document.getElementById('ifYes3').style.display = 'block';

                document.getElementById('ifYes4').style.display = 'none';
                document.getElementById('query').value = '';
                document.getElementById('query2').value = '';
                document.getElementById('label1').innerHTML = '<div class="nina-text">What else can i help you with ?</div>';

            });

            $(document).on('click', '[rel="collapse0"]', function ()
            {

                if (document.getElementById("img0").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img0").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img0").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }

            });

            $(document).on('click', '[rel="collapse1"]', function ()
            {


                if (document.getElementById("img1").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img1").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img1").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }


                if ($('#proxy_frame1').length)
                {
                    return;
                }

                var link = iFrameArray[1];

                var iframe = document.createElement('iframe');
                iframe.frameBorder = 0;
                iframe.width = "100%";
                iframe.height = "300px";
                iframe.id = "proxy_frame1";
                iframe.scrolling = "yes";
                iframe.sandbox = "allow-forms allow-same-origin allow-scripts";
                iframe.setAttribute("src", link);
                document.getElementById("demo1").appendChild(iframe);





            });

            $(document).on('click', '[rel="collapse2"]', function ()
            {

                if (document.getElementById("img2").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img2").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img2").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }

                if ($('#proxy_frame2').length)
                {
                    return;
                }

                var link = iFrameArray[2];

                var iframe = document.createElement('iframe');
                iframe.frameBorder = 0;
                iframe.width = "100%";
                iframe.height = "300px";
                iframe.id = "proxy_frame2";
                iframe.scrolling = "yes";
                iframe.sandbox = "allow-forms allow-same-origin allow-scripts";
                iframe.setAttribute("src", link);
                document.getElementById("demo2").appendChild(iframe);
            });

            $(document).on('click', '[rel="collapse3"]', function ()
            {


                if (document.getElementById("img3").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img3").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img3").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }

                if ($('#proxy_frame3').length)
                {
                    return;
                }

                var link = iFrameArray[3];

                var iframe = document.createElement('iframe');
                iframe.frameBorder = 0;
                iframe.width = "100%";
                iframe.height = "300px";
                iframe.id = "proxy_frame3";
                iframe.scrolling = "yes";
                iframe.sandbox = "allow-forms allow-same-origin allow-scripts";
                iframe.setAttribute("src", link);
                document.getElementById("demo3").appendChild(iframe);
            });

            $(document).on('click', '[rel="collapse4"]', function ()
            {

                if (document.getElementById("img4").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img4").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img4").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }

                if ($('#proxy_frame4').length)
                {
                    return;
                }

                var link = iFrameArray[4];

                var iframe = document.createElement('iframe');
                iframe.frameBorder = 0;
                iframe.width = "100%";
                iframe.height = "300px";
                iframe.id = "proxy_frame4";
                iframe.scrolling = "yes";
                iframe.sandbox = "allow-forms allow-same-origin allow-scripts";
                iframe.setAttribute("src", link);
                document.getElementById("demo4").appendChild(iframe);
            });

            $(document).on('click', '[rel="collapse5"]', function ()
            {

                if (document.getElementById("img5").src.indexOf("frame_close_btn") > -1)
                {
                    document.getElementById("img5").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_open_btn.png';
                }
                else
                {
                    document.getElementById("img5").src = 'https://dl.dropboxusercontent.com/u/19978973/frame_close_btn.png';
                }

                if ($('#proxy_frame5').length)
                {
                    return;
                }

                var link = iFrameArray[5];

                var iframe = document.createElement('iframe');
                iframe.frameBorder = 0;
                iframe.width = "100%";
                iframe.height = "300px";
                iframe.id = "proxy_frame5";
                iframe.scrolling = "yes";
                iframe.sandbox = "allow-forms allow-same-origin allow-scripts";
                iframe.setAttribute("src", link);
                document.getElementById("demo5").appendChild(iframe);
            });




            $(document).on('click', '[rel="popover2"]', function () {

                iFrameArray = [];
                content = '';
                responseHTML = '';
                content = '';


                var question = document.getElementById('query2').value;

                if (!question || question == '')
                {
                    document.getElementById('label2').innerHTML = '<div class ="nina-text">Nina : In order to help, please type your question below.</div>';
                    return;
                }
                /*
                 var regex = /\s+/gi;
                 var wordCount = question.trim().replace(regex, ' ').split(' ').length;
                 
                 if (wordCount == 1)
                 {
                 
                 document.getElementById('label1').innerHTML = '<div class ="nina-text">Nina : Please type a more descriptive question.</div>';
                 return;
                 }
                 */

                var contextId = 0;

                $('.overlay-spinner2').show();

                $.ajax({
                    type: 'GET',
                    url: '<%=NikURL%>context?projectId=' + projectID,
                    //data: message,
                    dataType: 'json',
                    success: function (res)
                    {




                        $.ajax({
                            type: 'GET',
                            url: '<%=NikURL%>question?contextId=' + encodeURIComponent(res) + '&question=' + encodeURIComponent(question),
                            //data: message,
                            //url: 'http://demo7319927.mockable.io/queryResponse',
                            dataType: 'json',
                            success: function (data) {

                                maxResponses = 5;
                                if (data.length < maxResponses)
                                {
                                    maxResponses = data.length;
                                }


                                for (var i = 0; i < maxResponses; i++) {
                                    console.log("title : " + data[i].answer.title);
                                    console.log("pageURL : " + data[i].answer.pageURL);
                                    console.log("presentationURL : " + data[i].answer.presentationUrl);
                                    console.log("summary : " + data[i].answer.summary);

                                    iFrameArray.push(data[i].answer.presentationUrl);



                                    var titleHTML = '<div class="response-title">' + data[i].answer.title + '</div>';
                                    var summaryHTML = '<div class="response-summary">' + data[i].answer.summary + '</div>';
                                    //var frameHTML = '<iframe width="100%" height=300px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="' + data[i].answer.presentationUrl + '" id="proxy_frame" frameborder="0"></iframe>'

                                    var frameHTML = '';
                                    var frameImageURL = framecloseurl;

                                    var collapseType = 'collapse out';
                                    if (i == 0)
                                    {
                                        frameHTML = '<iframe width="100%" height=300px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="' + data[i].answer.presentationUrl + '" id="proxy_frame" frameborder="0"></iframe>';
                                        titleHTML = '<div class="best-match"> Best Match: </div>' + titleHTML;
                                        collapseType = 'collapse in';
                                        frameImageURL = frameopenurl;
                                    }

                                    if (i == 1)
                                    {
                                        titleHTML = '<div class="other-matches"> Other matches : </div>' + titleHTML;
                                    }

                                    var collapseHTML = '<div id="demo' + i + '"  class="' + collapseType + '"> ' + frameHTML + '</div><div rel="collapse' + i + '" class="dropdown-div" data-toggle="collapse" data-target="#demo' + i + '"><img id="img' + i + '" src="' + frameImageURL + '"></div>';
                                    responseHTML = responseHTML + '<div class="response-div"><br>' + titleHTML + summaryHTML + collapseHTML + '</div>';


                                }

                                content = content + responseHTML;

                                $('.overlay-spinner2').hide();

                                myFunction2();


                            },
                            error: function (data, error) {

                                alert("Error While fetching response !");
                                $('.overlay-spinner2').hide();

                            }
                        });

                    },
                    error: function (data, error) {

                        alert("Error While fetching context ID !");
                        $('.overlay-spinner2').hide();

                    }

                });

            });



            function myFunction2()
            {



                $('[rel="popover"]').popover('destroy');



                var query = document.getElementById('query2').value;



                document.getElementById('label1').innerHTML = '<div class="user-text"><b>You</b> : ' + query + '</div>';


                var popoverTemplate = ['<div class="popover-custom2">',
                    '<div class="arrow"></div>',
                    '<div class="popover-content">',
                    '</div>',
                    '</div>'
                ].join('');

                $('[rel="popover2"]').popover({
                    html: true,
                    selector: '[rel="popover2"]',
                    trigger: 'mannual',
                    placement: 'bottom',
                    template: popoverTemplate,
                    content: function () {
                        return content;
                    },
                }).popover('show');

                content = '';
                responseHTML = '';
                document.getElementById('query2').value = '';


            }


            $(document).ready(function () {


                projectID = getURLParameter('projectID');
                projectURL = getURLParameter('projectURL');

                if (!projectID) {
                    projectID = 'usaa';
                }
                
                 if (!projectURL) {
                    projectURL = 'https://www.usaa.com/inet/pages/our-products-main?wa_ref=pub_global_products_viewall';
                }

                iFrameArray = [];


                $('.overlay-spinner').hide();

                $('.overlay-spinner2').hide();

                var popoverTemplate = ['<div class="popover-custom">',
                    '<div class="arrow"></div>',
                    '<div class="popover-content">',
                    '</div>',
                    '</div>'
                ].join('');
                // var innerQuery = '<div class="inner-query"><div class="row"><div class="col-md-8"><div class="inner-input"><input type="text" class="form-control pull-left input-sm" placeholder="Type your question here" name="query2"></div> </div><div class="col-md-1"><div class="inner-ask"><a href="#" class="btn pull-left btn-success btn-sm btn-block" >Ask</a></div></div><div rel="cancelbtn" class="col-md-1 pull-right"><a href="#" ><div class="inner-cancel"><img src="https://dl.dropboxusercontent.com/u/19978973/cancelbtn.png"></div></a></div></div></div>';
                // var responseToQuery = '<iframe width="100%" height=300px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="http://www.audihelp.com/" id="proxy_frame" frameborder="0"></iframe>'
                //var responseHtml = '<div class="response-div"><p>Passage Lines here</p><div id="demo" class="collapse in"> ' + responseToQuery + '</div><div class="dropdown-div" data-toggle="collapse" data-target="#demo"><a href="#" ><img src="https://dl.dropboxusercontent.com/u/19978973/dropdown.png"></a></div></div>'

                content = '';
                responseHTML = '';

                $('[rel="popover"]').bind('click', function () {

                    content = '';
                    responseHTML = '';
                    iFrameArray = [];



                    var question = document.getElementById('query').value;

                    if (!question || question == '')
                    {

                        document.getElementById('label1').innerHTML = '<div class ="nina-text">Nina : In order to help, please type your question below.</div>';
                        return;
                    }
                    /*
                     var regex = /\s+/gi;
                     var wordCount = question.trim().replace(regex, ' ').split(' ').length;
                     
                     if (wordCount == 1)
                     {
                     
                     document.getElementById('label1').innerHTML = '<div class ="nina-text">Nina : Please type a more descriptive question. </div>';
                     return;
                     }*/


                    var contextId = 0;

                    $('.overlay-spinner').show();

                    $.ajax({
                        type: 'GET',
                        url: '<%=NikURL%>context?projectId=' + projectID,
                        //data: message,
                        dataType: 'json',
                        success: function (res)
                        {


                            $.ajax({
                                type: 'GET',
                                url: '<%=NikURL%>question?contextId=' + encodeURIComponent(res) + '&question=' + encodeURIComponent(question),
                                //data: message,
                                //url: 'http://demo7319927.mockable.io/queryResponse',
                                dataType: 'json',
                                success: function (data) {

                                    maxResponses = 5;
                                    if (data.length < maxResponses)
                                    {
                                        maxResponses = data.length;

                                    }


                                    for (var i = 0; i < maxResponses; i++) {
                                        console.log("title : " + data[i].answer.title);
                                        console.log("pageURL : " + data[i].answer.pageURL);
                                        console.log("presentationURL : " + data[i].answer.presentationUrl);
                                        console.log("summary : " + data[i].answer.summary);

                                        iFrameArray.push(data[i].answer.presentationUrl);

                                        var titleHTML = '<div class="response-title">' + data[i].answer.title + '</div>';
                                        var summaryHTML = '<div class="response-summary">' + data[i].answer.summary + '</div>';
                                        // var frameHTML = '<iframe width="100%" height=300px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="' + data[i].answer.presentationUrl + '" id="proxy_frame" frameborder="0"></iframe>'

                                        var frameHTML = '';
                                        var frameImageURL = framecloseurl;


                                        var collapseType = 'collapse out';
                                        if (i == 0)
                                        {
                                            frameHTML = '<iframe width="100%" height=300px scrolling="yes" sandbox="allow-forms allow-same-origin allow-scripts" src="' + data[i].answer.presentationUrl + '" id="proxy_frame" frameborder="0"></iframe>';
                                            titleHTML = '<div class="best-match"> Best Match: </div>' + titleHTML;
                                            collapseType = 'collapse in';
                                            frameImageURL = frameopenurl;
                                        }

                                        if (i == 1)
                                        {
                                            titleHTML = '<div class="other-matches"> Other matches :</div>' + titleHTML;
                                        }

                                        var collapseHTML = '<div id="demo' + i + '" class="' + collapseType + '"> ' + frameHTML + '</div><div rel="collapse' + i + '" class="dropdown-div" data-toggle="collapse" data-target="#demo' + i + '"><img id="img' + i + '" src="' + frameImageURL + '"></div>';
                                        responseHTML = responseHTML + '<div class="response-div"><br>' + titleHTML + summaryHTML + collapseHTML + '</div>';


                                    }

                                    content = content + responseHTML;
                                    $('.overlay-spinner').hide();
                                    myFunction();

                                },
                                error: function (data, error) {

                                    $('.overlay-spinner').hide();
                                    alert("Error While fetching response !");

                                }
                            });

                        },
                        error: function (data, error) {

                            $('.overlay-spinner').hide();
                            alert("Error While fetching context ID !");

                        }

                    });

                });

                function myFunction()
                {



                    var query = document.getElementById('query').value;

                    document.getElementById('ifYes4').style.display = 'block';

                    document.getElementById('ifYes1').style.display = 'block';
                    document.getElementById('ifYes2').style.display = 'none';
                    document.getElementById('ifYes3').style.display = 'none';

                    document.getElementById('label1').innerHTML = '<div class="user-text"><b>You</b> : ' + query + '</div>';


                    $('[rel="popover"]').popover({
                        html: true,
                        selector: '[rel="popover"]',
                        trigger: 'mannual',
                        placement: 'bottom',
                        template: popoverTemplate,
                        content: content,
                    }).popover('toggle');

                    content = '';
                    responseHTML = '';

                }




            });

        </script>

    </body>
</html>

