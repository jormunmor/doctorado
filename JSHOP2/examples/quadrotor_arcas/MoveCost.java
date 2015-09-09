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
	TermNumber x1N = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber y1N = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber z1N = (TermNumber) l.getHead();
	l = l.getRest();

	TermNumber x2N = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber y2N = (TermNumber) l.getHead();
	l = l.getRest();
	TermNumber z2N = (TermNumber) l.getHead();

	double x1 = (double) x1N.getNumber();
	double y1 = (double) y1N.getNumber();
	double z1 = (double) z1N.getNumber();

	double x2 = (double) x2N.getNumber();
	double y2 = (double) y2N.getNumber();
	double z2 = (double) z2N.getNumber();

	//System.out.prdoubleln("From " + x1 + " " + y1 + " " + z1);
	//System.out.prdoubleln("To " + x2 + " " + y2 + " " + z2);

	double xDifference = x1 - x2;
	double yDifference = y1 - y2;
	double zDifference = z1 - z2;
	double distance = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference) + (zDifference * zDifference));
	//System.out.prdoubleln("Result " + distance);
	
	return new TermNumber(distance);

  }

}
