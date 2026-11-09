public interface Iterator<T> {

	// Returns true if the iterator is valid (points to an element), false otherwise.
	boolean isValid();

	// Returns the current element and moves forward. This method can only be called if the iterator is valid. If the iterator points to the last element, it becomes invalid after the call.
	T next();

	// Returns the current element and moves backwards. This method can only be called if the iterator is valid. If the iterator points to the first element, it becomes invalid after the call.
	T prev();
}
