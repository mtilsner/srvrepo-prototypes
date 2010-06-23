package domain

import buildingblocks._

// Services
class FindFlightFromCity extends ExternalService[City,Flight] {
	def call(input: City): Flight = new Flight(input.airports(0), null, "LH")
}

class FindFlightFromAirport extends ExternalService[Airport,Flight] {
	def call(input: Airport): Flight = new Flight(input, null, "LH")
}

// Converters
class ConvertCityToAirport extends ExternalService[City,Airport] {
	def call(input: City): Airport = input.airports(0)
}

class ConvertFlightToFlight extends ExternalService[Flight,Flight] {
	def call(input: Flight): Flight = input
}

// Predicates
class IsValidCity extends ExternalService[City,Boolean] {
	def call(input: City): Boolean = true
}

class IsCityWithAirport extends ExternalService[City,Boolean] {
  def call(input: City): Boolean = true
}

class IsValidFlight extends ExternalService[Flight,Boolean] {
  def call(input: Flight): Boolean = true
}

class IsFlightFromCity extends ExternalService[Tuple2[City,Flight],Boolean] {
  def call(input: Tuple2[City,Flight]): Boolean = (input._1 == input._2.origin.city)
}

class IsValidAirport extends ExternalService[Airport,Boolean] {
  def call(input: Airport): Boolean = true
}

class IsAirportInCity extends ExternalService[Tuple2[City,Airport],Boolean] {
  def call(input: Tuple2[City,Airport]): Boolean = (input._1 == input._2.city && input._1.airports.contains(input._2))
}

class IsFlightFromAirport extends ExternalService[Tuple2[Airport,Flight],Boolean] {
  def call(input: Tuple2[Airport,Flight]): Boolean = (input._1 == input._2.origin)
}

class IsEquals extends ExternalService[Tuple2[_,_],Boolean] {
  def call(input: Tuple2[_,_]): Boolean = (input._1 == input._2)
}