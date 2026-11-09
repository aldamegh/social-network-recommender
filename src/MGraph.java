public class MGraph<K extends Comparable<K>> implements Graph<K> {
	public Set<K> nodes; // Do not change this
	public Map<K, Set<K>> adj; // Do not change this

	public MGraph() {
		nodes = new BSTSet<K>();
		adj = new BSTMap<K, Set<K>>();
	}

	
	public boolean addNode(K i) {
		if (!nodes.insert(i)) return false;
        adj.insert(i, new BSTSet<K>());
        return true;
	}

	
	public boolean isNode(K i) {
		return nodes.exists(i);
	}

	
	public boolean addEdge(K i, K j) {
		if (!isNode(i) || !isNode(j) || i.compareTo(j) == 0){
			return false;
		}
		if (!adj.retrieve(i).first) {
			adj.insert(i, new BSTSet<K>());
		}
		if (!adj.retrieve(j).first) {
			adj.insert(j, new BSTSet<K>());
		}

		Set<K> set = adj.retrieve(i).second;
		Set<K> set2 = adj.retrieve(j).second;
		if (set.exists(j)) return false;
        set.insert(j); set2.insert(i);
        return true;
	}


	public boolean isEdge(K i, K j) {
		if (!adj.retrieve(i).first) {
			return false;
		}
		Set<K> set = adj.retrieve(i).second;
		return set.exists(j);
	}

	
	public Set<K> neighb(K i) {
		if (!isNode(i)) {
			return null;
		}
		return adj.retrieve(i).second;
	}

	
	public int deg(K i) {
		if (!isNode(i)){
			return -1;
		}
		if (!adj.retrieve(i).first) {
			return 0;
		}
		return adj.retrieve(i).second.size();
	}

	
	public Iterator<K> nodesIt() {
		return nodes.minIt();
	}
}
