package handEvaluator

import model.Card

class HandProbability {

	def analyzeFlop(ArrayList<Card> cards){
		boolean flush = checkForFlush(cards)
		if(flush){
			println("flush made")
		}
		else{
			boolean flushDraw=checkForFlushDraw(cards)
			if(flushDraw){
				def turnProbability = calculateTurnProbability(9)
				def riverProbability = calculateRiverProbability(9)
				def turnOrRiverProbability = calculateTurnOrRiverProbability(9)
			}
		}
		boolean straight = checkForStraight(cards)
		if(straight){
			println("straight made")
		}
		else{
			boolean openEndedStraightDraw = checkForOpenEndedStraightDraw(cards)
			if(openEndedStraightDraw){
				def turnProbability = calculateTurnProbability(8)
				def riverProbability = calculateRiverProbability(8)
				def turnOrRiverProbability = calculateTurnOrRiverProbability(8)
				println(turnProbability)
				println(riverProbability)
				println(turnOrRiverProbability)
			}
		}
		return "done"
	}

	def calculateTurnProbability(int outs){
		return outs/47
	}
	
	def calculateRiverProbability(int outs){
		return outs/46
	}
	
	def calculateTurnOrRiverProbability(int outs){
		return 1-(((47-outs)/47) * ((46-outs)/46))
	}
	
	
	/********************************************************************************************************
	 ************************************ INSIDE STRAIGHT FLUSH **********************************************
	 *********************************************************************************************************/
	boolean checkForStraightFlush(ArrayList<Card> cards){
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		if(highestSuitCount >= 5){
			return true
		}
		return false
	}
	
	boolean checkForInsightStraightFlushDraw(ArrayList<Card> cards){
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		if(highestSuitCount == 4){
			return true
		}
		return false
	}
	/********************************************************************************************************
	 ********************************************** FLUSH **************************************************** 
	 *********************************************************************************************************/
	boolean checkForFlush(ArrayList<Card> cards){
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		if(highestSuitCount >= 5){
			return true
		}
		return false
	}
	
	boolean checkForFlushDraw(ArrayList<Card> cards){
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		if(highestSuitCount == 4){
			return true
		}
		return false
	}
	
	/********************************************************************************************************
	 *****************************************  STRAIGHT  ******************************************************
	 *********************************************************************************************************/
	boolean checkForStraight(ArrayList<Card> cards){
		def cardList = getHighestConsecutiveCardList(cards)
		if(cardList.size() >= 5){
			return true
		}
		return false
	}
	
	boolean checkForOpenEndedStraightDraw(ArrayList<Card> cards){
		def cardList = getHighestConsecutiveCardList(cards)
		if(cardList.first() > 1 && cardList.last() < 14){
			return true
		}
		return false
	}
	
	def getHighestConsecutiveCardList(ArrayList<Card> cards){
		def numericList = cards.collect{it.numericValue}
		if(numericList.contains(14)){
			numericList.add(1)
		}
		numericList.sort()
		numericList = numericList.unique()
		int currentConsecutiveCount = 1;
		int bestConsecutiveCount = 0;
		def currentConsecutiveCards = []
		def bestConsecutiveCards = []
		if(numericList.size() >= 5){
			int previousValue = numericList[0]
			currentConsecutiveCards.add(previousValue)
			for(int i=1;i<5;i++){
				if(numericList[i] == previousValue +1){
					currentConsecutiveCount++;
					currentConsecutiveCards.add(numericList[i])
					if(currentConsecutiveCount > bestConsecutiveCount){
						bestConsecutiveCount = currentConsecutiveCount
						bestConsecutiveCards = currentConsecutiveCards
					}
				}
				else{
					currentConsecutiveCards = [numericList[i]]
					currentConsecutiveCount = 1;
				}
				previousValue = numericList[i]
			}
		}
		return bestConsecutiveCards
	}
}
