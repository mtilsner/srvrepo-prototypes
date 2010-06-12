package repository

import buildingblocks._
import domain._

object Repository {
	// Predicates
	val ValidCity = new PredicateEntry1[City](new domain.IsValidCity) 
	val CityWithAirport = new PredicateEntry1[City](new domain.IsCityWithAirport)
	val ValidFlight = new PredicateEntry1[Flight](new domain.IsValidFlight)
	val FlightFromCity = new PredicateEntry2[City,Flight](new domain.IsFlightFromCity)
	val ValidAirport = new PredicateEntry1[Airport](new domain.IsValidAirport)
	val AirportInCity = new PredicateEntry2[City,Airport](new domain.IsAirportInCity)
	val FlightFromAirport = new PredicateEntry2[Airport,Flight](new domain.IsFlightFromAirport)
	val Equals = new PredicateEntry2[Any,Any](new domain.IsEquals)
	
	// Services
	val services = Map[String,ServiceEntry[_,_]](
		"FlightFromAirport" 	->	new ServiceEntry[Airport,Flight] (
											new domain.FindFlightFromAirport,
											(input: Symbol[Airport]) => ValidAirport(input),
											(input: Symbol[Airport], output: Symbol[Flight]) => And(ValidFlight(output), FlightFromAirport(input,output))
									),
		"FlightFromCity"		->	new ServiceEntry[City,Flight] (
											new domain.FindFlightFromCity,
											(input: Symbol[City]) => And(ValidCity(input), CityWithAirport(input)),
											(input: Symbol[City], output: Symbol[Flight]) => And(ValidFlight(output), FlightFromCity(input,output))
									),
		"ConvertCityToAirport"	->	new ServiceEntry[City,Airport] (
											new domain.ConvertCityToAirport,
											(input: Symbol[City]) => And(ValidCity(input), CityWithAirport(input)),
											(input: Symbol[City], output: Symbol[Airport]) => And(ValidAirport(output), AirportInCity(input,output))
									),
		"ConvertFlightToFlight"	->	new	ServiceEntry[Flight,Flight] (
											new domain.ConvertFlightToFlight,
											(input: Symbol[Flight]) => ValidFlight(input),
											(input: Symbol[Flight], output: Symbol[Flight]) => And(ValidFlight(output), Equals(input, output))
									)
	)
	
	// Converters
	val converters = Map[Tuple2[Class[_],Class[_]],ServiceEntry[_,_]](
		(classOf[City],classOf[Airport])	->	services("ConvertCityToAirport"),
		(classOf[Flight],classOf[Flight])	->	services("ConvertFlightToFlight")
	)
}