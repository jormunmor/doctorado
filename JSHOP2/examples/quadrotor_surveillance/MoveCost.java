import JSHOP2.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

// This function calculates the length of the path existing between the two given locations. It invokes the client developed
// to communicate with V-REP, wich has a built-in function for that. For the moment, the UAV that has to do the operation
// is irrelevant, we only want to know if there is a path between the two points. For that reason, the name of the UAV
// is hardcoded. It is necessary that a UAV with this name and two locations with these names are present in the V-REP simulation.
public class MoveCost implements Calculate {
  public Term call(List l)
  {

		TermNumber fromLoc = (TermNumber) l.getHead();
	  l = l.getRest();
		TermNumber toLoc = (TermNumber) l.getHead();
		int fromLocation = (int) fromLoc.getNumber();
		int toLocation = (int) toLoc.getNumber();
		TermNumber result = null;

		String uavName = "Quadricopter#0";
		String fromLocationName = "loc" + fromLocation;
		String toLocationName = "loc" + toLocation;

		try{

			Runtime rt = Runtime.getRuntime();
			String command = "/home/jorge/Escritorio/SystemPlanner/bin/vrep_client/VRepClient pathplanning " + uavName + " " + fromLocationName + " " + toLocationName;
			Process ps = rt.exec(command);
			ps.waitFor();
			int res = ps.exitValue();
			if(res == 255){
				res = -1;
				System.out.println("Possible loss of data!!");

			}
			
			result = new TermNumber((double) res);

		} catch(IOException exception) {
			exception.printStackTrace();
		
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		
		}

		System.out.println("Resultado: " + result.getNumber());
		
		return result;

  }

}
