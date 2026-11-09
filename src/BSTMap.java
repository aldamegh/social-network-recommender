
class BSTMapNode<K extends Comparable<K>, T> {
	public K key;
	public T data;
	public BSTMapNode<K, T> left, right, next, prev;

	public BSTMapNode(K key, T data) {
		this.key = key;
		this.data = data;
		left = right = next = prev = null;
	}
}


public class BSTMap<K extends Comparable<K>, T> implements Map<K, T> {
	public BSTMapNode<K, T> root; // Do not change this
	private int nelems = 0;

	
	public int size() {
		return nelems;
	}

	
	public boolean full() {
		return false;
	}

	
	public void clear() {
		root = null;
		nelems = 0;
	}

	
	public boolean update(K k, T e) {
		BSTMapNode<K, T> node = root;
		while (node != null) {
			if (k.compareTo(node.key) == 0) {
				node.data = e;
				return true;
			} else if (k.compareTo(node.key) < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return false;
	}

	
	public Pair<Boolean, T> retrieve(K k) {
		BSTMapNode<K, T> node = root;
		while (node != null) {
			if (k.compareTo(node.key) == 0) {
				return new Pair<Boolean, T>(true, node.data);
			} else if (k.compareTo(node.key) < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return new Pair<Boolean, T>(false, null);
	}

	private Pair<Boolean, BSTMapNode<K, T>> helpingInsert(BSTMapNode<K, T> node, K key, T data) {
		if (node == null) {
			node = new BSTMapNode<K, T>(key, data);
			return new Pair<Boolean, BSTMapNode<K, T>>(true, node);
		}
		int diff = key.compareTo(node.key);
		if (diff == 0) {
			return new Pair<Boolean, BSTMapNode<K, T>>(false, node);
		} else {
			Pair<Boolean, BSTMapNode<K, T>> result = null;
			if (diff < 0) {
				result = helpingInsert(node.left, key, data);
				node.left = result.second;
			} else {
				result = helpingInsert(node.right, key, data);
				node.right = result.second;
			}
			return new Pair<Boolean, BSTMapNode<K, T>>(result.first, node);
		}
	}

	
	public boolean insert(K k, T e) {
		Pair<Boolean, BSTMapNode<K, T>> result = helpingInsert(root, k, e);
		root = result.second;
		if (result.first) {
			nelems++;
			
		}
		return result.first;
	}

	private boolean helpingRemove(BSTMapNode<K, T> node, K key) {
		if (node == null) {
			return false;
		}
		int diff = key.compareTo(node.key);
		if (diff == 0) {
			if (node.left == null && node.right == null) {
				node = null;
				return true;
			} else if (node.left == null) {
				node = node.right;
				return true;
			} else if (node.right == null) {
				node = node.left;
				return true;
			} else {
				BSTMapNode<K, T> temp = node.right;
				while (temp.left != null) {
					temp = temp.left;
				}
				node.key = temp.key;
				node.data = temp.data;
				return helpingRemove(node.right, temp.key);
			}
		} else if (diff < 0) {
			return helpingRemove(node.left, key);
		} else {
			return helpingRemove(node.right, key);
		}
	}

	
	public boolean remove(K k) {
		boolean removed = helpingRemove(root, k);
		if (removed) {
			nelems--;
		}
		return removed;
	}


	// Return an iterator that points to the minimum key. The iterator moves
	// according to the order of the keys.
	
	public Iterator<Pair<K, T>> minIt() {
		return new BSTMapMinIterator<K, T>(root, nelems);
	}

	
	public Iterator<Pair<K, T>> maxIt() {
		return new BSTMapMaxIterator<K, T>(root, nelems);
	}
}

class BSTMapMinIterator<K extends Comparable<K>, T> implements Iterator<Pair<K, T>> {

	private BSTMapNode<K, T>[] stack;
	private int start;


	
	public BSTMapMinIterator(BSTMapNode<K, T> root, int size) {
		stack = (BSTMapNode<K, T>[])new BSTMapNode[size];
		
		start = 0;
		while (root != null) {
			stack[start++] = root;
			root = root.left;
		}
	}




	public boolean isValid() {
		return start > 0;
	}


	public Pair<K, T> next() {
		BSTMapNode<K, T> node = stack[--start];
		BSTMapNode<K, T> temp = node.right;
		while (temp != null){
			stack[start++] = temp;
			temp = temp.left;
		}
		return new Pair<K,T>(node.key, node.data);

	}


	public Pair<K, T> prev() {
		return null;
	}
}

class BSTMapMaxIterator<K extends Comparable<K>, T> implements Iterator<Pair<K, T>> {

	private BSTMapNode<K, T>[] stack;
	private int start;



	public BSTMapMaxIterator(BSTMapNode<K, T> root, int size) {
		stack = (BSTMapNode<K, T>[])new BSTMapNode[size];
		
		start = 0;
		while (root != null) {
			stack[start++] = root;
			root = root.right;
		}
	}



	
	public boolean isValid() {
		return start > 0;
	}

	
	public Pair<K, T> next() {
		return null;
	}

	
	public Pair<K, T> prev() {
		BSTMapNode<K, T> node = stack[--start];
		BSTMapNode<K, T> temp = node.left;
		while (temp != null){
			stack[start++] = temp;
			temp = temp.right;
		}
		return new Pair<K,T>(node.key, node.data);
	}
}
