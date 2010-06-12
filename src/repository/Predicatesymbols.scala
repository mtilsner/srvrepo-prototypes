package repository

import buildingblocks._

trait Predicatesymbol

class Predicatesymbol1(_predicate: Predicate, _pos: Int) extends Predicatesymbol {
	val predicate: Predicate = _predicate
 	override def toString: String = predicate.asInstanceOf[PredicateServiceEntry1[_]].implementation.getClass.getName + _pos
}

class Predicatesymbol2(_predicate: Predicate, _pos1: Int, _pos2: Int) extends Predicatesymbol {
	val predicate: Predicate = _predicate
	override def toString: String = predicate.asInstanceOf[PredicateServiceEntry2[_,_]].implementation.getClass.getName + _pos1 + _pos2
}

class ^(p1: Predicatesymbol, p2: Predicatesymbol) extends Predicatesymbol {
  override def toString(): String = "(" + p1 + ") ^ (" + p2 + ")"
}
object ^ {
  def apply(p1: Predicatesymbol, p2: Predicatesymbol): Predicatesymbol = { new ^(p1, p2) }
}

class ¬(p: Predicatesymbol) extends Predicatesymbol{
  override def toString(): String = "¬ (" + p + ")"
}
object ¬ {
  def apply(p: Predicatesymbol): Predicatesymbol = { new ¬(p) }
}

class PredicatesymbolGenerator(f: ServiceEntry[_,_], g: ServiceEntry[_,_], c1: ServiceEntry[_,_], c2: ServiceEntry[_,_]) {
	def position(reference: Symbol[_]): Int = reference match {
		case f.input | c1.input	=> 1
		case f.output | c2.output => 2
		case g.input | c1.output => 3
 		case _ => 4
	}
	
	def apply(p: Predicate): Predicatesymbol = p match {
		case Not(p1)							=> ¬(this(p))
		case And(p1, p2)						=> ^(this(p1), this(p2))
		case Or(p1, p2)							=> ¬(^(¬(this(p1)), ¬(this(p2))))
		case PredicateService1(item)			=> new Predicatesymbol1(p, position(item))
		case PredicateService2(item1, item2)	=> new Predicatesymbol2(p, position(item1), position(item2))
	}
}