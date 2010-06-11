package buildingblocks

class Airport(name: String, city: City) extends BasicDatatype

class City(name: String, location: String, airports: List[Airport]) extends BasicDatatype

class Flight(origin: Airport, destination: Airport, airline: String) extends BasicDatatype