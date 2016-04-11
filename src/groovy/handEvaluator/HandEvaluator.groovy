package handEvaluator

import handEvaluator.analysis.result.DrawAnalysisResult
import java.util.ArrayList;

import model.Card;

class HandEvaluator {

	def analyzeFlop(ArrayList<Card> cards){
		HandProbability hp = new HandProbability()
		def madeHands = []
		def drawResultList = []
		def draws = []
		int handRank = 0;
		if(checkForStraightFlush(cards)){
			handRank = 8
		}
		else if(checkForFourOfAKind(cards)){
			handRank = 7
		}
		else if(checkForFullHouse(cards)){
			handRank = 6
		}
		else if(checkForFlush(cards)){
			handRank = 5
		}
		else if(checkForStraight(cards)){
			handRank = 4
		}
		else if(checkForThreeOfAKind(cards)){
			handRank = 3
		}
		else if(checkForTwoPair(cards)){
			handRank = 2
		}
		else if(checkForPair(cards)){
			handRank = 1
		}

		switch(handRank){
			case 0:
				drawResultList.add(checkForPairDraw(cards))
			case 1:
				drawResultList.add(checkForTwoPairDraw(cards))
			case 2:
				drawResultList.add(checkForThreeOfAKindDraw(cards))
			case 3:
				drawResultList.add(checkForOpenEndedStraightDraw(cards))
				drawResultList.add(checkForInsideStraightDraw(cards))
			case 4:
				drawResultList.add(checkForFlushDraw(cards))
			case 5:
				drawResultList.add(checkForFullHouseDraw(cards))
			case 6:
				drawResultList.add(checkForFourOfAKindDraw(cards))
			case 7:
				drawResultList.add(checkForOpenEndedStraightFlushDraw(cards))
				drawResultList.add(checkForInsideStraightFlushDraw(cards))
				break;
		}
		return ["hands" : [handRank], "draws" :  drawResultList]
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

	DrawAnalysisResult checkForOpenEndedStraightFlushDraw(ArrayList<Card> cards){
		boolean found = false;
		def suitedSets = []
		suitedSets.add(["name":"H","cards":cards.findAll{it.suit == "H"}])
		suitedSets.add(["name":"D","cards":cards.findAll{it.suit == "D"}])
		suitedSets.add(["name":"S","cards":cards.findAll{it.suit == "S"}])
		suitedSets.add(["name":"C","cards":cards.findAll{it.suit == "C"}])
		suitedSets.each{
			if(it.cards.size() >= 4){
				boolean flag = checkForOpenEndedStraightDraw(it.cards).found
				if(flag){
					found = true
				}
			}
		}
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"openEndedStraightFlush", turnOuts:2,riverOuts:2,found:found)
		return result
	}

	DrawAnalysisResult checkForInsideStraightFlushDraw(ArrayList<Card> cards){
		boolean found = false;
		def suitedSets = []
		suitedSets.add(["name":"H","cards":cards.findAll{it.suit == "H"}])
		suitedSets.add(["name":"D","cards":cards.findAll{it.suit == "D"}])
		suitedSets.add(["name":"S","cards":cards.findAll{it.suit == "S"}])
		suitedSets.add(["name":"C","cards":cards.findAll{it.suit == "C"}])
		suitedSets.each{
			if(it.cards.size() >= 4){
				boolean flag = checkForInsideStraightDraw(it.cards).found
				if(flag){
					found = true
				}
			}
		}
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"insideStraightFlush", turnOuts:1,riverOuts:1,found:found)
		return result
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

	DrawAnalysisResult checkForFourOfAKindDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		counts.each{ key, value ->
			if(value > highestCount){
				highestCount = value
			}
		}
		boolean found = (highestCount == 3)
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"fourOfAKind", turnOuts:1,riverOuts:1,found:found)
		return result
	}

	/********************************************************************************************************
	 **************************************** Full House *************************************************
	 *********************************************************************************************************/
	boolean checkForFullHouse(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		if((secondCount[3] >= 2) || (secondCount[3] >= 1 && secondCount[2]>= 1)){
			return true
		}
		return false
	}

	DrawAnalysisResult checkForFullHouseDraw(ArrayList<Card> cards){
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"fullHouse", found:false)
		//three pair only counts on river
		if(secondCount[2] == 3){
			result.setFound(true)
			result.setRiverOuts(6)
		}
		if(checkForTwoPair(cards)){
			result.setFound(true)
			result.setTurnOuts(4)
			result.setRiverOuts(4)
		}
		if(checkForThreeOfAKind(cards)){
			result.setFound(true)
			result.setTurnOuts(6)
			result.setRiverOuts(9)
		}
		return result
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

	DrawAnalysisResult checkForFlushDraw(ArrayList<Card> cards){
		def suitCounts = []
		suitCounts.add(["name":"H","count":cards.findAll{it.suit == "H"}.size()])
		suitCounts.add(["name":"D","count":cards.findAll{it.suit == "D"}.size()])
		suitCounts.add(["name":"S","count":cards.findAll{it.suit == "S"}.size()])
		suitCounts.add(["name":"C","count":cards.findAll{it.suit == "C"}.size()])
		suitCounts.sort{-it.count}
		int highestSuitCount = suitCounts[0].count
		boolean found = (highestSuitCount == 4)
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"flush", turnOuts:9,riverOuts:9,found:found)
		return result
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

	DrawAnalysisResult checkForOpenEndedStraightDraw(ArrayList<Card> cards){
		def cardList = getHighestConsecutiveCardList(cards)
		//turn 8
		//river 8
		boolean found = false
		if(cardList?.first() > 1 && cardList?.last() < 14){
			if(cardList.size() >= 4){
				found = true
			}
		}
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"openEndedStraight", turnOuts:8,riverOuts:8,found:found)
		return result
	}

	DrawAnalysisResult checkForInsideStraightDraw(ArrayList<Card> cards){
		def cardList = getHighestConsecutiveCardList(cards)
		boolean found = false
		if(cardList.containsAll([1, 2, 3, 4]) || cardList.containsAll([11, 12, 13, 14]) || cardList.containsAll([10, 11, 12, 14]) || cardList.containsAll([1, 3, 4, 5])){
			found = true
		}
		else{
			def lists = []
			def numericList = cards.collect{it.numericValue}
			if(numericList.contains(14)){
				numericList.add(1)
			}
			numericList.sort()
			numericList = numericList.unique()
			def currentOrderedList = [numericList[0]]
			for(int i =1; i<numericList.size();i++){
				if(numericList[i-1]+1 == numericList[i]){
					currentOrderedList.add(numericList[i])
				}
				else{
					lists.add(currentOrderedList)
					currentOrderedList = [numericList[i]]
				}
				if(i== numericList.size()-1){
					lists.add(currentOrderedList)
				}
			}
			if(lists.size()>1){
				for(int i =1; i<lists.size();i++){
					if(lists[i-1].last()+2 == lists[i].first() && ((lists[i-1].size() + lists[i].size()) >= 4)){
						found =  true
					}
				}
			}
		}
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"insideStraight", turnOuts:4,riverOuts:4,found:found)
		return result
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

	DrawAnalysisResult checkForThreeOfAKindDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		def pairs = secondCount[2]
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"threeOfAKind", found:false)
		if(pairs >= 1){
			result.setTurnOuts(pairs*2)
			result.setRiverOuts(pairs*2)
			result.setFound(true)
		}
		return result
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

	DrawAnalysisResult checkForTwoPairDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}
		def found = (secondCount[2] == 1)
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"twoPair", found:found, turnOuts:9,riverOuts:12)
		return result
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

	DrawAnalysisResult checkForPairDraw(ArrayList<Card> cards){
		int highestCount = 0;
		def counts = cards.countBy{it.numericValue}
		def secondCount = counts.countBy{it.value}

		def found = (secondCount[2] == 0)
		DrawAnalysisResult result = new DrawAnalysisResult(identifier:"pair", found:found, turnOuts:15,riverOuts:18)
		return result
	}
}
