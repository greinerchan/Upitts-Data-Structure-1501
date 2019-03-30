import java.text.DecimalFormat;
import java.util.Scanner;

public class NetworkAnalysis {
    private static Scanner scanner = new Scanner(System.in);
    private static GraphManager directedGraph;
    public static void main(String[] args) {
        BuildGraph graph = new BuildGraph();
        if (args.length == 0) {
            throw new AssertionError("Please Enter the FileName");
        }
        String fileName = args[0];
        directedGraph = graph.buildGraph(fileName);
        System.out.println("The whole graph: ");
        directedGraph.printDiGraph();
        mainMenu();
    }

    private static void mainMenu() {
        String choose = null;
        do {
            StringBuilder sb = new StringBuilder();
            sb.append("        Menu \n").append("1. lowest latency path between any two points\n")
            .append("2. Determine whether or not the graph is copper-only connected \n")
            .append("3. Find the lowest average latency spanning tree for the graph \n")
            .append("4. Determine whether or not the graph would remain connected if any two vertices in the graph were to fail.\n")
            .append("0. Quit");
            System.out.println(sb);
            choose = scanner.nextLine();
            switch(choose) {
            case "1":
                minLatency();
                break;
            case "2":
                copperOnly();
                break;
            case "3":
                minTree();
                break;
            case "4":
                remainConnected();
                break;
            case "0":
                System.out.println("Program Exit!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice");
                break;
            }
        } while(!choose.equals("0"));
    }

    private static void remainConnected() {
        if (directedGraph.failTwoTest()) {
            System.out.println("Yes! The rest graph will remain connected if two vertices are failed");
        } else {
            System.out.println("No! The rest graph will not remain connected if two vertices are failed");
        }
        System.out.println("Press Any key to back menu");
        scanner.nextLine();
        if (true) {
            mainMenu();
        }
    }

    private static void minTree() {
        Iterable<DirectedEdge> edges = directedGraph.MST();
        int count = 0; double time = 0;
        System.out.println("lowest average latency spanning tree for the graph is: ");
        for (DirectedEdge e : edges) {
            System.out.println(e.from() + "-" + e.to());
            count++;
            time = time + e.timeCost();
        }
        double avg = time / count;
        DecimalFormat format =
                new DecimalFormat("#,###.000");
        String avgTime = format.format(avg);
        avg = Double.parseDouble(avgTime);
        System.out.println("The average latency is: " + avg + " nanoseconds");
        System.out.println("Press Any key to back menu");
        scanner.nextLine();
        if (true) {
            mainMenu();
        }
    }

    private static void copperOnly() {
        if (directedGraph.copperOnly()) {
            System.out.println("Yes! The graph can remain connected without fiber optic cables.");
        } else {
            System.out.println("No! The graph will not be connected without fiber optic cables.");
        }
        System.out.println("Press Any key to back menu");
        scanner.nextLine();
        if (true) {
            mainMenu();
        }
    }

    private static void minLatency() {
        String v,w,back;
        double minTime = 0;
        double minBandwidth = Integer.MAX_VALUE;
        int vv, ww;
        //graphs.printGraph();
        System.out.println("Please Enter the First Vertex");
        v = scanner.nextLine();
        System.out.println("Please Enter the Second Vertex");
        w = scanner.nextLine();
        System.out.println();
        if (!isInt(v) || !isInt(w)) {
            minLatency();
        }
        vv = Integer.parseInt(v);
        ww = Integer.parseInt(w);
        if (vv == ww) {
            System.out.println("start and end path should not be the same!");
            System.out.println("Press 0 Back to Menu, Press Any key to enter again");
            back = scanner.nextLine();
            if (back.equals("0")) {
                mainMenu();
            } else {
                minLatency();
            }
        }
        if (!directedGraph.hasPath(vv, ww)) {
            System.out.println("No Path Exist!");
            System.out.println("Press 0 Back to Menu, Press Any key to enter again");
            back = scanner.nextLine();
            if (back.equals("0")) {
                mainMenu();
            } else {
                minLatency();
            }
        } else {
            Iterable<DirectedEdge> path = directedGraph.minLatency(vv, ww);
            for (DirectedEdge e : path) {
                minTime = minTime + e.timeCost();
                if (e.bandwidth() < minBandwidth) {
                    minBandwidth = e.bandwidth();
                }
                System.out.println(e.toString());
            }
            DecimalFormat format =
                    new DecimalFormat("#,###.000");
            String time = format.format(minTime);
            minTime = Double.parseDouble(time);
            System.out.println("\nThe minimum latency is: " + minTime + " nanoseconds");
            System.out.println("the minimum bandwidth that is: " + minBandwidth + " megabits");
            System.out.println("Press 0 Back to Menu, Press Any key to enter again");
            back = scanner.nextLine();
            if (back.equals("0")) {
                mainMenu();
            } else {
                minLatency();
            }
        }
    }
    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException ex){
            System.out.println("Please Enter A Valid Number!\n");
        }
        return false;
    }

}
