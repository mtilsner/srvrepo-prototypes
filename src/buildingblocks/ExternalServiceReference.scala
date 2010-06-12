package buildingblocks

trait ExternalServiceReference[Input, Output] {
	def implementation: ExternalService[Input,Output]
}