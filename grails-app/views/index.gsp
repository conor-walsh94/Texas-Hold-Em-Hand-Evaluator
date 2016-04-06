<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Welcome</title>

</head>
<body>
	<div id="container" style="margin-left: 50px">
		<div class="row">
			<div class="col-xs-4">
				<div class="row">
					<h3>Playing Cards</h3>
					<div class="col-xs-12">
						<g:each in="${['D','H','C','S']}" var="suit">
						<div class="row">
						<g:each in="${model.Card.findAllBySuit(suit)}" var="card">
									<a class="card"> ${card.suit}${card.value} <g:hiddenField name="cardVal"
											id="cardValue" value="${card.identifier}" />
									</a>
						</g:each>
								</div>
								</g:each>
					</div>
				</div>
				<div class="row">
					<h3>Settings</h3>
					Players
					<g:select name="players" from="[1,2,3,4,5,6,7,8]" value="8"></g:select>
				</div>
				<div class="row">
					<h3>Pocket Cards</h3>
					<div id="pocket-pair">
						<div id="cardA"></div>
						<div id="cardB"></div>
					</div>
					<a id="clear">CLEAR</a>
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
			<div class="col-xs-8">
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
									$("#cardA").html(val)
								} else if ($("#cardB").is(':empty')) {
									$("#cardB").html(val)
									jQuery.ajax({
												url : '<g:createLink controller="evaluator" action="evaluatePocketPair"/>',
												type : "POST",
												data : {
													"cardA" : $("#cardA").text(),
													"cardB" : $("#cardB").text()
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
								}  else if ($("#flopA").is(':empty')) {
									$("#flopA").html(val)
								}
								else if ($("#flopB").is(':empty')) {
									$("#flopB").html(val)
								}
								else if ($("#flopC").is(':empty')) {
									$("#flopC").html(val)
								}else if ($("#turn").is(':empty')) {
									$("#turn").html(val)
								}else if ($("#river").is(':empty')) {
									$("#river").html(val)
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
		</script>
	</div>
</body>
</html>
