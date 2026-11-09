

class BSTSetNode<K extends Comparable<K>> {
	public K key;
	public BSTSetNode<K> left, right, next, prev;

	public BSTSetNode(K key) {
		this.key = key;
		left = right = next = prev = null;
	}
}




public class BSTSet<K extends Comparable<K>> implements Set<K> {
	public BSTSetNode<K> root; // Do not change this
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

	
	public boolean exists(K k) {
		BSTSetNode<K> node = root;
		while (node != null) {
			if (k.compareTo(node.key) == 0) {
				return true;
			} else if (k.compareTo(node.key) < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return false;
	}

	private Pair<Boolean, BSTSetNode<K>> helpingInsert(BSTSetNode<K> node, K key) {
		if (node == null) {
			node = new BSTSetNode<K>(key);
			return new Pair<Boolean, BSTSetNode<K>>(true, node);
		}
		int diff = key.compareTo(node.key);
		if (diff == 0) {
			return new Pair<Boolean, BSTSetNode<K>>(false, node);
		} else {
			Pair<Boolean, BSTSetNode<K>> result = null;
			if (diff < 0) {
				result = helpingInsert(node.left, key);
				// setNext(result.second, node);
				node.left = result.second;
			} else {
				result = helpingInsert(node.right, key);
				node.right = result.second;
			}
			return new Pair<Boolean, BSTSetNode<K>>(result.first, node);
		}
	}

	
	public boolean insert(K k) {
		Pair<Boolean, BSTSetNode<K>> result = helpingInsert(root, k);
		root = result.second;
		if (result.first) {
			nelems++;
			// isNextPrevInitialized = false;
		}
		return result.first;
	}

	private boolean helpingRemove(BSTSetNode<K> node, K key) {
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
				BSTSetNode<K> temp = node.right;
				while (temp.left != null) {
					temp = temp.left;
				}
				node.key = temp.key;
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
			// isNextPrevInitialized = false;
		}
		return removed;

	}

	
	public Iterator<K> minIt() {
		return new BSTSetMinIterator<K>(root, nelems);
	}

	
	public Iterator<K> maxIt() {
		return new BSTSetMaxIterator<K>(root, nelems);
	}
}



class BSTSetMinIterator<K extends Comparable<K>> implements Iterator<K> {

	private BSTSetNode<K>[] stack;
	private int start;


//	@SuppressWarnings("unchecked")
	public BSTSetMinIterator(BSTSetNode<K> root, int size) {
		stack = (BSTSetNode<K>[])new BSTSetNode[size];
		
		start = 0;
		while (root != null) {
			stack[start++] = root;
			root = root.left;
		}
	}



	
	public boolean isValid() {
		return start > 0;
	}

	
	public K next() {
		BSTSetNode<K> node = stack[--start];
		BSTSetNode<K> temp = node.right;
		while (temp != null){
			stack[start++] = temp;
			temp = temp.left;
		}
		return node.key;
	}

	
	public K prev() {
		return null;
	}
}

class BSTSetMaxIterator<K extends Comparable<K>> implements Iterator<K> {

	private BSTSetNode<K>[] stack;
	private int start;


//	@SuppressWarnings("unchecked")
	public BSTSetMaxIterator(BSTSetNode<K> root, int size) {
		stack = (BSTSetNode<K>[])new BSTSetNode[size];
		
		start = 0;
		while (root != null) {
			stack[start++] = root;
			root = root.right;
		}
	}



	
	public boolean isValid() {
		return start > 0;
	}

	
	public K next() {
		return null;
	}

	
	public K prev() {
		BSTSetNode<K> node = stack[--start];
		BSTSetNode<K> temp = node.left;
		while (temp != null){
			stack[start++] = temp;
			temp = temp.right;
		}
		return node.key;
	}
}
