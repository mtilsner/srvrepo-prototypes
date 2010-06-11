package buildingblocks

trait Service {
	type Input
	type Output

	def inputReference: Input
	def outputReference: Output

	def pre: Predicate
	def post: Predicate

	def call(input: Input): Output
}