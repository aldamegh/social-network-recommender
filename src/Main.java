import java.util.Random;

public class Main {

	private static void testPQK() {
		System.out.println("-------------------");
		PQK<Integer, String> pq = new ArrayPQK<Integer, String>(3);
		pq.enqueue(5, "A");
		pq.enqueue(3, "B");
		pq.enqueue(2, "C");
		pq.enqueue(1, "D");
		pq.enqueue(2, "E");
		pq.enqueue(4, "F");
		pq.enqueue(6, "G");
		pq.enqueue(7, "H");
		pq.enqueue(8, "I");
		pq.enqueue(8, "J");
		pq.enqueue(7, "K");
		pq.enqueue(6, "L");

		while (pq.length() > 0) {
			Pair<Integer, String> p = pq.serve();
			System.out.println(p.first + "\t" + p.second);
		}
		System.out.println("-------------------");
	}

	private static void testMap() {
		System.out.println("-------------------");
		Map<Integer, Integer> m = new BSTMap<Integer, Integer>();
		for (int i = 100; i > 0; i--) {
			m.insert(i % 13, i);
		}
		{
			Iterator<Pair<Integer, Integer>> it = m.minIt();
			while (it.isValid()) {
				Pair<Integer, Integer> p = it.next();
				System.out.println(p.first + "\t" + p.second);
			}
		}
		System.out.println("----------");
		{
			Iterator<Pair<Integer, Integer>> it = m.maxIt();
			while (it.isValid()) {
				Pair<Integer, Integer> p = it.prev();
				System.out.println(p.first + "\t" + p.second);
			}
		}
		System.out.println("-------------------");
	}

	private static void testSet() {
		System.out.println("-------------------");
		Set<Integer> m = new BSTSet<Integer>();
		for (int i = 100; i > 0; i--) {
			m.insert(i % 13);
		}
		{
			Iterator<Integer> it = m.minIt();
			while (it.isValid()) {
				int p = it.next();
				System.out.println(p);
			}
		}
		System.out.println("----------");
		{
			Iterator<Integer> it = m.maxIt();
			while (it.isValid()) {
				int p = it.prev();
				System.out.println(p);
			}
		}
		System.out.println("-------------------");
	}

	private static void testGraph() {
		System.out.println("-------------------");
		int n = 8;
		Graph<Integer> g = new MGraph<Integer>();
		for (int i = 0; i < n; i++) {
			g.addNode(i);
		}
		for (int i = 0; i < n; i++) {
			g.addEdge(i, (i + 1) % n);
		}
		Iterator<Integer> it = g.nodesIt();
		while (it.isValid()) {
			int i = it.next();
			Set<Integer> ni = g.neighb(i);
			Iterator<Integer> nit = ni.minIt();
			while (nit.isValid()) {
				int j = nit.next();
				System.out.println(i + "\t" + j);
			}
		}
		System.out.println("-------------------");
	}

	private static void testRecommender() {
		System.out.println("-------------------");
		Graph<Integer> g = Recommender.read("graph.txt");
		{
			PQK<Double, Integer> top = Recommender.recommendDeg(g, 3, 4);

			while (top.length() > 0) {
				Pair<Double, Integer> e = top.serve();
				System.out.println(e.second + "\t" + e.first);
			}
		}
		System.out.println("----------");
		{
			PQK<Double, Integer> top = Recommender.recommendCN(g, 3, 4);
			while (top.length() > 0) {
				Pair<Double, Integer> e = top.serve();
				System.out.println(e.second + "\t" + e.first);
			}
		}
		System.out.println("----------");
		{
			PQK<Double, Integer> top = Recommender.recommendWCN(g, 3, 4);
			while (top.length() > 0) {
				Pair<Double, Integer> e = top.serve();
				System.out.println(e.second + "\t" + e.first);
			}
		}
		System.out.println("-------------------");
	}

	public static void main(String[] args) {
		testPQK();
		testMap();
		testSet();
		testGraph();
		testRecommender();
	}
}
