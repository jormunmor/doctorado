import JSHOP2.TermNumber;
import JSHOP2.Term;
import JSHOP2.TermList;
import JSHOP2.Calculate;
import JSHOP2.DijkstraShortestPath;
import JSHOP2.Vertex;
import java.util.LinkedList;

public class Path implements Calculate {
  public Term call(JSHOP2.List l)
  {
		// The list will have two TermNumbers, source vertex and destination vertex
		TermNumber source = (TermNumber) l.getHead();
    l = l.getRest();
		TermNumber destination = (TermNumber) l.getHead();
		java.util.List<Vertex> vertexJavaList = DijkstraShortestPath.main((int) source.getNumber(), (int) destination.getNumber());

		LinkedList list = new LinkedList<TermNumber>();
		for(int i=0; i<vertexJavaList.size(); i++){
			list.addFirst( new TermNumber( ( (Vertex) vertexJavaList.get(i) ).getNumber() ) );		
					
		}		

		JSHOP2.List vertexShopList = JSHOP2.List.MakeList(list);	
		TermList returnList = new TermList(vertexShopList);

		return returnList;

  }
}
