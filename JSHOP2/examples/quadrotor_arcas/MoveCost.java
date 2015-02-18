import JSHOP2.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

// This function calculates the length of the path existing between the two given locations. For simplicity, the locations
// are hardcoded and represent the x,y,z values for the Blender reference system on which the environment was designed.
public class MoveCost implements Calculate {
  public Term call(List l)
  {
	double[] x = {
		0, 6.700, 5.317, 7.300, 7.132, -7.400, 6.225, 5.750, 7.040, 6.170, 7.224, -6.700, 5.409, -8.200, 5.225, -8.000, 5.015, -6.700, 5.620, -8.000, 6.830, -6.700, 7.435, -7.2, 7.2, 0, 0

		};
	
	double[] y = {
		0, 6.930, -6.906, 6.930, -5.091, 7.000, -5.998, 6.930, -4.999, 6.930, -5.183, 6.930, -6.998, 6.930, -6.814, -5.800, -7.208, -5.800, -6.603, -7.300, -5.393, -7.300, -4.789, -1.5, -1.5, 7, -7 

		};

	double[] z = {
		0, 1.663, 0.165, 1.663, 0.165, 1.663, 0.819, 1.663, 0.819, 1.663, 0.819, 1.663, 0.819, 1.663, 0.819, 1.663, 0.492, 1.663, 0.492, 1.663, 0.492, 1.663, 0.492, 0.5, 0.5, 0.5, 0.5

		};

	TermNumber fromLoc = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber toLoc = (TermNumber) l.getHead();
	int fromLocation = (int) fromLoc.getNumber();
	int toLocation = (int) toLoc.getNumber();
	int result = fromLocation - toLocation;
	double xDifference = x[fromLocation] - x[toLocation];
  double yDifference = y[fromLocation] - y[toLocation];
  double zDifference = z[fromLocation] - z[toLocation];
  double distance = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference) + (zDifference * zDifference));
	
	return new TermNumber(distance);

  }

}
