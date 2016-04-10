package handEvaluator.analysis.result

import java.util.ArrayList;

import model.Card;

class StraightFlushDrawResult extends DrawResult{

	
	void evaluate(ArrayList<Card> cards){
		if(handChecker.checkForOpenEndedStraightFlushDraw(cards)){
			outs = 2;
			handDraw = true;
		}
		else if(handChecker.checkForInsideStraightFlushDraw(cards)){
			outs = 1;
			handDraw = true;
		}
		calculateProbability(cards)
	}
}
