package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class FourOfAKindDrawResult extends DrawResult {

	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForFourOfAKindDraw(cards)){
			outs = 1;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
