package buildingblocks

// Predicate traits
trait Predicate extends Symbol[Predicate] {
	def test(values: Map[Symbol[_],_]): Boolean
}
trait Predicate1[T] extends Predicate {
	def item: T
}
trait Predicate2[S,T] extends Predicate {
	def item1: S
	def item2: T
}

// Predicate combinators
case class Not(_item: Predicate) extends Predicate1[Predicate] {
	val item: Predicate = _item 
	def test(values: Map[Symbol[_],_]): Boolean = !item.asInstanceOf[Predicate].test(values)
}

case class And(_item1: Predicate, _item2: Predicate) extends Predicate2[Predicate,Predicate] {
	val item1: Predicate = _item1
	val item2: Predicate = _item2
	def test(values: Map[Symbol[_],_]): Boolean = item1.asInstanceOf[Predicate].test(values) && item2.asInstanceOf[Predicate].test(values)
}

case class Or(_item1: Predicate, _item2: Predicate) extends Predicate2[Predicate,Predicate] {
	val item1: Predicate = _item1
	val item2: Predicate = _item2
	def test(values: Map[Symbol[_],_]): Boolean = item1.asInstanceOf[Predicate].test(values) || item2.asInstanceOf[Predicate].test(values)
}

// Service predicates
case class PredicateService1[T](_item: Symbol[T]) extends Predicate1[Symbol[T]] {
	val item: Symbol[T] = _item
	def test(values: Map[Symbol[_],_]): Boolean = true
}
case class PredicateService2[S,T](_item1: Symbol[S], _item2: Symbol[T]) extends Predicate2[Symbol[S],Symbol[T]] {
	val item1: Symbol[S] = _item1
	val item2: Symbol[T] = _item2
	def test(values: Map[Symbol[_],_]): Boolean = true
}

trait PredicateServiceEntry

class PredicateServiceEntry1[T](_implementation: ExternalService[T,Boolean], _item: Symbol[T])
extends PredicateService1[T](_item) with ExternalServiceReference[T,Boolean] {
	val implementation: ExternalService[T,Boolean] = _implementation
	override def test(values: Map[Symbol[_],_]): Boolean = implementation.call(values(item).asInstanceOf[T])
}
class PredicateEntry1[T](implementation: ExternalService[T,Boolean]) {
	def apply(item: Symbol[T]): PredicateServiceEntry1[T] = new PredicateServiceEntry1[T](implementation, item)
}

class PredicateServiceEntry2[S,T](_implementation: ExternalService[Tuple2[S,T],Boolean], _item1: Symbol[S], _item2: Symbol[T])
extends PredicateService2[S,T](_item1, _item2) with ExternalServiceReference[Tuple2[S,T],Boolean] {
	val implementation: ExternalService[Tuple2[S,T],Boolean] = _implementation
	override def test(values: Map[Symbol[_],_]): Boolean = implementation.call((values(item1).asInstanceOf[S],values(item2).asInstanceOf[T]))	
}
class PredicateEntry2[S,T](implementation: ExternalService[Tuple2[S,T],Boolean]) {
	def apply(item1: Symbol[S], item2: Symbol[T]): PredicateServiceEntry2[S,T] = new PredicateServiceEntry2[S,T](implementation, item1, item2)
}