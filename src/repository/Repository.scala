package repository

import buildingblocks._
import domain._

object Repository {
	// Predicates
	val ValidCity = new PredicateEntry1[City](new external.IsValidCity) 
	val CityWithAirport = new PredicateEntry1[City](new external.IsCityWithAirport)
	val ValidFlight = new PredicateEntry1[Flight](new external.IsValidFlight)
	val FlightFromCity = new PredicateEntry2[City,Flight](new external.IsFlightFromCity)
	val ValidAirport = new PredicateEntry1[Airport](new external.IsValidAirport)
	val AirportInCity = new PredicateEntry2[City,Airport](new external.IsAirportInCity)
	val FlightFromAirport = new PredicateEntry2[Airport,Flight](new external.IsFlightFromAirport)
	val Equals = new PredicateEntry2[Any,Any](new external.IsEquals)
	
	// Services
	val services = Map[String,ServiceEntry[_,_]](
		"FlightFromAirport" 	->	new ServiceEntry[Airport,Flight] (
											new external.FindFlightFromAirport,
											(input: Symbol[Airport]) => ValidAirport(input),
											(input: Symbol[Airport], output: Symbol[Flight]) => And(ValidFlight(output), FlightFromAirport(input,output))
									),
		"FlightFromCity"		->	new ServiceEntry[City,Flight] (
											new external.FindFlightFromCity,
											(input: Symbol[City]) => And(ValidCity(input), CityWithAirport(input)),
											(input: Symbol[City], output: Symbol[Flight]) => And(ValidFlight(output), FlightFromCity(input,output))
									),
		"ConvertCityToAirport"	->	new ServiceEntry[City,Airport] (
											new external.ConvertCityToAirport,
											(input: Symbol[City]) => And(ValidCity(input), CityWithAirport(input)),
											(input: Symbol[City], output: Symbol[Airport]) => And(ValidAirport(output), AirportInCity(input,output))
									),
		"ConvertFlightToFlight"	->	new	ServiceEntry[Flight,Flight] (
											new external.ConvertFlightToFlight,
											(input: Symbol[Flight]) => ValidFlight(input),
											(input: Symbol[Flight], output: Symbol[Flight]) => And(ValidFlight(output), Equals(input, output))
									)
	)
	
	// Converters
	val converters = Map[Tuple2[Class[_],Class[_]],List[ServiceEntry[_,_]]](
		(classOf[City],classOf[Airport])	->	List(services("ConvertCityToAirport")),
		(classOf[Flight],classOf[Flight])	->	List(services("ConvertFlightToFlight"))
	)
}