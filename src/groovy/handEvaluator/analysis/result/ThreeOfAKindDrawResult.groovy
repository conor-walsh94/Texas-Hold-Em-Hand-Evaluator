package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class ThreeOfAKindDrawResult extends DrawResult {

	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForPair(cards)){
			outs = 2;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
