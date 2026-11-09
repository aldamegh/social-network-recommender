
public class ArrayPQK<P extends Comparable<P>, T> implements PQK<P, T> {
	private Pair<P, T>[] heap;
	private int size;
    private long[] arrival;
    private long sequence;

	
	
	public ArrayPQK(int k) {
		if (k < 0) throw new IllegalArgumentException("Negative capacity");
        heap = (Pair<P, T>[]) new Pair[k];
        arrival = new long[k];
		size = 0;
	}

	
	public int length() {
		return size;
	}

	
	public void enqueue(P pr, T e) {
        if (heap.length == 0) return;
        if (size == heap.length) {
            int worst = 0;
            for (int j = 1; j < size; j++) {
                int comparison = heap[j].first.compareTo(heap[worst].first);
                if (comparison < 0 || (comparison == 0 && arrival[j] > arrival[worst])) worst = j;
            }
            if (heap[worst].first.compareTo(pr) >= 0) return;
            heap[worst] = new Pair<P, T>(pr, e);
            arrival[worst] = sequence++;
            bubbleUp(worst);
        } else {
            heap[size] = new Pair<P, T>(pr, e);
            arrival[size] = sequence++;
            size++;
            bubbleUp(size-1);
        }
    }

	
	public Pair<P, T> serve() {
        if (size == 0) return null;
        Pair<P, T> result = heap[0];
        size--;
        heap[0] = heap[size];
        arrival[0] = arrival[size];
        heap[size] = null;
        if (size > 0) trickleDown(0);
        return result;
    }

    private boolean better(int a, int b) {
        int comparison = heap[a].first.compareTo(heap[b].first);
        return comparison > 0 || (comparison == 0 && arrival[a] < arrival[b]);
    }

	private void bubbleUp(int i) {
		while (i > 0 && better(i, getParent(i))) {
			swap(i, getParent(i));
			i = getParent(i);
		}
	}

	private void trickleDown(int i) {
		int left = getLeft(i);
		int right = getRight(i);
		int largest = i;
		if (left < size && better(left, largest)) {
			largest = left;
		}
		if (right < size && better(right, largest)) {
			largest = right;
		}
		if (largest != i) {
			swap(i, largest);
			trickleDown(largest);
		}
	}

	private void swap(int i, int largest) {
		Pair<P, T> temp = heap[i];
		heap[i] = heap[largest];
		heap[largest] = temp;
        long oldOrder = arrival[i]; arrival[i] = arrival[largest]; arrival[largest] = oldOrder;
	}

	private int getLeft(int index) {
		return 2 * index + 1;
	}

	private int getRight(int index) {
		return 2 * index + 2;
	}

	private int getParent(int index) {
		return (index - 1) / 2;
	}

}
