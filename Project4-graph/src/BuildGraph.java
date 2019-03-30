import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BuildGraph {
   public GraphManager buildGraph(String fileName) {
       Scanner scanner = null;
       GraphManager graphs = null;
       String numVertices = null;
       try {
           scanner = new Scanner(new File(fileName));
           if (scanner.hasNextLine()) {
               numVertices = scanner.nextLine();
           } else {
               throw new AssertionError("cannot get vertices number");
           }
           graphs = new GraphManager(Integer.parseInt(numVertices));
           while (scanner.hasNextLine()) {
               String parameters = scanner.nextLine();
               if(parameters.startsWith("#")) {
                   continue;
               }
               String[] parameter = parameters.split(Pattern.quote(" "));
               int v = Integer.parseInt(parameter[0]);
               int w = Integer.parseInt(parameter[1]);
               String cable = parameter[2];
               double bandwidth = Double.valueOf(parameter[3]);
               double weight =  Double.valueOf(parameter[4]);
               //Edge edge = new Edge(v, w, cable, bandwidth, weight);
               DirectedEdge rightEdge = new DirectedEdge(v, w, cable, bandwidth, weight);
               DirectedEdge leftEdge = new DirectedEdge(w, v, cable, bandwidth, weight);
               //graphs.addEdge(edge);
               graphs.addDirectedEdge(rightEdge);
               graphs.addDirectedEdge(leftEdge);
           }
       } catch (FileNotFoundException e) {
           System.err.println("Cannot find the file");
       } finally {
           if (scanner != null) {
               scanner.close();
           }
       }
       return graphs;
   }
}
