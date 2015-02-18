import JSHOP2.TermNumber;
import JSHOP2.Term;
import JSHOP2.TermList;
import JSHOP2.Calculate;
import JSHOP2.DijkstraShortestPath;
import JSHOP2.Vertex;
import java.util.LinkedList;

public class RouteDuration implements Calculate {
  public Term call(JSHOP2.List l)
  {
		int cost = 0;
		JSHOP2.List termList = ((TermList) l.getHead()).getList(); // The list l containt a TermList as head and NIL as tail.
				
		int source = (int) ((TermNumber) termList.getHead()).getNumber();
		int destination;
    while (termList != null)	// We will have always at least two nodes
    {
      termList = termList.getRest();
			if(termList == null) {
				break;

			}
			destination = (int) ((TermNumber) termList.getHead()).getNumber();
			cost += DijkstraShortestPath.getCost(source, destination);
			source = destination;

    }
		
    return new TermNumber(cost);

	}

}	
