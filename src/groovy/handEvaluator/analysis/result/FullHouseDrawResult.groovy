package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class FullHouseDrawResult extends DrawResult {

	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForTwoPair(cards)){
			outs = 4;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
