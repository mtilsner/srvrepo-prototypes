package buildingblocks

trait ExternalService[Input, Output] {
	def call(input: Input): Output
}