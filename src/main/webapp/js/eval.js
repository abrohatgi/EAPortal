var answerTurn = 0;

var answerkey = [
                 ["how does the cruise control work?","Controls>>Adaptive cruise control (ACC)>>Speed and distance control system>>How does the distance control work?>>"],
                 ["how can I switch on the cruise control?","Controls>>Adaptive cruise control (ACC)>>Speed and distance control system>>Switching the system on and off>>"],
                 ["tell me about the lane assistance","Controls>>Audi lane assist>>Audi lane assist>>Description>>"],
                 ["tell me about the parking assistance","Controls>>Parking aid>>Audi parking system>>Description>>"],
                 ["tell me about parking assistance","Controls>>Parking aid>>Audi parking system>>Description>>"],
                 ["tyre pressure for towing a trailer?","Driving tips>>Towing a trailer>>Driving the vehicle with a trailer or caravan>>Points to check before towing>>Tyre pressure>>"],
                 ["tell me about the flat tyres","General maintenance>>Wheels and tyres>>Run flat tyres>>Introduction>>"],
                 ["how should I repair my tyres?","Self-help>>Self-help>>Tyre repairs>>"],
                 ["how do I check the oil?", "General maintenance>>Checking and topping up fluids>>Engine oil>>Checking the oil level (with dipstick)>>"],
                 ["tell me about airbags","Safety>>Airbag system>>Description of airbag system>>General notes on airbag system>>"],
                 ["Why do the seat belts tighten up?","Safety>>Seat belts>>Why is it so important to use seat belts?>>"],
                 ["How do i increase the maximum speed?","Controls>>Instruments and warning/indicator lamps>>Instruments>>SET button>>Setting speed warning>>"],
                 ["How do i change the maximum speed warning?","Controls>>Instruments and warning/indicator lamps>>Instruments>>SET button>>Setting speed warning>>"],
                 ["how can i tell the range i can drive from the fuel i have?","Controls>>Driver information system>>On-board computer>>Introduction>>"],
                 ["how can i tell how much distance I have covered?","Controls>>Driver information system>>On-board computer>>Introduction>>Distance covered>>"],
                 ["how do i reset the computer to zero?","Controls>>Driver information system>>On-board computer>>Controls>>Resetting figures to zero>>"],
                 ["how do I turn on the high beams","Controls>>Lights and vision>>Lights>>Turn signal and main beam lever>>"],
                 ["how do I turn on the high beams?","Controls>>Lights and vision>>Lights>>Turn signal and main beam lever>>"],
                 ["how do i set the parking break?","Controls>>Driving>>Electro-mechanical parking brake>>"],
                 ["where is the parking break set?","Controls>>Driving>>Electro-mechanical parking brake>>Operation>>"],
                 ["are the side mirrors heated?","Controls>>Heating and cooling>>Deluxe automatic air conditioner plus>>Heated rear window>>"],
                 ["how do i set night mode on the rearview mirror?","Controls>>Lights and vision>>Rear-view mirrors>>Manual anti-dazzle adjustment>>"],
                 ["What is the yellow symbol in my display?","Controls>>Driver information system>>Auto-check control>>Yellow symbols>>"],
                 ["how do I start the engine?", "Controls>>Driving>>Starting and stopping the engine with the advanced key>>Starting the engine with the Start/Stop button>>"]
                 //["What is the radar sensor?",]
                 ]

var results = []
var run_evaluation = function(){
	for(var i=0; i<answerkey.length; i++){
		var question = answerkey[i][0];
		var expectedAnswer = answerkey[i][1];
		results[i] = "";
		sendQuery(i, question);
	}
}

var sendQuery = function(idx, question){
	$.get('/question?contextId='+encodeURIComponent(1)+'&question='+encodeURIComponent(question),  function(res) {
		var answer = "";
		results[idx] = res;
		register_end(idx, res);
	});	
}

var counter = 0;
var register_end = function(i, res){
	counter ++;
	if(counter == answerkey.length){
		output = '<table style="text-align:left; border:1">';
		output += "<tr>";
		output += "<td><b>Question/Expected Answer</b></td><td><b>Correct answer rank</b></td><td>Correct answer in subsection</td><td><b>Correct answer confidence</b></td>"
		output += "</tr>";
		var firstRank = 0;
		var inNBest = 0;
		var included = 0;
		var wrong = 0;
		for(var i=0; i<answerkey.length; i++){
			var rank = getCorrectAnswerRank(i);
			var score = " - ";
			var subSectionRank = " - "
			if (rank != '-'){
				score = results[i][rank].answer.score;
			}else {
				subSectionRank = getCorrectAnswerSectionRank(i);
				if(subSectionRank != '-'){
					score = results[i][subSectionRank].answer.score;
				}
			}
			var answerColor = "Red";
			if(rank != '-'){
				if( rank == 0){
					firstRank ++;
					answerColor = "Green";
				}else{
					inNBest ++ ;
					answerColor = "Blue";
				}
			}else{
				if (subSectionRank != '-'){
					included ++;
					answerColor = "Gold";
				}else{
					wrong++;
				}
			}
			output += '<tr style="color:'+answerColor+'">'
			output += "<td>"+answerkey[i][0]+"<br><i>"+answerkey[i][1]+"</i></td>";
			output += "<td>"+rank+"</td>";
			output += "<td>"+subSectionRank+"</td>";
			output += "<td>"+score+"</td>";
			output += "</tr>"
		}
		output += "<table>";
		$('.searchResults').append(output);
		var stats = '<table style="text-align:left; border:1">';
		stats += "<tr><td>Answer presented at 1st rank:</td><td>"+firstRank+'/'+answerkey.length+'('+firstRank/answerkey.length+')'+'</td></tr>';
		stats += "<tr><td>Answer presented in NBest :</td><td>"+inNBest+'/'+answerkey.length+'('+inNBest/answerkey.length+')'+'</td></tr>';
		stats += "<tr><td>Answer presented in enclosing section:</td><td>"+included+'/'+answerkey.length+'('+included/answerkey.length+')'+'</td></tr>';
		stats += "<tr><td>Answer not found:</td><td>"+wrong+'/'+answerkey.length+'('+wrong/answerkey.length+')'+'</td></tr>';
		stats += "</table>";
		$('#statistics').append(stats);
	}
}

var getCorrectAnswerRank = function(idx){
	expectedAnswer = answerkey[idx][1];
	answers = results[idx];
	for(var i=0; i<answers.length; i++){
		console.log(answers[i].answer.text);
		console.log(expectedAnswer);
		if(answers[i].answer.text == expectedAnswer){
			console.log(answers[i].answer);
			return i;
		}
	}
	return '-';
}

var getCorrectAnswerSectionRank = function(idx){
	expectedAnswer = answerkey[idx][1];
	answers = results[idx];
	for(var i=0; i<answers.length; i++){
		console.log(answers[i].answer.text);
		console.log(expectedAnswer);
		if(expectedAnswer.indexOf(answers[i].answer.text) > -1){
			console.log(answers[i].answer);
			return i;
		}
	}
	return '-';
}

var register_handlers = function(){
	console.log($('#searchButton'));
	$('#searchButton').click(function (event){
	question = $('#textInput').val();
	$('#textInput').val('');
    $(".whiteOverlay").addClass('visible');
    $("#textInput").addClass('focus');
    $('#textInput').focus();

	$('.searchResults').append('<br><h3 style="text-align:left;">'+question+'</h3>');
	$('.searchResults').append('<img id="preloader" src="images/audi_preloader.gif"/>');
	$.get('/question?contextId='+encodeURIComponent(1)+'&question='+encodeURIComponent(question),  function(res) {
		var answer = "";
		var len = res.length ;
		if(res.length == 0){
			answer += '<div style="text-align:left">';
			answer += "<p>Sorry, but I couldn't find an answer to this questions.</p>";
			answer += "</div>"
		}else {
			if (res.length > 5){
				len = 5;
			}
			answer += '<div style="text-align:left">';
			for(var i=0; i<len; i++){
				answer += '<h4 style="cursor:pointer;" onClick="$(\'#answer-'+answerTurn+'-'+i+'\').fadeToggle()">'+res[i].answer.text+"</h4>"; 
				answer += '<div id="answer-'+answerTurn+'-'+i+'">' + res[i].answer.evidence.text+' </div>';
			}
			answer += '</div>'; 
		}
		$('#preloader').remove();

		$('.searchResults').append(answer);
		for(var i=1; i<len; i++){
			$('#answer-'+answerTurn+'-'+i).fadeToggle();
		}
		answerTurn ++;
	});
	return false;
});
	
	$('#searchButton').submit(function (event){
		return false;
	});
}