package handEvaluator

import java.util.ArrayList;

import model.Card;

class HandEvaluator {

	def analyzeFlop(ArrayList<Card> cards){
		HandProbability hp = new HandProbability()
		def madeHands = []
		def resultList = []
		def draws = []
		if(checkForStraightFlush(cards)){
			madeHands.add("straightFlush")
		}
		else if(checkForOpenEndedStraightFlushDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"straightFlush")
			rs.setTurnProbability(hp.calculateTurnProbability(2))
			rs.setRiverProbability(hp.calculateRiverProbability(2))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(2))
			resultList.add(rs)
		}
		else if(checkForInsideStraightFlushDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"straightFlush")
			rs.setTurnProbability(hp.calculateTurnProbability(1))
			rs.setRiverProbability(hp.calculateRiverProbability(1))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(1))
			resultList.add(rs)
		}

		if(checkForFourOfAKind(cards)){
			madeHands.add("fours")
		}
		else if(checkForFourOfAKindDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"fours")
			rs.setTurnProbability(hp.calculateTurnProbability(1))
			rs.setRiverProbability(hp.calculateRiverProbability(1))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(1))
			resultList.add(rs)
		}


		if(checkForFlush(cards)){
			madeHands.add("flush")
		}
		else if(checkForFlushDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"flush")
			rs.setTurnProbability(hp.calculateTurnProbability(9))
			rs.setRiverProbability(hp.calculateRiverProbability(9))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(9))
			resultList.add(rs)
		}


		if(checkForFullHouse(cards)){
			madeHands.add("fullHouse")
		}
		else if(checkForFullHouseDraw(cards)){
			if(checkForTwoPair(cards)){
				ResultHolder rs = new ResultHolder(hand:"fullHouse")
				rs.setTurnProbability(hp.calculateTurnProbability(4))
				rs.setRiverProbability(hp.calculateRiverProbability(4))
				rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(4))
				resultList.add(rs)
			}
			else if(checkForThreeOfAKind(cards)){
				ResultHolder rs = new ResultHolder(hand:"fullHouse")
				rs.setTurnProbability(hp.calculateTurnProbability(6))
				rs.setRiverProbability(hp.calculateRiverProbability(9))
				rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(6,9))
				resultList.add(rs)
			}
		}

		if(checkForStraight(cards)){
			madeHands.add("straight")
		}
		else if(checkForOpenEndedStraightDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"straight")
			rs.setTurnProbability(hp.calculateTurnProbability(8))
			rs.setRiverProbability(hp.calculateRiverProbability(8))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(8))
			resultList.add(rs)
		}
		else if(checkForInsideStraightDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"straight")
			rs.setTurnProbability(hp.calculateTurnProbability(4))
			rs.setRiverProbability(hp.calculateRiverProbability(4))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(4))
			resultList.add(rs)
		}

		if(checkForThreeOfAKind(cards)){
			madeHands.add("3s")
		}
		else if(checkForThreeOfAKindDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"3s")
			rs.setTurnProbability(hp.calculateTurnProbability(2))
			rs.setRiverProbability(hp.calculateRiverProbability(2))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(2))
			resultList.add(rs)
		}

		if(checkForTwoPair(cards)){
			madeHands.add("2pair")
		}
		else if(checkForTwoPairDraw(cards)){
			ResultHolder rs = new ResultHolder(hand:"2pair")
			rs.setTurnProbability(hp.calculateTurnProbability(9))
			rs.setRiverProbability(hp.calculateRiverProbability(12))
			rs.setTurnOrRiverProbability(hp.calculateTurnOrRiverProbability(9,12))
			resultList.add(rs)
		}

		return madeHands + resultList
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
	 **************************************** Full House *************************************************
	 *********************************************************************************************************/
	boolean checkForFullHouse(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[3] >= 1 && secondCount[2]>= 1){
			return true
		}
		return false
	}

	boolean checkForFullHouseDraw(ArrayList<Card> cards){
		boolean twoPair = checkForTwoPair(cards)
		boolean threes = checkForThreeOfAKind(cards)
		if(twoPair || threes){
			return true
		}
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
	 **************************************** THREE OF A KIND *************************************************
	 *********************************************************************************************************/
	boolean checkForThreeOfAKind(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[3] >= 1){
			return true
		}
		return false
	}

	boolean checkForThreeOfAKindDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[2] >= 1){
			return true
		}
		return false
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

	/********************************************************************************************************
	 **************************************** PAIR *************************************************
	 *********************************************************************************************************/
	boolean checkForPair(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[2] >= 1){
			return true
		}
		return false
	}

	boolean checkForPairDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if(secondCount[2] == 1){
			return true
		}
		return false
	}
}
