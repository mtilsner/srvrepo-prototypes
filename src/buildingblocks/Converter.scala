package buildingblocks

trait Converter[From,To] {
	def pre: Predicate
	def post: Predicate

	def call(i: From): To
}
