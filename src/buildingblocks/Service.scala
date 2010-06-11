package buildingblocks

trait Service[Input,Output] {
	val input = new Symbol
	val output = new Symbol

	def pre: Predicate
	def post: Predicate

	def call(input: Input): Output
}