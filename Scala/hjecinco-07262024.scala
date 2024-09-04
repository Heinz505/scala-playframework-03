//1 and 2
trait MatterState
	vibrate: String


Class Gas extends MatterState:
	vibrate = "moving freely at high speeds."
Class Liquid extends MatterState:
	vibrate = "moving about, and sliding past each other."
Class Solid extends MatterState: 
	vibrate = "jiggling but generally not moving from place to place."
Class Plasma extends MatterState: 
	vibrate = "interacting with electrical and magnetic fields"

//3 and 4
enum Currency:
	USD, EUR, GBP
	Money: Double

def convertMoney(amount: Double, target: Currency): Money =
	target match {
		case USD 
			if(target==USD) then amount
			else if(target==EUR) then amount*0.85
			else if(target==GBP) then amount*0.73
		case EUR 
			if(target==USD) then amount*1.18
			else if(target==EUR) then amount
			else if(target==GBP) then amount*0.86
		case GBP 
			if(target==USD) then amount*1.37
			else if(target==EUR) then amount*1.16
			else if(target==GBP) then amount
		case _ => null
	}
