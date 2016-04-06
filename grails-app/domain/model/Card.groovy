package model

class Card {

	String value
	String suit
	int identifier
	
    static constraints = {
		value(inList : ["2","3","4","5","6","7","8","9","10","J","Q","K","A"])
		suit(inList: ["D","H","C","S"])
		identifier(min:0,max:207,unique:true)
    }
}
