import repository._
import matcher._ 

object Run extends Application{
	Console.println(ServiceReplacement(Repository.services("FlightFromCity")).implementation.getClass.getName)
}
