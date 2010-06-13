package repository

import buildingblocks._

abstract class AbstractPredicatesymbol

case class Predicatesymbol(_value: String) extends AbstractPredicatesymbol {
	val value: String = _value
	def ==(other: Predicatesymbol): Boolean = this.value.equals(other.value)
	def equals(other: Predicatesymbol): Boolean = (this == other)
	override def toString(): String = value
}

case class ^(p1: AbstractPredicatesymbol, p2: AbstractPredicatesymbol) extends AbstractPredicatesymbol {
 	override def toString(): String = "(" + p1 + ") ^ (" + p2 + ")"
}

case class ¬(p: AbstractPredicatesymbol) extends AbstractPredicatesymbol {
	override def toString(): String = "¬ (" + p + ")"
}

class PredicatesymbolGenerator(f: ServiceEntry[_,_], g: ServiceEntry[_,_], c1: ServiceEntry[_,_], c2: ServiceEntry[_,_]) {
	def position(reference: Symbol[_]): Int = reference match {
		case f.input | c1.input	=> 1
		case f.output | c2.output => 2
		case g.input | c1.output => 3
 		case _ => 4
	}
	
	def name(p: Predicate): String = {
		p.asInstanceOf[ExternalServiceReference[_,_]].implementation.getClass.getName.split("\\.").last
	}
	
	def apply(p: Predicate): AbstractPredicatesymbol = p match {
		case Not(p1)							=> ¬(this(p))
		case And(p1, p2)						=> ^(this(p1), this(p2))
		case Or(p1, p2)							=> ¬(^(¬(this(p1)), ¬(this(p2))))
		case PredicateService1(item)			=> Predicatesymbol(name(p) + position(item))
		case PredicateService2(item1, item2)	=> Predicatesymbol(name(p) + position(item1) + position(item2))
	}
}