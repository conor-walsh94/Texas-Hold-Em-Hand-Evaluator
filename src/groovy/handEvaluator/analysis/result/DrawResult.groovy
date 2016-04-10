package handEvaluator.analysis.result

import model.Card

abstract class DrawResult {

	int outs
	boolean handDraw = false;
	
	def turnProbability;
	def flopProbability;
	def turnOrFlopProbability;

	abstract void evaluate(ArrayList<Card> cards);
}
