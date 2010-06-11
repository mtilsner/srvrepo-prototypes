package buildingblocks

trait Predicate {
	type Output = Boolean
}
trait Predicate1[T] extends Predicate {
	type Input = T
	def call(input: T): Boolean
	
	def item: T
	def test(values: Map[_,_]): Boolean = {
		call(values(item).asInstanceOf[T])
	}
}
trait Predicate2[S,T] extends Predicate {
	type Input = Tuple2[S,T]
	def call(input: Tuple2[S,T]): Boolean
	
	def item1: S
	def item2: T
	def test(values: Map[_,_]): Boolean = {
		call((values(item1).asInstanceOf[S], values(item2).asInstanceOf[T]))
	}
}