import JSHOP2.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/*
* This function receives a list of object names and returns the maximum write-time among all of them.
* The objets may be of different types. In the case of an empty list, -1 is returned.
*/

public class LastWriteTime implements Calculate {
  public Term call(List l)
  {
	double maxWriteTime = 0.0;	
	double candidateMaxWriteTime;
	TermList termList = (TermList) l.getHead();
	List list = termList.getList();
	// check if empty list
	if(list == null){
		//System.out.println("Empty list!!!");
		return new TermNumber(maxWriteTime);	
	
	}
	// create a map of objectNames->write-times values from all the predicates present in the current state
	ArrayList<String> state = JSHOP2.getState().getState();
	Map<String, Double> writeTimeMap = new HashMap<String, Double>();
	for(int i=0; i<state.size(); i++)
	{
		String predString = state.get(i);
		String predicate = predString.substring(1, predString.length()-1); // eliminate the parenthesis
		if(predicate.contains("write-time at ")){ // filter only 'write-time at' predicates
			String[] parts = predicate.split(" ");
			writeTimeMap.put(parts[2], Double.parseDouble(parts[3])); // parts[0] = write-time / parts[1] = at / parts[2] = objectName / parts[3] = time

		}		

	}
	// calculate the maximum value
	Term max = (Term) list.getHead();
	maxWriteTime = writeTimeMap.get(max.toString());
	//System.out.println(max.toString() + ": " + maxWriteTime);
	list = list.getRest();
	Term candidate;
	while (list != null)
	{
		candidate = (Term) list.getHead();
		candidateMaxWriteTime = writeTimeMap.get(candidate.toString());
		if(candidateMaxWriteTime > maxWriteTime){
			maxWriteTime = 	candidateMaxWriteTime;	

		}
		//System.out.println(candidate.toString() + ": " + maxWriteTime);
		list = list.getRest();

	}

	//System.out.println("Max: " + maxWriteTime);
	return new TermNumber(maxWriteTime);

  }

}
