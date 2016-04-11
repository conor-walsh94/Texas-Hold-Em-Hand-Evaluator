<b>Best Made Hand : </b> ${bestHand}
<h4>Draws</h4>
<g:each in="${draws}" var="${draw}">
<br>
<div>
<p>Hand : <b>${draw.identifier}</b></p>
<p>Turn Outs : ${draw.turnOuts}</p>
<p>River Outs : ${draw.riverOuts}</p>
</div>
</g:each>