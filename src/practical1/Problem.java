/*
    @author Roberta Buzatu - s1020137
    @author Bart van der Heijden - s1017343
 */

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

    /**
     * Adds an edge between the two nodes to the graph.
     * @param node1
     * @param node2
     */
    public void addEdge(int node1, int node2)
    {
        this.graph.addEdge(node1, node2);
    }

    /**
     * Prints out the answer given in the list.
     * @param nodes
     */
    public void printSolution(List<Integer> nodes)
    {
        nodes.sort(Integer::compareTo);

        String answer = "answer";
        for(int node : nodes)
        {
            answer += ' ';
            answer += String.valueOf(node);
        }

        System.out.println(answer);
    }

    /**
     * Solves the problem.
     * @param scanner
     * @return the list of infected nodes.
     */
    public List<Integer> solve(Scanner scanner)
    {
        List<Integer> answer = new ArrayList<>();

        if(edges == 0)
        {
            List<Integer> nodes = new ArrayList<>();
            for (int i = 0; i < this.nodes; i++)
                nodes.add(i);
            testWithinClique(nodes, scanner, answer);
        }
        else
        {
            graph.findCliques();
            int clique = 0;
            while(clique < graph.getCliques().size() && answer.size()<=upperBound)
            {
                boolean test = testClique(graph.getCliques().get(clique), scanner);
                if (test)
                    testWithinClique(graph.getCliques().get(clique), scanner, answer);
                clique++;
            }
        }

        return answer;
    }

    /**
     * Performs the testing for a given clique, either in group or individually.
     * @param clique
     * @param scanner
     * @param answer
     */
    private void testWithinClique(List<Integer> clique, Scanner scanner, List<Integer> answer)
    {
        if(answer.size() >= upperBound)
            return;

        if (clique.size() > 10 && this.infectionChance <= 0.3)
        {
            List<Integer> clique1 = clique.subList(0, clique.size()/2);
            List<Integer> clique2 = clique.subList(clique.size()/2, clique.size());

            if (testClique(clique1, scanner))
                testWithinClique(clique.subList(0, clique.size()/2), scanner, answer);
            if (testClique(clique2, scanner))
                testWithinClique(clique.subList(clique.size()/2, clique.size()), scanner, answer);
        }
        else
            {
                List<Integer> person = new ArrayList<>();
                for(int node : clique) {
                    person.add(node);
                    if (testClique(person, scanner) && !answer.contains(node))
                        answer.add(node);
                    person.remove(0);
            }
        }
    }


    /**
     * Prints the test command for a given clique.
     * @param clique
     * @param scanner
     * @return whether the clique was positive or not.
     */
    private boolean testClique(List<Integer> clique, Scanner scanner)
    {
        String test = "test";
        for (int node : clique)
        {
            test += ' ';
            test += String.valueOf(node);
        }

        System.out.println(test);

        return scanner.next().equals("true");
    }

}
