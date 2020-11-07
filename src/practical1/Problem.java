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

    public void addEdge(int node1, int node2)
    {
        this.graph.addEdge(node1, node2);
    }

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

    public List<Integer> solve(Scanner scanner)
    {
        List<Integer> answer = new ArrayList<>();
        graph.findCliques();
        int clique = 0;
        while(clique < graph.getCliques().size() && answer.size()<=upperBound)
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
        if(answer.size() >= upperBound)
            return;

        if (clique.size() > 10 || this.infectionChance <= 0.3)
        {
            testWithinClique(clique.subList(0, clique.size()/2), scanner, answer);
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
