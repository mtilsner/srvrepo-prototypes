package buildingblocks

class ServiceEntry[Input,Output](_implementation: ExternalService[Input,Output], _pre: (Symbol[Input] => Predicate), _post: ((Symbol[Input],Symbol[Output]) => Predicate)) 
extends ExternalServiceReference[Input,Output] {
	val input: Symbol[Input] = new Symbol[Input]
	val output: Symbol[Output] = new Symbol[Output]

	val pre: Predicate = _pre(input)
	val post: Predicate = _post(input,output)
	val implementation: ExternalService[Input,Output] = _implementation

	def call(input: Input): Output = implementation.call(input)
}