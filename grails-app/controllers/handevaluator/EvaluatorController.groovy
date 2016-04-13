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
	
	def evaluateFlop(int cardA, int cardB, int flopA, int flopB, int flopC){
		Card pocketCardA = Card.findByIdentifier(cardA)
		Card pocketCardB = Card.findByIdentifier(cardB)
		Card flopCardA = Card.findByIdentifier(flopA)
		Card flopCardB = Card.findByIdentifier(flopB)
		Card flopCardC = Card.findByIdentifier(flopC)
		def drawList = evaluatorService.getPossibleDraws([pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC])
		def handRank = evaluatorService.getBestHand([pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC])
		drawList = drawList.findAll{it.found == true}
		render template:"/evaluator/flopAnalysis", model : [handRank:handRank, draws:drawList]
	}
	
	def evaluateTurn(int cardA, int cardB, int flopA, int flopB,int flopC, int turn){
		Card pocketCardA = Card.findByIdentifier(cardA)
		Card pocketCardB = Card.findByIdentifier(cardB)
		Card flopCardA = Card.findByIdentifier(flopA)
		Card flopCardB = Card.findByIdentifier(flopB)
		Card flopCardC = Card.findByIdentifier(flopC)
		Card turnCard = Card.findByIdentifier(turn)
		def drawList = evaluatorService.getPossibleDraws([pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC,turnCard])
		def handRank = evaluatorService.getBestHand([pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC,turnCard])
		drawList = drawList.findAll{it.found == true}
		render template:"/evaluator/turnAnalysis", model : [handRank:handRank, draws:drawList]
	}
	
	def evaluateHand(int cardA, int cardB, int flopA, int flopB,int flopC, int turn, int river){
		Card pocketCardA = Card.findByIdentifier(cardA)
		Card pocketCardB = Card.findByIdentifier(cardB)
		Card flopCardA = Card.findByIdentifier(flopA)
		Card flopCardB = Card.findByIdentifier(flopB)
		Card flopCardC = Card.findByIdentifier(flopC)
		Card turnCard = Card.findByIdentifier(turn)
		Card riverCard = Card.findByIdentifier(river)
		def handRank = evaluatorService.getBestHand([pocketCardA, pocketCardB, flopCardA, flopCardB, flopCardC,turnCard,riverCard])
		render template:"/evaluator/handAnalysis", model : [handRank:handRank]
	}
	
	def renderCardDisplay(int identifier){
		Card card = Card.findByIdentifier(identifier)
		render template : "/card/card", model : [card:card]
	}
}
