package repository

object False extends AbstractPredicatesymbol {
	override def toString: String = "false"
}
object True extends AbstractPredicatesymbol {
	override def toString: String = "true"
}

object TautologyChecker {
	def contains(formula: AbstractPredicatesymbol, symbol: AbstractPredicatesymbol): Boolean = symbol match {
		case ^(p1,p2)									=> contains(formula,p1) && contains(formula,p2)
		case _											=> containsSingle(formula,symbol)
	}
	
	def containsSingle(formula: AbstractPredicatesymbol, symbol: AbstractPredicatesymbol): Boolean = formula match {
		case ^(p1,p2)									=> containsSingle(p1,symbol) || containsSingle(p2,symbol)
		case _ if (formula == symbol)					=> true
		case _											=> false
	}
	
	def r(formula: AbstractPredicatesymbol): AbstractPredicatesymbol = formula match {
		// domain specifc replacement rules
		case ^(p1,p2)    if contains(formula,Predicatesymbol("IsAirportInCity13")) && contains(formula,Predicatesymbol("IsFlightFromAirport34")) && !contains(formula,Predicatesymbol("IsFlightFromCity14"))
														=> Console.println("DEBUG A1: "+formula); ^(formula,Predicatesymbol("IsFlightFromCity14"))
		case ^(p1,p2)	 if contains(formula,Predicatesymbol("IsFlightFromCity14")) && contains(formula,Predicatesymbol("IsEquals42")) && !contains(formula,Predicatesymbol("IsFlightFromCity12"))
														=> Console.println("DEBUG A2: "+formula); ^(formula,Predicatesymbol("IsFlightFromCity12"))
		
		// general replacement rules
		case ¬(True)									=> False
		case ¬(False)									=> True
		case ^(p1,¬(p2)) if (p1 == p2)					=> False
		case ^(¬(p1),p2) if (p1 == p2)					=> False
		case ^(¬(p1),p2) if contains(p1,p2)				=> False	
		case ^(p1,¬(p2)) if contains(p2,p1)				=> False	
		case ^(p,False)									=> False
		case ^(False,p)									=> False 
		case ^(True,True)								=> True
		case ¬(¬(p))									=> Console.println("DEBUG B: "+formula); p
		case ¬(p)										=> Console.println("DEBUG C: "+formula); val pn = r(p); if(p != pn) r(¬(pn)) else ¬(pn)
		case ^(p1,p2)									=> Console.println("DEBUG D: "+formula); val p1n = r(p1); val p2n = r(p2); if(   (p1n != p1 && p1n != ^(p1,p2) && p1n != ^(p2,p1)) 
																																	  || (p2n != p2  && p2n != ^(p1,p2) && p2n != ^(p2,p1))
																															 	   ) r(^(p1n,p2n)) else ^(p1,p2)
		// fallback (not successfull)
		case _											=> formula
	}
	
	def apply(formula: AbstractPredicatesymbol): Boolean = {
		if(r(formula) == True)
			return true
		else {
			Console.println("Rule could not be resolved: " + formula)
			return false
		}
	}
}