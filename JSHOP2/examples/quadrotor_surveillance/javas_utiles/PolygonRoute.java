import JSHOP2.*;
import java.io.IOException;

public class PolygonRoute implements Calculate {
  public Term call(List l)
  {
		TermNumber location = (TermNumber) l.getHead();
		TermNumber result = null;
    int locationId = (int) location.getNumber();
		
		try{
		Runtime rt = Runtime.getRuntime();
		Process ps = rt.exec("/home/jorge/Escritorio/SystemPlanner/zig_zag/zig_zag" + " " + locationId);
		ps.waitFor();
		int res = ps.exitValue();
		result = new TermNumber((double) res);

		} catch(IOException exception) {
			exception.printStackTrace();
		
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		
		}
		

		return result;

  }

}
