package handEvaluator

import spock.lang.Specification;
import model.Card
import grails.test.mixin.TestFor
import handEvaluator.analysis.result.DrawAnalysisResult

class HandEvaluatorSpec  extends Specification {

	HashMap diamonds = new HashMap()
	HashMap hearts = new HashMap()
	HashMap clubs = new HashMap()
	HashMap spades = new HashMap()
	
	HandEvaluator evaluator = new HandEvaluator()

	void setup() {
		int identifier = 0;
		["D","H","C","S"].each{ suit ->
			int numericValue = 2;
			["2","3","4","5","6","7","8","9","10","J","Q","K","A"].each{ value ->
				Card card = new Card(value:value,suit:suit,identifier:identifier,numericValue:numericValue)
				if(suit == "D"){
					diamonds.put(value, card)
				}
				else if(suit == "H"){
					hearts.put(value, card)
				}
				else if(suit == "C"){
					clubs.put(value, card)
				}
				else if(suit == "S"){
					spades.put(value, card)
				}
				identifier++;
				numericValue++;
			}
		}
	}
	
	
	/********************************************************************************************************
	 ************************************ STRAIGHT FLUSH **********************************************
	 *********************************************************************************************************/
	void "test straight flush checker"() {
		when : "I check for a straight flush with suited run of consecutive cards"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["9"],diamonds["8"],diamonds["5"],diamonds["7"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker starting at the ace"() {
		when : "I check for a straight flush with suited run of consecutive cards starting at the ace"
		boolean res = evaluator.checkForStraightFlush([diamonds["3"],diamonds["2"],diamonds["4"],diamonds["5"],diamonds["A"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker finishing at the ace"() {
		when : "I check for a straight flush with suited run of consecutive cards finishing at the ace"
		boolean res = evaluator.checkForStraightFlush([diamonds["A"],diamonds["K"],diamonds["J"],diamonds["10"],diamonds["Q"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker with a streak of 6"() {
		when : "I check for a straight flush with suited run of 6 consecutive cards"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["9"],diamonds["8"],diamonds["5"],diamonds["7"],diamonds["4"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker with a streak an additional offsuit card"() {
		when : "I check for a straight flush with suited run of consecutive cards and an additional offsuit card"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["9"],diamonds["8"],diamonds["5"],diamonds["7"],clubs["4"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker with a streak a duplicate streak card of a different suit"() {
		when : "I check for a straight flush with suited run of consecutive cards and a duplicate streak card of a different suit"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["9"],diamonds["8"],diamonds["5"],diamonds["7"],clubs["6"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight flush checker with a non consecutive streak of suited cards"() {
		when : "I check for a straight flush with suited run of non-consecutive cards"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["10"],diamonds["8"],diamonds["5"],diamonds["7"]])
		then : "The result is false"
		res == false
	}
	
	void "test straight flush checker with a non consecutive streak of 6 suited cards"() {
		when : "I check for a straight flush with suited run of non-consecutive cards"
		boolean res = evaluator.checkForStraightFlush([diamonds["6"],diamonds["10"],diamonds["8"],diamonds["5"],diamonds["7"],diamonds["J"]])
		then : "The result is false"
		res == false
	}
	
	void "test straight flush checker with a consecutive streak of unsuited cards"() {
		when : "I check for a straight flush with non-suited run of cards"
		boolean res = evaluator.checkForStraightFlush([diamonds["A"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["4"]])
		then : "The result is false"
		res == false
	}
	
	void "test for open ended straight flush draw"(){
		when : "I check for an open ended straight flush draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["7"],diamonds["8"],diamonds["9"],diamonds["10"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for open ended straight flush draw starting at the ace"(){
		when : "I check for an open ended straight flush draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["2"],diamonds["4"],diamonds["3"],diamonds["5"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for open ended straight flush draw ending at the ace"(){
		when : "I check for an open ended straight flush draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["K"],diamonds["J"],diamonds["Q"],diamonds["10"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for open ended straight flush draw including an ending ace"(){
		when : "I check for an open ended straight flush draw including an ending ace"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["K"],diamonds["J"],diamonds["Q"],diamonds["A"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test for open ended straight flush draw including a starting ace"(){
		when : "I check for an open ended straight flush draw including a starting ace"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["A"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	
	void "test for open ended straight flush draw including a gapped streak"(){
		when : "I check for an open ended straight flush draw including a gapped streak"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightFlushDraw([diamonds["4"],diamonds["2"],diamonds["3"],diamonds["6"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test for an inside straight flush draw with 2 cards on two sides"(){
		when : "test for an inside straight flush draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightFlushDraw([diamonds["5"],diamonds["2"],diamonds["3"],diamonds["6"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for an inside straight flush draw with 1 card followed by a gap followed by 3 cards"(){
		when : "test for an inside straight flush draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightFlushDraw([diamonds["4"],diamonds["2"],diamonds["5"],diamonds["6"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for an inside straight flush draw with 3 cards followed by a gap followed by 1 card"(){
		when : "test for an inside straight flush draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightFlushDraw([diamonds["4"],diamonds["2"],diamonds["3"],diamonds["6"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for an inside straight flush draw with a high ace"(){
		when : "test for an inside straight flush draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightFlushDraw([diamonds["J"],diamonds["K"],diamonds["Q"],diamonds["A"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test for an inside straight flush draw an open ended straight flush draw"(){
		when : "test for an inside straight flush draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightFlushDraw([diamonds["4"],diamonds["5"],diamonds["6"],diamonds["7"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** FOUR OF A KIND **************************************************
	 *********************************************************************************************************/
	void "test four of a kind checker"() {
		when : "I check for a four of a kind"
		boolean res = evaluator.checkForFourOfAKind([diamonds["6"],clubs["6"],hearts["6"],spades["6"],hearts["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test four of a kind checker without a hand"() {
		when : "I check for a four of a kind"
		boolean res = evaluator.checkForFourOfAKind([diamonds["6"],clubs["6"],hearts["6"],spades["5"],hearts["5"]])
		then : "The result is false"
		res == false
	}
	
	void "test four of a kind draw checker"() {
		when : "I check for a four of a kind draw"
		DrawAnalysisResult res = evaluator.checkForFourOfAKindDraw([diamonds["6"],clubs["6"],hearts["6"],spades["5"],hearts["5"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test four of a kind draw checker without a draw"() {
		when : "I check for a four of a kind draw"
		DrawAnalysisResult res = evaluator.checkForFourOfAKindDraw([diamonds["6"],clubs["6"],hearts["4"],spades["5"],hearts["5"]])
		then : "The result is false"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** FULL HOUSE **************************************************
	 *********************************************************************************************************/
	void "test full house checker"() {
		when : "I check for a full house"
		boolean res = evaluator.checkForFullHouse([diamonds["6"],clubs["6"],hearts["6"],diamonds["5"],hearts["5"]])
		then : "The result is true"
		res == true
	}
	
	
	void "test full house checker with additional card"() {
		when : "I check for a full house"
		boolean res = evaluator.checkForFullHouse([diamonds["6"],clubs["6"],hearts["6"],diamonds["5"],hearts["5"], clubs["9"]])
		then : "The result is true"
		res == true
	}
	
	void "test full house checker with additional card already in house"() {
		when : "I check for a full house"
		boolean res = evaluator.checkForFullHouse([diamonds["6"],clubs["6"],hearts["6"],diamonds["5"],hearts["5"], clubs["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test full house draw checker with 3 of a kind"() {
		when : "I check for a full house draw"
		DrawAnalysisResult res = evaluator.checkForFullHouseDraw([diamonds["6"],clubs["6"],hearts["6"],diamonds["5"],hearts["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test full house draw checker with 2 pair"() {
		when : "I check for a full house draw"
		DrawAnalysisResult res = evaluator.checkForFullHouseDraw([diamonds["6"],clubs["6"],hearts["5"],diamonds["5"],hearts["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test full house draw checker with 3 pair"() {
		when : "I check for a full house draw"
		DrawAnalysisResult res = evaluator.checkForFullHouseDraw([diamonds["6"],clubs["6"],hearts["5"],diamonds["5"],hearts["4"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test full house draw checker without a draw"() {
		when : "I check for a full house draw"
		DrawAnalysisResult res = evaluator.checkForFullHouseDraw([diamonds["6"],clubs["6"],hearts["2"],diamonds["5"],hearts["4"]])
		then : "The result is false"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** FLUSH **************************************************
	 *********************************************************************************************************/
	void "test flush checker"() {
		when : "I check for a flush"
		boolean res = evaluator.checkForFlush([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],diamonds["9"]])
		then : "The result is true"
		res == true
	}
	
	
	void "test flush checker with an extra card"() {
		when : "I check for a flush with an extra card"
		boolean res = evaluator.checkForFlush([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],diamonds["9"],diamonds["10"]])
		then : "The result is true"
		res == true
	}
	
	void "test flush checker with an extra unsuited card"() {
		when : "I check for a flush with an extra card"
		boolean res = evaluator.checkForFlush([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],diamonds["9"],clubs["9"]])
		then : "The result is true"
		res == true
	}
	
	void "test flush checker without a flush"() {
		when : "I check for a flush with an extra card"
		boolean res = evaluator.checkForFlush([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["A"],clubs["9"]])
		then : "The result is false"
		res == false
	}
	
	void "test flush draw checker"() {
		when : "I check for a flush draw"
		DrawAnalysisResult res = evaluator.checkForFlushDraw([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["A"],clubs["9"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test flush draw checker without a draw"() {
		when : "I check for a flush draw"
		DrawAnalysisResult res = evaluator.checkForFlushDraw([diamonds["6"],diamonds["2"],diamonds["3"],clubs["4"],clubs["A"],clubs["9"]])
		then : "The result is false"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** STRAIGHT **************************************************
	 *********************************************************************************************************/
	void "test straight checker"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight checker with extra straight card"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["5"],clubs["7"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight checker with extra non straight card"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["5"],clubs["J"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight checker without a straight"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["6"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["8"]])
		then : "The result is false"
		res == false
	}
	
	void "test straight checker with ace low"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["5"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["A"]])
		then : "The result is true"
		res == true
	}
	
	void "test straight checker with ace high"() {
		when : "I check for a straight"
		boolean res = evaluator.checkForStraight([diamonds["10"],diamonds["J"],diamonds["Q"],diamonds["K"],clubs["A"]])
		then : "The result is true"
		res == true
	}
	
	void "test open ended straight draw checker"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["10"],diamonds["J"],diamonds["Q"],diamonds["9"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test open ended straight draw checker waiting on ace high"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["10"],diamonds["J"],diamonds["Q"],diamonds["K"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test open ended straight draw checker waiting on ace low"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["2"],diamonds["3"],diamonds["4"],diamonds["5"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test open ended straight draw checker with an ace low"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["J"],diamonds["Q"],diamonds["K"],diamonds["A"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test open ended straight draw checker with an ace high"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["2"],diamonds["3"],diamonds["4"],diamonds["A"],clubs["4"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test open ended straight draw checker with an inside draw"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForOpenEndedStraightDraw([diamonds["6"],diamonds["7"],diamonds["9"],diamonds["10"],clubs["J"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test inside straight draw checker"() {
		when : "I check for an inside straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["6"],diamonds["7"],diamonds["9"],diamonds["10"],clubs["4"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test inside straight draw checker with extra card"() {
		when : "I check for an inside straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["6"],diamonds["7"],diamonds["9"],diamonds["10"],clubs["J"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test inside straight draw checker with an open ended straight draw"() {
		when : "I check for an inside straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["6"],diamonds["7"],diamonds["9"],diamonds["8"],clubs["2"]])
		then : "The result is false"
		res.found == false
	}
	
	void "test inside straight draw checker with an ace low"() {
		when : "I check for a open ended straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["A"],diamonds["2"],diamonds["3"],diamonds["4"],clubs["J"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test inside straight draw checker with an ace high"() {
		when : "I check for an inside  straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["A"],diamonds["K"],diamonds["Q"],diamonds["4"],clubs["J"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test inside straight draw checker without a draw"() {
		when : "I check for an inside ended straight draw"
		DrawAnalysisResult res = evaluator.checkForInsideStraightDraw([diamonds["6"],diamonds["7"],diamonds["9"],diamonds["2"],clubs["3"]])
		then : "The result is false"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** THREE OF A KIND **************************************************
	 *********************************************************************************************************/
	void "test three of a kind checker"() {
		when : "I check for a three of a kind"
		boolean res = evaluator.checkForThreeOfAKind([diamonds["6"],clubs["6"],hearts["6"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test three of a kind checker without a hand"() {
		when : "I check for a three of a kind"
		boolean res = evaluator.checkForThreeOfAKind([diamonds["6"],clubs["6"],hearts["4"],diamonds["4"],clubs["5"]])
		then : "The result is false"
		res == false
	}
	
	void "test three of a kind draw checker with two pair"() {
		when : "I check for a three of a kind"
		DrawAnalysisResult res = evaluator.checkForThreeOfAKindDraw([diamonds["6"],clubs["6"],hearts["4"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test three of a kind draw checker with single pair"() {
		when : "I check for a three of a kind"
		DrawAnalysisResult res = evaluator.checkForThreeOfAKindDraw([diamonds["6"],clubs["6"],hearts["3"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test three of a kind draw checker with three pair"() {
		when : "I check for a three of a kind"
		DrawAnalysisResult res = evaluator.checkForThreeOfAKindDraw([diamonds["6"],clubs["6"],hearts["3"],diamonds["3"],clubs["5"], hearts["5"]])
		then : "The result is true"
		res.found == true
	}
	
	/********************************************************************************************************
	 ***************************************** TWO PAIR **************************************************
	 *********************************************************************************************************/
	void "test two pair checker"() {
		when : "I check for two pair"
		boolean res = evaluator.checkForTwoPair([diamonds["6"],clubs["6"],hearts["4"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test two pair checker without hand"() {
		when : "I check for two pair"
		boolean res = evaluator.checkForTwoPair([diamonds["6"],clubs["6"],hearts["3"],diamonds["4"],clubs["5"]])
		then : "The result is true"
		res == false
	}
	
	void "test two pair checker with three pair"() {
		when : "I check for two pair"
		boolean res = evaluator.checkForTwoPair([diamonds["6"],clubs["6"],hearts["3"],diamonds["3"],clubs["5"], diamonds["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test two pair draw checker"() {
		when : "I check for two pair draw"
		DrawAnalysisResult res = evaluator.checkForTwoPairDraw([diamonds["6"],clubs["6"],hearts["4"],diamonds["3"],clubs["5"]])
		then : "The result is true"
		res.found == true
	}
	
	void "test two pair draw checker without draw"() {
		when : "I check for two pair draw"
		DrawAnalysisResult res = evaluator.checkForTwoPairDraw([diamonds["6"],clubs["A"],hearts["4"],diamonds["3"],clubs["5"]])
		then : "The result is dalse"
		res.found == false
	}
	
	/********************************************************************************************************
	 ***************************************** PAIR **************************************************
	 *********************************************************************************************************/
	void "test pair checker"() {
		when : "I check for a pair"
		boolean res = evaluator.checkForPair([diamonds["6"],clubs["6"],hearts["4"],diamonds["A"],clubs["5"]])
		then : "The result is true"
		res == true
	}
	
	void "test pair checker without hand"() {
		when : "I check for a pair"
		boolean res = evaluator.checkForPair([diamonds["6"],clubs["3"],hearts["4"],diamonds["A"],clubs["5"]])
		then : "The result is false"
		res == false
	}
	
}
