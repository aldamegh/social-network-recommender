public interface Set<K extends Comparable<K>> {

	// Return the size of the set.
	int size();

	// Return true if the set is full.
	boolean full();

	// Remove all keys from the set.
	void clear();

	// Search for the key k and return true if it exists, false otherwise.
	boolean exists(K k);

	// Insert a new key if does not exist and return true. If k already exists, return false.
	boolean insert(K k);

	// Remove the key k if it exists and return true. If the key does not exist return false.
	boolean remove(K k);

	// Return an iterator that points to the minimum key. The iterator moves according to the order of the keys.
	Iterator<K> minIt();

	// Return an iterator that points to the minimum key. The iterator moves according to the order of the keys.
	Iterator<K> maxIt();

}
