package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.PriorityQueue;

public class UnorderedArray<E extends Comparable<E>> implements PriorityQueue<E> {

	private E[] array;
	private int maxSize;
	private int currentSize;
	protected long deleteCounter;

	
	
	
	@SuppressWarnings("unchecked")
	public UnorderedArray(int maxContainer) {
		currentSize = 0;
		maxSize = maxContainer;
		array = (E[]) new Object[maxSize];
	}
	
	public UnorderedArray() {

		this(DEFAULT_MAX_CAPACITY);

	}

	// Inserts a new object into the priority queue. Returns true if
	// the insertion is successful. If the PQ is full, the insertion
	// is aborted, and the method returns
	// false.data_structures.UnorderedArrayPriorityQueue.deleteCounter
	public boolean insert(E object) {

		if (isFull()) {
			return false;
		}

		else if (currentSize < maxSize) { // Checking if the size is less than 1000
			array[currentSize++] = object; // If so, insert,adds into stack

		}
		return true;

	}

	// Removes the object of highest priority that has been in the
	// PQ the longest, and returns it. Returns null if the PQ is empty.
	public E remove() {
		if (isEmpty()) {
			return null;
		}

		int index = 0;
		E arrayHigh = array[0];

		for (int i = 1; i < currentSize; i++) {
			if (((Comparable<E>) array[i]).compareTo(arrayHigh) > 0) {
				throw new IndexOutOfBoundsException();
			} else {
				index = i;
				arrayHigh = array[i];
			}
		}

		for (int j = index; j < currentSize - 1; j++) {
			array[j] = array[j + 1];
		}
		currentSize--;

		return arrayHigh;
	}

	// Deletes all instances of the parameter obj from the PQ if found, and
	// returns true. Returns false if no match to the parameter obj is found.
	public boolean delete(E obj) {

		deleteCounter = 0;
		if (contains(obj)) {
			return false;
		} else {
			deleteCounter++;
			return true;
		}

	}

	// Returns the object of highest priority that has been in the
	// PQ the longest, but does NOT remove it.
	// Returns null if the PQ is empty.
	public E peek() {
		if (isEmpty())
			return null;

		int index = 0;

		for (int i = 1; i < currentSize; i++) {
			if (((Comparable<E>) array[i]).compareTo(array[index]) > 0) {
				index = i;
			}
		}

		return array[index];
	}

	// Returns true if the priority queue contains the specified element
	// false otherwise.
	public boolean contains(E obj) {
		for (int i = 0; i < currentSize; i++)
			if (((Comparable<E>) obj).compareTo(((E) array[i])) == 0)
				return true;
		return false;
	}

	// Returns the number of objects currently in the PQ.
	public int size() {
		return currentSize;
	}

	// Returns the PQ to an empty state.
	public void clear() {
		currentSize = 0;
	}

	// Returns true if the PQ is empty, otherwise false
	public boolean isEmpty() {
		if (currentSize > 0) {
			return false;
		} else if (currentSize == 0) {

		}
		return true;
	}

	// Returns true if the PQ is full, otherwise false. List based
	// implementations should always return false.
	public boolean isFull() {
		if (currentSize == maxSize) {
			return true;
		} else {
			return false;
		}

	}

	// Returns an iterator of the objects in the PQ, in no particular
	// order.
	
	


	public Iterator<E> iterator() {

		return new IteratorHelper();
	}

	private class IteratorHelper implements Iterator<E> {
		private int counter = 0;

		public boolean hasNext() {
			return counter < currentSize;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (E) array[counter++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
