package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class StraightDrawResult extends DrawResult {

	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForOpenEndedStraightDraw(cards)){
			outs = 8;
			handDraw = true;
		}
		else if(handChecker.checkForInsideStraightDraw(cards)){
			outs = 4;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
