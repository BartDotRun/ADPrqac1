package practical1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem {
    public int nodes;
    public int edges;
    public int initInfected;
    public double infectionChance;
    public int lowerBound;
    public int upperBound;
    public Graph graph;


    public Problem(int nodes, int edges, int initInfected, double infectionChance, int lowerBound, int upperBound) {

        this.nodes = nodes;
        this.edges = edges;
        graph = new Graph(nodes, edges);

        this.initInfected = initInfected;
        this.infectionChance = infectionChance;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public void addEdge(int node1, int node2)
    {
        this.graph.addEdge(node1, node2);
    }

    public void printSolution(List<Integer> nodes)
    {
        String answer = "answer";
        for(int node : nodes)
            answer += ' ' + node;

        System.out.println(answer);
    }

    public List<Integer> solve(Scanner scanner)
    {
        List<Integer> answer = new ArrayList<>();
        graph.findCliques();
        int clique = 0;
        while(clique < graph.getCliques().size() && answer.size()<upperBound)
        {
            boolean test = testClique(graph.getCliques().get(clique), scanner);
            if (test)
                testWithinClique(graph.getCliques().get(clique), scanner, answer);
            clique++;
        }

        return answer;
    }

    private void testWithinClique(List<Integer> clique, Scanner scanner, List<Integer> answer)
    {

    }

    private boolean testClique(List<Integer> clique, Scanner scanner)
    {
        String test = "test";
        for (int node : clique)
            test += ' ' + node;

        System.out.println(test);

        return scanner.nextLine().equals("true");
    }

    public String showContents() {
        return "nodes: " + String.valueOf(nodes) + '\n' + "edges: " + String.valueOf(edges) + '\n' + "initInfected: " +
                String.valueOf(initInfected) + '\n' + "infectionChance: " + String.valueOf(infectionChance) + '\n' +
                "lowerBound: " + String.valueOf(lowerBound) + '\n' + "upperBound: " + String.valueOf(upperBound) + '\n'
                + "adjacencyMatrix: " + '\n' + graph.showContents() + '\n';
    }
}
