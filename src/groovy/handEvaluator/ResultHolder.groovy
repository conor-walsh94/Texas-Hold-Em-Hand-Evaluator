package handEvaluator

class ResultHolder {
	
	String hand;
	
	def turnProbability;
	def riverProbability;
	def turnOrRiverProbability;
	
	String toString(){
		String str = "[Hand : $hand, Turn Odds : $turnProbability, River Odds : $riverProbability, Turn or River $turnOrRiverProbability]"
	}
}
