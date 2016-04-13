<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Welcome</title>

<style>
* {margin: 0; padding: 0;}
 
body {
  background: #00a651;
}
 
.card {
  position: relative;
  float: left;
  margin-right: 10px;
  width: 40px;
  height: 60px;
  border-radius: 10px;
  background: #fff;
  -webkit-box-shadow: 3px 3px 7px rgba(0,0,0,0.3);
  box-shadow: 3px 3px 7px rgba(0,0,0,0.3);
}

.card p {
  text-align: center;
  font: 24px/56px Georgia, Times New Roman, serif;
}

.suitD:before, .suitD:after {
  content: "♦";
  color: #ff0000;
}
 
.suitH:before, .suitH:after {
  content: "♥";
  color: #ff0000;
}
 
.suitC:before, .suitC:after {
  content: "♣";
  color: #000;
}
 
.suitS:before, .suitS:after {
  content: "♠";
  color: #000;
}

div[class*='suit']:before {
  position: absolute;
  font-size: 20px;
  left: 0px;
  top: -5px;
}
 
div[class*='suit']:after {
  position: absolute;
  font-size: 20px;
  right: 0px;
  bottom: -5px;
}
</style>
</head>
<body>
	<div id="container" style="margin-left: 50px">
		<div class="row">
			<div class="col-xs-5">
				<div class="row">
					<h3>Select Cards</h3>
					<button id="clear" type="button" class="btn btn-primary">New Hand</button>
					<div class="col-xs-12">
						<g:each in="${['D','H','C','S']}" var="suit">
						<div class="row">
						<g:each in="${model.Card.findAllBySuit(suit)}" var="card">
									<g:render template="/card/card" model="[card:card]"/>
						</g:each>
								</div>
								</g:each>
					</div>
				</div>
			</div>
			<div class="col-xs-3">
			<div class="row">
					<h3>Pocket Cards</h3>
					<div id="pocket-pair">
						<div id="cardA"></div>
						<div id="cardB"></div>
					</div>
				</div>
				<div class="row">
					<h3>Community Cards</h3>
					<div id="comunity-cards">
						<div id="flopA"></div>
						<div id="flopB"></div>
						<div id="flopC"></div>
						<div id="turn"></div>
						<div id="river"></div>
					</div>
				</div>
			</div>
			<div class="col-xs-4">
			<div class="row">
					<div id="result"></div>
				</div>
			</div>
		</div>
		<script>
			$(".card").on("click",
							function(c) {
								var val = $(this).find("#cardValue").val()
								if ($("#cardA").is(':empty')) {
									retrieveChartDisplay(val,"#cardA")
								} else if ($("#cardB").is(':empty')) {
									retrieveChartDisplay(val,"#cardB")
								}  else if ($("#flopA").is(':empty')) {
									retrieveChartDisplay(val,"#flopA")
								}
								else if ($("#flopB").is(':empty')) {
									retrieveChartDisplay(val,"#flopB")
								}
								else if ($("#flopC").is(':empty')) {
									retrieveChartDisplay(val,"#flopC")
								}else if ($("#turn").is(':empty')) {
									retrieveChartDisplay(val,"#turn")
								}else if ($("#river").is(':empty')) {
									retrieveChartDisplay(val,"#river")
								}
							})
			$("#clear").on("click", function(c) {
				$("#cardA").empty()
				$("#cardB").empty()
				$("#flopA").empty()
				$("#flopB").empty()
				$("#flopC").empty()
				$("#turn").empty()
				$("#river").empty()
			})
			
			function retrieveChartDisplay(identifier,target){
				jQuery.ajax({
					url : '<g:createLink controller="evaluator" action="renderCardDisplay"/>',
					type : "POST",
					data : {
						"identifier" :identifier
					},
					success : function(html) {
						$(target).html(html);
						if(target == "#cardB"){
							jQuery.ajax({
								url : '<g:createLink controller="evaluator" action="evaluatePocketPair"/>',
								type : "POST",
								data : {
									"cardA" : $("#cardA").find("#cardValue").val(),
									"cardB" : $("#cardB").find("#cardValue").val()
								},
								success : function(html) {
									$("#result").html(html);
								},
								error : function(request,
										status, error) {
									handleAjaxError(request,
											status, error);
								}
							})	
						}
						if(target == "#flopC"){
							jQuery.ajax({
								url : '<g:createLink controller="evaluator" action="evaluateFlop"/>',
								type : "POST",
								data : {
									"cardA" : $("#cardA").find("#cardValue").val(),
									"cardB" : $("#cardB").find("#cardValue").val(),
									"flopA" : $("#flopA").find("#cardValue").val(),
									"flopB" : $("#flopB").find("#cardValue").val(),
									"flopC" : $("#flopC").find("#cardValue").val()
								},
								success : function(html) {
									$("#result").html(html);
								},
								error : function(request,
										status, error) {
									handleAjaxError(request,
											status, error);
								}
							})	
						}
					},
					error : function(request,
							status, error) {
						handleAjaxError(request,
								status, error);
					}
				})
			}
		</script>
	</div>
</body>
</html>
