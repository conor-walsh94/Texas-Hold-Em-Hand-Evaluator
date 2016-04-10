package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class FlushDrawResult extends DrawResult {

	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForFlushDraw(cards)){
			outs = 9;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
