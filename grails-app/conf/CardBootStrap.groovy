import model.Card


class CardBootStrap {

	def init = { servletContext ->
			int identifier = 0;
			["D","H","C","S"].each{ suit ->
				int numericValue = 2;
				["2","3","4","5","6","7","8","9","10","J","Q","K","A"].each{ value ->
					Card card = new Card(value:value,suit:suit,identifier:identifier,numericValue:numericValue)
					card.save(flush:true)
					identifier++;
					numericValue++;
				}
			}	
	}
	
	def destroy = {
	}
	
}
