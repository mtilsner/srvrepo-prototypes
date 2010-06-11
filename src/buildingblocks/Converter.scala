package buildingblocks

trait Converter[From,To] extends Service {
	def apply(i: From): To
}
