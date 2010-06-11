package buildingblocks

trait Predicate[T] {
	def item: Symbol

	def call(input: T): Boolean
}
