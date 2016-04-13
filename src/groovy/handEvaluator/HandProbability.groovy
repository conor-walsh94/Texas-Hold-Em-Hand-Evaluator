package handEvaluator

import model.Card

class HandProbability {


	def calculateTurnProbability(int outs){
		return outs/47
	}

	def calculateRiverProbability(int outs){
		return outs/46
	}

	def calculateTurnOrRiverProbability(int outs){
		return 1-(((47-outs)/47) * ((46-outs)/46))
	}
	
	def calculateTurnOrRiverProbability(int turnOuts, int riverOuts){
		return 1-(((47-turnOuts)/47) * ((46-riverOuts)/46))
	}

}
