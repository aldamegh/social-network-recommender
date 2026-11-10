/** Deterministic example with fictional integer node IDs, added for publication. */
public class FriendRecommendationDemo {
    public static void main(String[] args) {
        Graph<Integer> graph = new MGraph<Integer>();
        for (int id = 1; id <= 6; id++) graph.addNode(id);
        int[][] edges = {{1, 2}, {1, 3}, {2, 4}, {3, 4}, {3, 5}, {4, 6}};
        System.out.println("Synthetic undirected graph");
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
            System.out.println("  " + edge[0] + " -- " + edge[1]);
        }
        System.out.println("\nCommon-neighbor recommendations for node 1");
        PQK<Double, Integer> recommendations = Recommender.recommendCN(graph, 1, 3);
        while (recommendations.length() > 0) {
            Pair<Double, Integer> item = recommendations.serve();
            System.out.println("  Node " + item.second + ": " + item.first.intValue() + " common neighbors");
        }
        System.out.println("\nExisting friends (2, 3) and node 1 are excluded.");
    }
}
