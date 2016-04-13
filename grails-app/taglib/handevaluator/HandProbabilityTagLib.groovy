package handevaluator

import handEvaluator.HandProbability

class HandProbabilityTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
	
	
	def renderTurnProbability = { attrs ->
		out << HandProbability.calculateTurnProbability(attrs.outs)
	}
	
	def renderRiverProbability = { attrs ->
		out << HandProbability.calculateRiverProbability(attrs.outs)
	}
	
	def renderTurnOrRiverProbability = { attrs ->
		out << HandProbability.calculateTurnOrRiverProbability(attrs.turnOuts,attrs.riverOuts) 
	}
}
