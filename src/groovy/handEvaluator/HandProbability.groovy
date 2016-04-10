package handEvaluator

import model.Card

class HandProbability {

	def analyzeFlop(ArrayList<Card> cards){
		boolean straightFlush = checkForStraightFlush(cards)
		if(straightFlush){
			println("straight flush made")
		}
		else{
			boolean openEndedStraightFlushDraw = checkForOpenEndedStraightFlushDraw(cards)
			if(openEndedStraightFlushDraw){
				println("open ended straight flush draw")
			}
			else{
				boolean insideStraightFlushDraw = checkForInsideStraightFlushDraw(cards)
				if(insideStraightFlushDraw){
					println("inside straight flush draw")
				}
			}
		}

		boolean fourOfAKind = checkForFourOfAKind(cards)
		if(fourOfAKind){
			println("fours")
		}
		else{
			boolean foursDraw = checkForFourOfAKindDraw(cards)
			if(foursDraw){
				println("foursDraw")
			}
		}
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
				println("open ended")
				def turnProbability = calculateTurnProbability(8)
				def riverProbability = calculateRiverProbability(8)
				def turnOrRiverProbability = calculateTurnOrRiverProbability(8)
				println(turnProbability)
				println(riverProbability)
				println(turnOrRiverProbability)
			}
			else{
				boolean insideStraightDraw = checkForInsideStraightDraw(cards)
				if(insideStraightDraw){
					def turnProbability = calculateTurnProbability(4)
					def riverProbability = calculateRiverProbability(4)
					def turnOrRiverProbability = calculateTurnOrRiverProbability(4)
					println("inside straight draw")
					println(turnProbability)
					println(riverProbability)
					println(turnOrRiverProbability)
				}
			}
		}
		boolean twoPair = checkForTwoPair(cards)
		if(twoPair){
			println("two pair")
		}
		else{
			boolean twoPairDraw = checkForTwoPairDraw(cards)
			if(twoPairDraw){
				println("two pair draw")
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
	 ************************************ STRAIGHT FLUSH **********************************************
	 *********************************************************************************************************/
	boolean checkForStraightFlush(ArrayList<Card> cards){
		boolean straightFlushMade = false;
		def suitedSets = []
		suitedSets.add(["name":"H","cards":cards.findAll{it.suit == "H"}])
		suitedSets.add(["name":"D","cards":cards.findAll{it.suit == "D"}])
		suitedSets.add(["name":"S","cards":cards.findAll{it.suit == "S"}])
		suitedSets.add(["name":"C","cards":cards.findAll{it.suit == "C"}])
		suitedSets.each{
			if(it.cards.size() >= 5){
				boolean flag = checkForStraight(it.cards)
				if(flag){
					straightFlushMade = true
				}
			}
		}
		return straightFlushMade
	}

	boolean checkForOpenEndedStraightFlushDraw(ArrayList<Card> cards){
		boolean found = false;
		def suitedSets = []
		suitedSets.add(["name":"H","cards":cards.findAll{it.suit == "H"}])
		suitedSets.add(["name":"D","cards":cards.findAll{it.suit == "D"}])
		suitedSets.add(["name":"S","cards":cards.findAll{it.suit == "S"}])
		suitedSets.add(["name":"C","cards":cards.findAll{it.suit == "C"}])
		suitedSets.each{
			if(it.cards.size() >= 4){
				boolean flag = checkForOpenEndedStraightDraw(it.cards)
				if(flag){
					found = true
				}
			}
		}
		return found
	}

	boolean checkForInsideStraightFlushDraw(ArrayList<Card> cards){
		boolean found = false;
		def suitedSets = []
		suitedSets.add(["name":"H","cards":cards.findAll{it.suit == "H"}])
		suitedSets.add(["name":"D","cards":cards.findAll{it.suit == "D"}])
		suitedSets.add(["name":"S","cards":cards.findAll{it.suit == "S"}])
		suitedSets.add(["name":"C","cards":cards.findAll{it.suit == "C"}])
		suitedSets.each{
			if(it.cards.size() >= 4){
				boolean flag = checkForInsideStraightDraw(it.cards)
				if(flag){
					found = true
				}
			}
		}
		return found
	}
	
	/********************************************************************************************************
	 **************************************** FOUR OF A KIND *************************************************
	 *********************************************************************************************************/
	boolean checkForFourOfAKind(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		counts.each{ key, value ->
			if(value > highestCount){
				highestCount = value
			}
		}
		if(highestCount == 4){
			return true
		}
		return false
	}

	boolean checkForFourOfAKindDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		counts.each{ key, value ->
			if(value > highestCount){
				highestCount = value
			}
		}
		if(highestCount == 3){
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
		if(cardList?.first() > 1 && cardList?.last() < 14){
			if(cardList.size() >= 4){
				return true
			}
		}
		return false
	}

	boolean checkForInsideStraightDraw(ArrayList<Card> cards){
		def cardList = getHighestConsecutiveCardList(cards)
		if(cardList.containsAll([1, 2, 3, 4]) || cardList.containsAll([11, 12, 13, 14]) || cardList.containsAll([10, 11, 12, 14]) || cardList.containsAll([1, 3, 4, 5])){
			return true
		}
		def lists = []
		def numericList = cards.collect{it.numericValue}
		if(numericList.contains(14)){
			numericList.add(1)
		}
		numericList.sort()
		numericList = numericList.unique()
		for(int i =1; i<numericList.size();i++){
			if(numericList[i-1]+1 == numericList[i]){
				lists.add([
					numericList[i-1],
					numericList[i]
				])
			}
		}
		if(lists.size()>1){
			for(int i =1; i<lists.size();i++){
				if(lists[i-1].last()+2 == lists[i].first()){
					return true
				}
			}
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
		currentConsecutiveCards.add(numericList[0])
		def bestConsecutiveCards = []
		bestConsecutiveCards.add(numericList[0])
		if(numericList.size() >= 2){
			int previousValue = numericList[0]
			for(int i=1;i<numericList.size();i++){
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
	
	/********************************************************************************************************
	 **************************************** TWO PAIR *************************************************
	 *********************************************************************************************************/
	boolean checkForTwoPair(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[2] >= 2){
			return true
		}
		return false
	}

	boolean checkForTwoPairDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[2] == 1){
			return true
		}
		return false
	}
}
