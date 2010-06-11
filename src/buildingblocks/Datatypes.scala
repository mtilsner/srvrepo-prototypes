package buildingblocks

class Airport extends BasicDatatype {
	var name: String
	var city: City
}

class City extends BasicDatatype {
	var name: String
	var location: String
	var airports: List[Airport]
}

class Flight extends BasicDatatype {
	var origin: Airport
	var destination: Airport
	var airline: String
	var takeoff: Date
	var landing: Date
}