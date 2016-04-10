package handevaluator

import handEvaluator.MadeHandChecker
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
	
	def evaluateFlop(int cardA, int cardB, int flopA, int flopB, int flopC){
		Card pocketCardA = Card.findByIdentifier(cardA)
		Card pocketCardB = Card.findByIdentifier(cardB)
		Card flopCardA = Card.findByIdentifier(flopA)
		Card flopCardB = Card.findByIdentifier(flopB)
		Card flopCardC = Card.findByIdentifier(flopC)
		render evaluatorService.evaluateFlop(pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC)
	}
	
	def renderCardDisplay(int identifier){
		Card card = Card.findByIdentifier(identifier)
		render template : "/card/card", model : [card:card]
	}
}
