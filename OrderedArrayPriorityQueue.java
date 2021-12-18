/**
      *  Program # 1: Ordered Array Priority Queue
      *  Using a implementation of Priority Queue. For using an ADT, The removal operations always return the object in the queue of highest priority that has been in
      *  the queue the longest. That is, no object of a given priority is ever removed as long as the queue contains one or more object of a higher priority.
      *  CS310-01
      *  Febraury 20, 2020
      *  @author Ariel Pingol cssc1259
      */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private E[] container; // creating an variable to store data in
	private int maxCapacity; // A variable that will store the Max storage
	private int currentCapacity; // A Variable that will store the current storage

	public OrderedArrayPriorityQueue() {

		this(DEFAULT_MAX_CAPACITY); // Will call Default max capacity from Priority Queue

	}

	@SuppressWarnings("unchecked")
	public OrderedArrayPriorityQueue(int maxContainer) {

		currentCapacity = 0;
		maxCapacity = maxContainer;
		container = (E[]) new Comparable[maxCapacity];

	}

	// Referenced this from the supplementary notes by prof. riggins
	// Binary search is an efficient algorithm for finding an item
	// from a sorted list of items
	private int binarySearch(E object, int firstIndex, int lastIndex) {

		if (lastIndex < firstIndex)
			return firstIndex;
		int midIndex = (firstIndex + lastIndex) >> 1;
		if (((Comparable<E>) container[midIndex]).compareTo(object) > 0)
			return binarySearch(object, firstIndex, midIndex - 1);
		else
			return binarySearch(object, midIndex + 1, lastIndex);

	}

	// Inserting new objects into the Priority Queue.
	// If successful, return true otherwise false if full
	// Helped by the T.A.'s
	public boolean insert(E object) {

		if (isFull()) {
			return false;
		} else {
			int location = binarySearch(object, 0, currentCapacity - 1); // Storing binarySearch into a variable.
			for (int i = currentCapacity - 1; i >= location; --i)
				container[i + 1] = container[i];
			container[location] = object;
			currentCapacity++;
		}
		return true;

	}

	// Objects with the highest priority that has been in the longest will be
	// removed.
	// Using a compareTo to compare between objects.
	// If Priority queue is empty, return null, otherwise the highest priority
	public E remove() {

		E highPriority = container[0];
		int storedValue = 0;

		if (isEmpty()) {
			return null;
		}

		for (int i = 1; i < currentCapacity; i++) {
			final E object = container[i];
			if (((Comparable<E>) object).compareTo(highPriority) < 0) {
				storedValue = i;
				highPriority = object;
			}

		}
		for (int j = storedValue; j < currentCapacity - 1; j++) {
			container[j] = container[j + 1];

		}
		currentCapacity--;
		return highPriority;

	}

	// Deleting all objects in the parameter from the Priority Queue if its there
	// Using a compareTo to compare between objects.
	// If found returns true otherwise false.
	public boolean delete(E obj) {

		if (currentCapacity == 0) {
			return false;
		}
		int index = 0;
		int location = binarySearch(obj, 0, currentCapacity - 1); // Storing Binary Search in a variable.
		for (int i = index; i < location; i++) { // Using a for loop to insert the object into container
			container[i] = container[i];
			currentCapacity--;

		}
		return true;
	}

	// Peeks into the Priority Queue returning the highest priority
	// Using a compareTo to compare between objects.
	// if found, returns highest priority, otherwise false
	public E peek() {

		if (isEmpty()) {
			return null;
		}

		int index = 0;

		for (int i = 1; i < currentCapacity; i++) {
			if (((Comparable<E>) container[i]).compareTo(container[index]) < 0) {
				index = i;
			}
		}

		return container[index];

	}

	// Returns true if the priority queue contains the specified element
	// false otherwise.
	public boolean contains(E obj) {

		for (int i = 0; i < currentCapacity; i++) {
			if (((Comparable<E>) obj).compareTo(((E) container[i])) == 0)
				return true;
		}
		return false;

	}

	// Returns the number of objects currently in the PQ.
	public int size() {

		return currentCapacity;
	}

	// Returns the PQ to an empty state.
	public void clear() {

		currentCapacity = 0;
	}

	// Returns true if the PQ is empty, otherwise false
	public boolean isEmpty() {

		if (currentCapacity > 0) {
			return false;
		} else if (currentCapacity == 0) {

		}
		return true;
	}

	// Returns true if the PQ is full, otherwise false. List based
	// implementations should always return false.
	public boolean isFull() {

		if (currentCapacity < maxCapacity) {
			return false;
		} else if (currentCapacity == maxCapacity) {

		}
		return true;
	}

	// Returns an iterator of the objects in the PQ, in no particular
	// order.

	public Iterator<E> iterator() {

		return new IteratorHelper();
	}

	private class IteratorHelper implements Iterator<E> {
		private int counter;

		public boolean hasNext() {
			return counter < currentCapacity;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (E) container[counter++];
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}