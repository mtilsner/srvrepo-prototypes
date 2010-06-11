package buildingblocks

trait Predicate

trait Predicate1[T] extends Predicate {
	def call(input: T): Boolean
	
	def item: Symbol
	def test(values: Map[Symbol,_]): Boolean = {
		call(values(item).asInstanceOf[T])
	}
}

trait Predicate2[S,T] extends Predicate {
	def call(input: Tuple2[S,T]): Boolean
	
	def item1: Symbol
	def item2: Symbol
	def test(values: Map[Symbol,_]): Boolean = {
		call((values(item1).asInstanceOf[S], values(item2).asInstanceOf[T]))
	}
}