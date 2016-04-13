<h3>Turn Analysis</h3>
<b>Best Made Hand : </b> <g:message code="hand.description.${handRank}.message"></g:message>
<h4>Draws</h4>
<g:each in="${draws}" var="${draw}">
<br>
<div>
<p>Hand : <b><g:message code="hand.draw.${draw.identifier}.message"></g:message></b></p>
<p>Make on River : <g:renderRiverProbability outs="${draw.riverOuts}"/></p>
</div>
</g:each>