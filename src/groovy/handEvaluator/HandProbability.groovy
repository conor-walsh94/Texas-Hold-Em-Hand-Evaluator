package handEvaluator

import model.Card

class HandProbability {

	def analyzeFlop(ArrayList<Card> cards){
		int flushCount = flushChecker(cards)
		return flushCount
	}

	def flushChecker(ArrayList<Card> cards){
		int totalCards = cards.size()
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		if(highestSuitCount >= 5){
			//flush made
		}
		else if(highestSuitCount == 4){
			//calculate probability of hitting outs
		}
		return 0
	}
}
