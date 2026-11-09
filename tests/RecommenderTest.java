public class RecommenderTest {
    private static int checks;
    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    private static void testQueues() {
        java.util.Random random = new java.util.Random(99);
        for (int k = 0; k <= 8; k++) {
            for (int trial = 0; trial < 20; trial++) {
                ArrayPQK<Integer, Integer> queue = new ArrayPQK<>(k);
                final int[] priorities = new int[30];
                java.util.List<Integer> expected = new java.util.ArrayList<>();
                for (int i = 0; i < priorities.length; i++) {
                    priorities[i] = random.nextInt(5); expected.add(i); queue.enqueue(priorities[i], i);
                }
                expected.sort((a,b) -> priorities[a] == priorities[b] ? Integer.compare(a,b) : Integer.compare(priorities[b],priorities[a]));
                check(queue.length() == k, "Queue capacity");
                for (int i = 0; i < k; i++) {
                    Pair<Integer,Integer> item = queue.serve();
                    check(item != null && item.second.equals(expected.get(i)), "Stable top-k order");
                }
                check(queue.serve() == null, "Empty queue");
            }
        }
    }
    private static void testGraphs() {
        java.util.Random random = new java.util.Random(73);
        for (int trial = 0; trial < 25; trial++) {
            final int n = 9;
            final boolean[][] edges = new boolean[n][n];
            final int[] degrees = new int[n];
            Graph<Integer> graph = new MGraph<>();
            for (int i = 0; i < n; i++) graph.addNode(1000+i);
            for (int i = 0; i < n; i++) for (int j = i+1; j < n; j++) {
                if (random.nextInt(4) == 0) {
                    graph.addEdge(1000+i, 1000+j); edges[i][j] = edges[j][i] = true; degrees[i]++; degrees[j]++;
                }
            }
            for (int i = 0; i < n; i++) {
                check(graph.neighb(1000+i) != null, "Isolated adjacency set");
                check(graph.deg(1000+i) == degrees[i], "Degree");
                for (int method = 0; method < 3; method++) {
                    final double[] scores = new double[n];
                    java.util.List<Integer> expected = new java.util.ArrayList<>();
                    for (int j = 0; j < n; j++) if (j != i && !edges[i][j]) {
                        expected.add(j);
                        if (method == 0) scores[j] = degrees[j];
                        else for (int x = 0; x < n; x++) if (edges[i][x] && edges[j][x]) scores[j] += method == 1 ? 1 : 1.0/degrees[x];
                    }
                    expected.sort((a,b) -> Double.compare(scores[b], scores[a]) == 0 ? Integer.compare(a,b) : Double.compare(scores[b], scores[a]));
                    int k = trial % 6;
                    PQK<Double,Integer> actual = method == 0 ? Recommender.recommendDeg(graph,Integer.valueOf(1000+i),k) : method == 1 ? Recommender.recommendCN(graph,Integer.valueOf(1000+i),k) : Recommender.recommendWCN(graph,Integer.valueOf(1000+i),k);
                    check(actual.length() == Math.min(k,expected.size()), "Recommendation count");
                    for (int rank = 0; rank < Math.min(k,expected.size()); rank++) {
                        Pair<Double,Integer> pair = actual.serve();
                        check(pair.second == 1000+expected.get(rank), "Recommendation ordering/self exclusion");
                        check(Math.abs(pair.first-scores[expected.get(rank)]) < 1e-10, "Recommendation score");
                    }
                }
            }
            check(Recommender.recommendCN(graph,-1,3) == null, "Unknown user");
        }
    }
    public static void main(String[] args) {
        testQueues(); testGraphs();
        System.out.println(checks + " graph and priority-queue checks passed.");
    }
}
