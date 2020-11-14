/*
    @author Roberta Buzatu - s1020137
    @author Bart van der Heijden - s1017343
 */

package practical1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int numberOfProblems = input.nextInt();

        for (int i = 0; i < numberOfProblems; i++)
        {
            Problem problem = readProblems(input);
            problem.printSolution(problem.solve(input));
            float proportion = (float)problem.numberOfTests / (float)problem.nodes;
            System.err.println("[INFO] Problem " + (i+1) + " was solved using " +
                    problem.numberOfTests + " tests. (proportion of: " + proportion +
                    " in relation to " + problem.nodes + " total nodes)");
            input.next();
            input.nextLine();
        }
        input.close();
    }

    /**
     * Reads a new problem and returns the Problem object.
     * @param input
     * @return the problem
     */
    public static Problem readProblems(Scanner input)
    {
        Problem p = new Problem(input.nextInt(), input.nextInt(), input.nextInt(), input.nextDouble(), input.nextInt(), input.nextInt());

        for (int i = 0; i < p.edges; i++)
        {
            int node1 = input.nextInt();
            int node2 = input.nextInt();
            p.addEdge(node1, node2);

        }

        return p;
    }
}
