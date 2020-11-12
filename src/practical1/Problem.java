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
    public int optimalPoolSize;
    public int numberOfTests;


    public Problem(int nodes, int edges, int initInfected, double infectionChance, int lowerBound, int upperBound) {

        this.nodes = nodes;
        this.edges = edges;
        graph = new Graph(nodes, edges);

        this.initInfected = initInfected;
        this.infectionChance = infectionChance;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.numberOfTests = 0;
        optimalPoolSize = setOptimalPoolSize();
        System.err.println("[INFO] Pool size set to: " + optimalPoolSize + " based on an infection rate of: " + infectionChance);
    }

    /**
     * Set the optimal pool size, based on the infection rate, according to: https://blogs.sas.com/content/iml/2020/07/06/pool-testing-covid19.html
     * @return optimal pool size.
     */
    private int setOptimalPoolSize() {
        if (this.edges == 0) {
            float ratio = (float) upperBound / (float) nodes;
            float value = ratio * (float)500;
            return 7;
        }
        if (this.infectionChance >= 0.125) {
            return 3;
        } else if (this.infectionChance >= 0.075) {
            return 4;
        } else if(this.infectionChance >= 0.0375) {
            return 5;
        } else if(this.infectionChance >= 0.0175) {
            return 7;
        }
        return 11;
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
            List<Integer> clique = new ArrayList<>();
            for (int i = 0; i < this.nodes; i++)
                clique.add(i);
            testWithinClique(clique, scanner, answer);
        }
        else
        {
            graph.findCliques();
            int clique = 0;
            while(clique < graph.getCliques().size() && answer.size()<=upperBound)
            {
                boolean test = testClique(graph.getCliques().get(clique), scanner, answer);
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

        if (clique.size() > optimalPoolSize)
        {
            List<Integer> clique1 = clique.subList(0, clique.size()/2);
            List<Integer> clique2 = clique.subList(clique.size()/2, clique.size());

            if (testClique(clique1, scanner, answer))
                testWithinClique(clique.subList(0, clique.size()/2), scanner, answer);
            if (testClique(clique2, scanner, answer))
                testWithinClique(clique.subList(clique.size()/2, clique.size()), scanner, answer);
        }
        else //if (testClique(clique, scanner, answer))
            {
                List<Integer> person = new ArrayList<>();
                for(int node : clique) {
                    person.add(node);
                    if (testClique(person, scanner, answer) && !answer.contains(node))
                        answer.add(node);
                    person.remove(0);
            }
        }
    }


    /**
     * Prints the test command for a given clique. And returns the test result as boolean.
     * @param clique
     * @param scanner
     * @return whether the clique was positive or not.
     */
    private boolean testClique(List<Integer> clique, Scanner scanner, List<Integer> answer)
    {
        if (answer.size() >= upperBound) {
            return false;
        }
        String test = "test";
        for (int node : clique)
        {
            test += ' ';
            test += String.valueOf(node);
        }

        System.out.println(test);
        numberOfTests++;
        return scanner.next().equals("true");
    }

}
