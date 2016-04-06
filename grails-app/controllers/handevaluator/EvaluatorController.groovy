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
		render "Rating : "+evaluatorService.evaluatePocketPairs(pocketCardA, pocketCardB)
	}
}
