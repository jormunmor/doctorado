package JSHOP2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class DijkstraShortestPath {
    
    private static List<Vertex> nodes;
  
    private static List<Edge> edges;

		private static Graph graph;

		static {
      nodes = new ArrayList<>();
      edges = new ArrayList<>();
      
			java.util.ArrayList<java.lang.String> stateList = JSHOP2.getState().getState();
			/*
				The getState() method only return predicates that are present in the domain definition. Any other predicate defined
				in the problem will not appear. To access them, they must be included in any of the methods or operators of
				the domain.
			*/
			for(int i=0;i<stateList.size();i++){
				String predicate = stateList.get(i);
				predicate = predicate.substring(1, predicate.length()-1);
				if(predicate.contains("location")) {
					String[] loc = predicate.split(" ");
					int number = (int) Float.parseFloat(loc[1]);
					Vertex vertex = new Vertex("Node_" + number, "Node_" + number, number);
					nodes.add(vertex);
				
				} else if(predicate.contains("path")){
					String[] path = predicate.split(" ");
					String id = path[1];
					int loc1 = (int) Float.parseFloat(path[2]);
					int loc2 = (int) Float.parseFloat(path[3]);
					int cost = (int) Float.parseFloat(path[4]);

					addLane("Edge_" + id, loc1, loc2, cost);

				}

				
			}      

			graph = new Graph(nodes, edges);

			
		}
    /**
     * @param args the command line arguments
     */
    public static List main(int sourceVertex, int destinationVertex) {
        
      // Lets check from location source to location destiny
      Vertex source = nodes.get(sourceVertex);
      Vertex destiny = nodes.get(destinationVertex);
      DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
      dijkstra.execute(source);
      LinkedList<Vertex> path = dijkstra.getPath(destiny);

      if(path == null){
          System.out.println("Error. Path is null. Aborting...");
          
      }
      
      if(path.size() <= 0){
          System.out.println("Error. Path size <= 0. Aborting...");
          
      }

			return path; 
        
    }
    
    private static void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
				Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
        edges.add(lane);
				
        
    }

		/*

			This method returns the distance between two adjacent nodes, or MAX_VALUE otherwise.

		*/
		public static int getCost(int source, int destination) {
		  int cost = Integer.MAX_VALUE; // set cost to positive infinity
		  for (Edge edge : edges) {
		    if (edge.getSource().getNumber() == source && edge.getDestination().getNumber() == destination ||
						edge.getSource().getNumber() == destination && edge.getDestination().getNumber() == source) {
		      //return edge.getWeight();
		      if(edge.getWeight() < cost){
		          cost = edge.getWeight();
		      }
		    }
		  }
		  return cost;

  	}
    
}
