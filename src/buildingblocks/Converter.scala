package buildingblocks

trait Converter[From,To] {
	def call(i: From): To
}
