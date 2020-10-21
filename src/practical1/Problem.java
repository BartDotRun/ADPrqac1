package practical1;

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

    public void solve()
    {
        graph.findCliques();

    }

    public String showContents() {
        return "nodes: " + String.valueOf(nodes) + '\n' + "edges: " + String.valueOf(edges) + '\n' + "initInfected: " +
                String.valueOf(initInfected) + '\n' + "infectionChance: " + String.valueOf(infectionChance) + '\n' +
                "lowerBound: " + String.valueOf(lowerBound) + '\n' + "upperBound: " + String.valueOf(upperBound) + '\n'
                + "adjacencyMatrix: " + '\n' + graph.showContents() + '\n';
    }
}
