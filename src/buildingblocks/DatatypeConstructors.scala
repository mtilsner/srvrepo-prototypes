package buildingblocks

trait Datatype

trait BasicDatatype extends Datatype
object BasicDatatype {
	def apply(name: String, superType: Datatype, properties: Map[String,Datatype]): BasicDatatype = {
		// to do
		return null 
	}
}

trait ListDatatype extends Datatype
object ListDatatype {
	def apply(itemType: Datatype): ListDatatype = {
		// to do
		return null
	}
}

trait MapDatatype extends Datatype
object MapDatatype {
	def apply(keyType: Datatype, valueType: Datatype): MapDatatype = {
		// to do
		return null
	}
}

trait TupleDatatype extends Datatype
object TupleDatatype {
	def apply(typeA: Datatype, typeB: Datatype): TupleDatatype = {
		// to do
		return null
	}
}