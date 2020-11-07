/*
    @author Roberta Buzatu - s1020137
    @author Bart van der Heijden - s1017343
 */

package practical1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    public int nodes; //# nodes the graph has.
    private int edges; //# edges the graph should have.
    public int[][] adjacency;
    private List<List<Integer>> cliques = new ArrayList<>();

    public Graph(int nodes, int edges) {

        this.nodes = nodes;
        this.edges = edges;
        this.adjacency = new int[nodes][nodes];

    }

    public void addEdge(int node1, int node2) {

        this.adjacency[node1][node2] = 1;
        this.adjacency[node2][node1] = 1;

    }

    public List<List<Integer>> getCliques() {
        return this.cliques;
    }

    public void findCliques() {
        List<Integer> degrees = this.computeDegreeOfNodes();
        List<Integer> grouped = new ArrayList<>();

        while (grouped.size() < this.nodes) {
            int currentNode = findMin(degrees, grouped);
            List<Integer> currentGroup = new ArrayList<>();
            currentGroup.add(currentNode);
            grouped.add(currentNode);

            for (int i = 0; i < this.nodes; i++)
                if (this.adjacency[currentNode][i] == 1 && !grouped.contains(i)) {
                    currentGroup.add(i);
                    grouped.add(i);
                }

            cliques.add(currentGroup);
        }

        eliminateSingleCliques(cliques);

    }

    private int findMin(List<Integer> degrees, List<Integer> grouped) {
        int index = -1;
        int minimum = Integer.MAX_VALUE;
        int i = 0;
        while (i < this.nodes) {
            if (!grouped.contains(i) && degrees.get(i) < minimum) {
                index = i;
                minimum = degrees.get(i);
            }
            i++;
        }
        return index;
    }

    private void eliminateSingleCliques(List<List<Integer>> cliques) {
        int i = 0;
        while (i < cliques.size()) {

            if (cliques.get(i).size() == 1) {
                int index = findSmallestClique(cliques, cliques.get(i).get(0));
                if (index >= 0)
                {
                    cliques.get(index).add(cliques.get(i).get(0));
                    cliques.remove(cliques.get(i));
                }
            } else
                i++;
        }
    }

    private int findSmallestClique(List<List<Integer>> cliques, int node) {
        List<Integer> adjacentNodes = new ArrayList<>();
        for (int i = 0; i < this.nodes; i++)
            if (this.adjacency[node][i] == 1)
                adjacentNodes.add(i);
        ArrayList copy = new ArrayList(cliques);
        for (List<Integer> clique : cliques) {
            if (clique.size() == 1)
                copy.remove(clique);
            if (Collections.disjoint(clique, adjacentNodes))
                copy.remove(clique);
        }

        if (copy.size() > 0)
            return cliques.indexOf(smallestClique(copy));
        else return -1;
    }

    private List<Integer> smallestClique(List<List<Integer>> cliques) {
        int minimumSize = Integer.MAX_VALUE;
        int index = 0;
        for (List<Integer> clique : cliques)
            if (clique.size() < minimumSize) {
                index = cliques.indexOf(clique);
                minimumSize = clique.size();
            }

        return cliques.get(index);
    }

    private List<Integer> computeDegreeOfNodes() {
        List<Integer> degrees = new ArrayList<>();
        for (int i = 0; i < this.nodes; i++) {
            int count = 0;
            for (int j = 0; j < this.nodes; j++)
                count += this.adjacency[i][j];

            degrees.add(count);
        }

        return degrees;
    }

}
