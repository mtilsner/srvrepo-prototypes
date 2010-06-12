package buildingblocks

class Airport(_name: String, _city: City) {
	val name: String = _name
	val city: City = _city
}

class City(_name: String, _location: String, _airports: List[Airport]) {
	val name: String = _name
	val location: String = _location
	val airports: List[Airport] = _airports
}

class Flight(_origin: Airport, _destination: Airport, _airline: String) {
	val origin: Airport = _origin
	val destination: Airport = _destination
	val airline: String = _airline
}