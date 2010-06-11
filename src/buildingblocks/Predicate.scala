package buildingblocks

trait Predicate extends Service {
	type Output = Boolean
}
trait Predicate1[T] extends Predicate {
	type Input = T
	def apply(a: T): Boolean
}
trait Predicate2[S,T] extends Predicate {
	type Input = Tuple2[S,T]
	def apply(a: S, b: T): Boolean
}