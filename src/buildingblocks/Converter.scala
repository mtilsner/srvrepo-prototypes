package buildingblocks

trait Converter[From,To] {
	def apply(i: From): To
}
