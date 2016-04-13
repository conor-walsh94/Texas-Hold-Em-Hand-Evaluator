package handevaluator

import grails.transaction.Transactional
import handEvaluator.HandEvaluator
import handEvaluator.HandProbability
import model.Card

@Transactional
class EvaluatorService {

	def evaluatePocketPairs(Card pocketA, Card pocketB) {
		def overallScore;
		def scoreA = getChenRatingForCard(pocketA)
		def scoreB = getChenRatingForCard(pocketB)
		if(pocketA.value == pocketB.value){
			def pairingScore = scoreA*2
			if(pairingScore < 5){
				pairingScore = 5
			}
			overallScore = pairingScore
		}
		else{
			def highScore = scoreA
			if(scoreB > scoreA){
				highScore = scoreB
			}
			if(pocketA.suit == pocketB.suit){
				highScore = highScore+2
			}
			overallScore = highScore
		}
		int gapOffset = getChenCardGapValue(pocketA, pocketB)
		overallScore = overallScore + gapOffset
		return overallScore
	}

	def getChenRatingForCard(Card card){
		def point;
		if(card.value == "A"){
			point = 10
		}
		else if(card.value == "K"){
			point = 8
		}
		else if(card.value == "Q"){
			point = 7
		}
		else if(card.value == "J"){
			point = 6
		}
		else{
			int num = Integer.parseInt(card.value)
			point = num/2
		}
		return point
	}

	def getChenCardGapValue(Card cardA, Card cardB){
		int aVal = cardA.numericValue
		int bVal = cardB.numericValue
		int gap;
		int gapScore;
		if(aVal > bVal){
			gap = aVal-bVal-1
		}
		else if(bVal > aVal){
			gap = bVal-aVal-1
		}
		else{
			gap =0
		}
		switch(gap){
			case 0 :
				gapScore = 0
				break;
			case 1 :
				gapScore = -1
				break;
			case 2 :
				gapScore = -2
				break;
			case 3 :
				gapScore = -4
				break;
			default :
				gapScore = -5
				break;
		}
		if((aVal < 12 && bVal < 12) && (bVal != aVal)){
			gapScore = gapScore+1
		}
		return gapScore
	}
	
	def getBestHand(ArrayList<Card> cards){
		HandEvaluator hp = new HandEvaluator()
		return hp.getBestHand(cards)
	}
	
	def getPossibleDraws(ArrayList<Card> cards){
		HandEvaluator hp = new HandEvaluator()
		return hp.analyzeHand(cards)
	}
	

}
