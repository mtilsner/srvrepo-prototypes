package repository

import buildingblocks._

// so far, this is done hardcoded. In future, the classes of the input and output parameter should be identified dynamically
object FindConverters {
	def apply(g: ServiceEntry[_,_], f: ServiceEntry[_,_]): Tuple2[ServiceEntry[_,_],ServiceEntry[_,_]] = {
		(
			Repository.converters((classOf[domain.City], classOf[domain.Airport]))(0),
			Repository.converters((classOf[domain.Flight], classOf[domain.Flight]))(0)
		)
	}
}

object ReplacementCheck {
	def apply(g: ServiceEntry[_,_], f: ServiceEntry[_,_]): Boolean = {
		val (c1,c2) = FindConverters(g,f)
		if(!(c1!=null && c2!=null)) return false 
		
		val psgen = new PredicatesymbolGenerator(f,g,c1,c2)
		
		val f_pre = psgen(f.pre)
		val f_post = psgen(f.post)
		val g_pre = psgen(g.pre)
		val g_post = psgen(g.post)
		val c1_pre = psgen(c1.pre)
		val c1_post = psgen(c1.post)
		val c2_pre = psgen(c2.pre)
		val c2_post = psgen(c2.post)

		// current debug
		Console.println("f.pre   = " + f_pre)
		Console.println("f.post  = " + f_post)
		Console.println("g.pre   = " + g_pre)
		Console.println("g.post  = " + g_post)
		Console.println("c1.pre  = " + c1_pre)
		Console.println("c1.post = " + c1_post)
		Console.println("c2.pre  = " + c2_pre)
		Console.println("c2.post = " + c2_post)
/*
 *		// check tautologies
 *		// - prove that f.pre => c1.pre
 *		if(!TautologyChecker(¬(^(¬(f_pre),c1_post)))) return false
 * 
 *		// - prove that f.pre ^ c1.post => g.pre
 *		if(!TautologyChecker(¬(^(¬(^(f_pre,c1_post)),g_pre)))) return false
 * 
 *		// - prove that f.pre ^ c1.post ^ g.post => c2.pre
 *		if(!TautologyChecker(¬(^(¬(^(^(f_pre,c1_post),g_post)),c2_pre)))) return false
 *
 *		// - prove that f.pre ^ c1.post ^ g.post ^ c2.post => f.post
 *		if(!TautologyChecker(¬(^(¬(^(^(^(f_pre,c1_post),g_post),c2_post)),f_post)))) return false
 */
 		return true
 
	}
}

object ServiceReplacement {
	def apply(f: ServiceEntry[_,_]): ServiceEntry[_,_] = {
		for(g <- Repository.services.values if f != g) {
			if(ReplacementCheck(g,f)) return g
		}
		return null
	}
}