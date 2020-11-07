/*
    @author Roberta Buzatu - s1020137
    @author Bart van der Heijden - s1017343
 */

package practical1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {

    public static OutputStreamWriter writer;
    public static void main(String[] args) throws IOException {

        try {
            writer = new OutputStreamWriter(new FileOutputStream("systemLog.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        int numberOfProblems = input.nextInt();

        for (int i = 0; i < numberOfProblems; i++)
        {
            Problem problem = readProblems(input);
            problem.printSolution(problem.solve(input));
            input.next();
        }
        input.close();

        writer.close();
    }

    public static Problem readProblems(Scanner input) throws IOException {
        Problem p = new Problem(input.nextInt(), input.nextInt(), input.nextInt(), input.nextDouble(), input.nextInt(), input.nextInt());


        int counter = 0;
        for (int i = 0; i < p.edges; i++){
            int node1 = input.nextInt();
            int node2 = input.nextInt();
            p.addEdge(node1, node2);
            counter++;
        }

        writer.write("Number of edges read: "+ counter + '\n');
        return p;
    }
}
