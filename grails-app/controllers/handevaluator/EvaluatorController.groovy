package handevaluator

import model.Card

class EvaluatorController {

	def evaluatorService 
	
    def index() {
		render view : "/index"
	}
	
	def evaluatePocketPair(int cardA, int cardB){
		Card pocketCardA = Card.findByIdentifier(cardA)
		Card pocketCardB = Card.findByIdentifier(cardB)
		def rating = evaluatorService.evaluatePocketPairs(pocketCardA, pocketCardB)
		render template:"/evaluator/pocketEvaluationResult", model : [rating:rating]
	}
	
	def renderCardDisplay(int identifier){
		Card card = Card.findByIdentifier(identifier)
		render template : "/card/card", model : [card:card]
	}
}
