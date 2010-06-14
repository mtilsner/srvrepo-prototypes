package repository

object False extends AbstractPredicatesymbol {
	override def toString: String = "false"
}
object True extends AbstractPredicatesymbol {
	override def toString: String = "true"
}

object TautologyChecker {
	def contains(formula: AbstractPredicatesymbol, symbol: AbstractPredicatesymbol): Boolean = symbol match {
		case ^(p1,p2)								=> contains(formula,p1) && contains(formula,p2)
		case _										=> containsSingle(formula,symbol)
	}
	
	def containsSingle(formula: AbstractPredicatesymbol, symbol: AbstractPredicatesymbol): Boolean = formula match {
		case _ if (formula == symbol)				=> true
		case ^(p1,p2)								=> containsSingle(p1,symbol) || containsSingle(p2,symbol)
		case _										=> false
	}
	
	def replace(in: AbstractPredicatesymbol, from: AbstractPredicatesymbol, to: AbstractPredicatesymbol): AbstractPredicatesymbol = in match {
		case _ if (in == from)						=> to
		case ^(p1,p2)								=> ^(replace(p1,from,to),replace(p2,from,to))
		case _										=> in
	}
	
	def rules(formula: AbstractPredicatesymbol): Seq[Tuple2[AbstractPredicatesymbol, AbstractPredicatesymbol]] =
		Repository.interferenceRules.toSeq.filter(
			(p: Tuple2[AbstractPredicatesymbol,AbstractPredicatesymbol])
			=> contains(formula,p._1) && !contains(formula,p._2)
		)
	
	def applyRule(formula: AbstractPredicatesymbol, rule: Tuple2[AbstractPredicatesymbol, AbstractPredicatesymbol]): AbstractPredicatesymbol = formula match {
		case ^(p1,p2) if contains(p1,rule._1) || contains(p2,rule._1)
													=> ^(applyRule(p1,rule),applyRule(p2,rule))
		case ^(p1,p2) if contains(formula,rule._1)	=> ^(formula,rule._2)
		case _ if contains(formula,rule._1)			=> ^(formula,rule._2)
		case _										=> formula
	}

	def r(formula: AbstractPredicatesymbol): AbstractPredicatesymbol = formula match {
		// domain specifc replacement rules
		case _ if rules(formula).size > 0			=>  applyRule(formula, rules(formula).head);
		// general replacement rules
		case ¬(True)								=>	False
		case ¬(False)								=>	True
		case ¬(¬(p))								=>	r(p)
		case ¬(p)									=>	val pn = r(p); if(p != pn) r(¬(pn)) else ¬(pn)
		case ^(p,False)								=>	False
		case ^(False,p)								=>	False
		case ^(True,True)							=>	True
		case ^(p1,¬(p2)) if (p1 == p2)				=>	False
		case ^(¬(p1),p2) if (p1 == p2)				=>	False
		case ^(p1,¬(p2)) if contains(p1,p2)			=>	False
		case ^(¬(p2),p1) if contains(p1,p2)			=>	False
		case ^(p1,p2)								=>	val p1n = r(p1); val p2n = r(p2);
														if(	(p1n != p1 && p1n != ^(p1,p2) && p1n != ^(p2,p1)) 
															|| (p2n != p2  && p2n != ^(p1,p2) && p2n != ^(p2,p1))
														) r(^(p1n,p2n)) else ^(p1,p2)
		// default rule
		case _										=>	formula
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