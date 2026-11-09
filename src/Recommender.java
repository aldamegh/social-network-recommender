import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Recommender {

	// Read graph from file. The file is a text file where each line contains an
	// edge. The end and start of the edge are separated by space(s) or tabs (see
	// graph.txt).
	public static Graph<Integer> read(String fileName) {
		Graph<Integer> g = new MGraph<Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				int start = Integer.parseInt(tokens[0]);
				int end = Integer.parseInt(tokens[1]);
				g.addNode(start);
				g.addNode(end);
				g.addEdge(start, end);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return g;
	}

	// Return the top k recommended friends for user i using the popular users
	// method. If i does not exist, return null. In case of a tie, users with the
	// lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendDeg(Graph<K> g, K i, int k) {
		if (!g.isNode(i)) {
			return null;
		}
		PQK<Double, K> top = new ArrayPQK<>(k);
		Iterator<K> it = g.nodesIt();
		while (it.isValid()) {
			K j = it.next();
			if (i.compareTo(j) != 0 && !g.isEdge(i, j)) {
				top.enqueue(g.deg(j) * 1.0, j);
			}
		}
		return top;

	}

	// Return the top k recommended friends for user i using common neighbors
	// method. If i does not exist, return null. In case of a tie, users with the
	// lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendCN(Graph<K> g, K i, int k) {
		PQK<Double, K> top = new ArrayPQK<>(k);
		if (!g.isNode(i)) {
			return null;
		}
		Iterator<K> it = g.nodesIt();

		while (it.isValid()) {
			K j = it.next();
			if (!g.isEdge(i, j) && i.compareTo(j) != 0) {
				int commonNeighbors = 0;
				Iterator<K> it2 = g.neighb(j).minIt();
				while (it2.isValid()) {
					K k2 = it2.next();
					if (g.isEdge(i, k2)) {
						commonNeighbors++;
					}
				}
				top.enqueue(commonNeighbors * 1.0, j);
			}
		}
		return top;

	}

	// Return the top k recommended friends for user i using weighted common
	// neighbors method. If i does not exist, return null. In case of a tie, users
	// with the lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendWCN(Graph<K> g, K i, int k) {
		if (!g.isNode(i)) {
			return null;
		}
		PQK<Double, K> top = new ArrayPQK<>(k);
		Iterator<K> it = g.nodesIt();
		while (it.isValid()) {
			K j = it.next();
			if (!g.isEdge(i, j) && i.compareTo(j) != 0) {
				int commonNeighbors = 0;
				Iterator<K> it2 = g.neighb(j).minIt();
				double value = 0;
				while (it2.isValid()) {
					K k2 = it2.next();
					if (g.isEdge(i, k2)) {
						value += 1.0 / g.deg(k2);
						commonNeighbors++;
					}
				}
				top.enqueue(value, j);
			}
		}
		return top;
	}
}
