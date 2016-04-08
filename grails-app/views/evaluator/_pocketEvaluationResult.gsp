<h3>Chen Rating : ${rating}</h3>
<h4>Advice</h4>
<div>
<b>Early Position</b>
<g:if test="${rating >= 9 }">
Raise
</g:if>
<g:else>
	<g:if test="${rating >= 8}">
	Call
	</g:if>
	<g:else>
	Fold
	</g:else>
</g:else>
</div>
<div>
<b>Middle Position</b>
<g:if test="${rating >= 9 }">
Raise
</g:if>
<g:else>
<g:if test="${rating >= 7}">
Call
</g:if>
<g:else>
Fold
</g:else>
</g:else>
</div>
<div>
<b>Late Position</b>
<g:if test="${rating >= 9 }">
Raise
</g:if>
<g:else>
	<g:if test="${rating >= 6}">
	Call
	</g:if>
	<g:else>
	Fold
	</g:else>
</g:else>
</div>