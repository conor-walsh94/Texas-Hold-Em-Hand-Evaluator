package handEvaluator

import model.Card

class HandProbability {


	static double calculateTurnProbability(int outs){
		return outs/47
	}

	static double calculateRiverProbability(int outs){
		return outs/46
	}

	
	static double calculateTurnOrRiverProbability(int turnOuts, int riverOuts){
		return 1-(((47-turnOuts)/47) * ((46-riverOuts)/46))
	}

}
