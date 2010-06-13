import repository._

object Run extends Application{
	val replacement = ServiceReplacement(Repository.services("FlightFromCity"))
	if(replacement != null)
		Console.println("Replacement found: " + replacement.implementation.getClass.getName)
	else
		Console.println("No replacement found!")
}
