package handEvaluator.analysis.result

import handEvaluator.HandEvaluator
import handEvaluator.HandProbability
import model.Card

abstract class DrawResult {

	int outs
	boolean handDraw = false;

	def turnProbability;
	def flopProbability;
	def turnOrFlopProbability;

	HandEvaluator handChecker = new HandEvaluator()
	HandProbability probabilityChecker = new HandProbability()

	abstract void evaluate(ArrayList<Card> cards);

	def calculateProbability(ArrayList<Card> cards){
		if(handDraw == true){
			if(cards == 5){
				probabilityChecker.calculateTurnProbability(outs)
				probabilityChecker.calculateTurnOrRiverProbability(outs)
			}
			probabilityChecker.calculateRiverProbability(outs)
		}
	}
}
