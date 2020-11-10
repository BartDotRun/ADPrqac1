/*
    @author Roberta Buzatu - s1020137
    @author Bart van der Heijden - s1017343
 */

package practical1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    public int nodes;
    private int edges;
    public int[][] adjacency;
    private List<List<Integer>> cliques = new ArrayList<>();

    public Graph(int nodes, int edges)
    {
        this.nodes = nodes;
        this.edges = edges;
        this.adjacency = new int[nodes][nodes];
    }

    /**
     * Adds an edge between the two nodes in the adjacency matrix.
     * @param node1
     * @param node2
     */
    public void addEdge(int node1, int node2)
    {
        this.adjacency[node1][node2] = 1;
        this.adjacency[node2][node1] = 1;
    }

    /**
     * A getter for the list of cliques.
     * @return the list of cliques, which are themselves lists of integers.
     */
    public List<List<Integer>> getCliques()
    {
        return this.cliques;
    }

    /**
     * Computes the cliques in the given graph.
     */
    public void findCliques()
    {
        List<Integer> degrees = this.computeDegreeOfNodes();
        List<Integer> grouped = new ArrayList<>();

        while (grouped.size() < this.nodes) {
            int currentNode = findMin(degrees, grouped);
            List<Integer> currentGroup = new ArrayList<>();
            currentGroup.add(currentNode);
            grouped.add(currentNode);

            for (int i = 0; i < this.nodes; i++)
                if (this.adjacency[currentNode][i] == 1 && !grouped.contains(i))
                {
                    currentGroup.add(i);
                    grouped.add(i);
                }

            this.cliques.add(currentGroup);
        }

        eliminateSingleCliques();
    }

    /**
     * Finds the node with the smallest degree that has not yet been grouped.
     * @param degrees
     * @param grouped
     * @return the number of the node.
     */
    private int findMin(List<Integer> degrees, List<Integer> grouped)
    {
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

    /**
     * Merges the cliques with only one element into the smallest cliques that have a connection with them.
     */
    private void eliminateSingleCliques()
    {
        int i = 0;
        while (i < this.cliques.size()) {

            if (this.cliques.get(i).size() == 1) {
                int index = findSmallestClique(this.cliques.get(i).get(0));
                if (index >= 0)
                {
                    this.cliques.get(index).add(this.cliques.get(i).get(0));
                    this.cliques.remove(this.cliques.get(i));
                }
            } else
                i++;
        }
    }

    /**
     * Finds the smallest clique that has a connection with the given node.
     * @param node
     * @return the index of the smallest clique or -1 if none was found.
     */
    private int findSmallestClique(int node)
    {
        List<Integer> adjacentNodes = new ArrayList<>();
        for (int i = 0; i < this.nodes; i++)
            if (this.adjacency[node][i] == 1)
                adjacentNodes.add(i);
        ArrayList copy = new ArrayList(this.cliques);
        for (List<Integer> clique : this.cliques) {
            if (clique.size() == 1)
                copy.remove(clique);
            if (Collections.disjoint(clique, adjacentNodes))
                copy.remove(clique);
        }

        if (copy.size() > 0)
            return cliques.indexOf(smallestClique(copy));
        else return -1;
    }

    /**
     * Finds the clique with the lest amount of elements in the given list.
     * @param cliques
     * @return the smallest clique.
     */
    private List<Integer> smallestClique(List<List<Integer>> cliques)
    {
        int minimumSize = Integer.MAX_VALUE;
        int index = 0;
        for (List<Integer> clique : cliques)
            if (clique.size() < minimumSize) {
                index = cliques.indexOf(clique);
                minimumSize = clique.size();
            }

        return cliques.get(index);
    }

    /**
     * Computes the degree of all the nodes in the undirected graph.
     * @return a list with the degree for each node.
     */
    private List<Integer> computeDegreeOfNodes()
    {
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
