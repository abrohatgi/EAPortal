var answerTurn = 0;

var confidenceThreshold = 50.0;

var lowestRankOverConfidenceThreshold = function(results){
	for(var i=0; i<results.length; i++){
		if(results[i].answer.score <= confidenceThreshold){
			return i - 1;
		}
	}
	// If all the answers are above the threshold, 
	// then just return -1 because we cannot discriminate them.
	return -1;
}
var contextId = 0;
var register_handlers = function(){
    
   var projID = access();

    $('.searchLayer').toggle();
       
                $.get('http://ibm-dqa12.grid.nuance.com:8037/context?projectId='+projID, function(res){
                               contextId = res;
                               $('.searchLayer').toggle();
                });
    
	console.log($('#searchButton'));
	$('#searchButton').click(function (event){
	question = $('#textInput').val();
         
        if(question.indexOf("What can i help you with")>-1)
        {
            
             $('#textInput').val('');
            return false;
        }
        else if (question == "")
        {  
            
            $('#textInput').val('What can i help you with ?');
            return false;
        }
        
        
	$('#textInput').val('');
    $(".whiteOverlay").addClass('visible');
    $("#textInput").addClass('focus');
    $('#textInput').focus();

	$('.searchResults').append('<br><h3 style="text-align:left;"> You : '+question+'</h3>');
	$('.searchResults').append('<img id="preloader" src="images/audi_preloader.gif"/>');
        
        
   
        
	$.get('http://ibm-dqa12.grid.nuance.com:8037/question?contextId='+encodeURIComponent(contextId)+'&question='+encodeURIComponent(question),  function(res) {
   
   
           
            var answer = "";
		if(res.length == 0){
                    
                        
			answer += '<div style="text-align:left">';
                        answer += '<table><tr>'
                        answer += '<td><img id="ninaimg" src="/ExpertAssistant/images/search-header.png"/></td>'
                        answer += '<td><h3 style="padding-left:5px"> Sorry but i couldnt find an answer to that question</h3></td>'
                        answer += '</tr>'
                        answer += '</table>' 
			//answer += "<p> Sorry, but I couldn't find an answer to this question.</p>";
			answer += "</div>"
		}else {
			var rankDivider = lowestRankOverConfidenceThreshold(res);
			console.log(rankDivider);
                        
			answer += '<div style="text-align:left">';
                        answer += '<table><tr>'
                        answer += '<td><img id="ninaimg" src="/ExpertAssistant/images/search-header.png"/></td>'
                        answer += '<td><h3 style="padding-left:5px"> So, here is what i found </h3></td>'
                        answer += '</tr>'
                        answer += '</table>'     
                        
			if(rankDivider == -1){
				// If we are very low confidence, at least present the 3 best-scored answers.
				rankDivider = Math.min(res.length-1, 2);
			}
			for(var i=0; i<=rankDivider; i++){
				answer += '<h4 style="cursor:pointer;" onClick="$(\'#answer-'+answerTurn+'-'+i+'\').fadeToggle()">'+res[i].answer.text+"</h4>"; 
				answer += '<div id="answer-'+answerTurn+'-'+i+'">' + res[i].answer.evidence.text+' </div>';
			}
			var len = res.length -1 ;
			if (len > rankDivider){
				len = Math.min(res.length, rankDivider+5);
				answer += '<div><h4 style="cursor:pointer;" onClick="$(\'#more-'+answerTurn+'\').fadeToggle()">More...</h4></div>';
				answer += '<div style="margin-left:5em" id="more-'+answerTurn+'">';
				for(var i=rankDivider+1; i<len; i++){
					answer += '<h4 style="cursor:pointer;" onClick="$(\'#answer-'+answerTurn+'-'+i+'\').fadeToggle()">'+res[i].answer.text+"</h4>"; 
					answer += '<div id="answer-'+answerTurn+'-'+i+'">' + res[i].answer.evidence.text+' </div>';
				}
				answer += '</div>';
			}
			answer += '</div>'; 
		}
		$('#preloader').remove();

		$('.searchResults').append(answer);
		$('#more-'+answerTurn).fadeToggle();
		for(var i=1; i<len+1; i++){
			console.log('toggle ' + answerTurn+'-'+i)
			$('#answer-'+answerTurn+'-'+i).fadeToggle();
		}
		answerTurn ++;
	});
	return false;
});
	
	$('#searchButton').submit(function (event){
		return false;
	});
        
        $('#textInput').click(function (event)
        {
            $('#textInput').val('');
        });
        
}