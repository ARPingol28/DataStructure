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
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class BH<E extends Comparable<E>> implements PriorityQueue<E> {

	private Wrapper<E>[] container; // creating an variable to store data in

	private int maxCapacity; // A variable that will store the Max storage
	private int currentCapacity; // A Variable that will store the current storage
	private long entryNumber;
	private int modCounter;

	public BH() {

		this(DEFAULT_MAX_CAPACITY); // Will call Default max capacity from Priority Queue

	}

	@SuppressWarnings("unchecked")
	public BH(int maxContainer) {

		entryNumber = 0;
		currentCapacity = 0;
		maxCapacity = maxContainer;
		modCounter = 0;
		container = new Wrapper[maxCapacity];

	}

	// Referenced this from the supplementary notes by prof. riggins
	// Binary search is an efficient algorithm for finding an item
	// from a sorted list of items
	/*private void trickleUp() {

		int child = currentCapacity - 1;
		int parent = (child - 1) >> 1; // divide by 2 efficiently
		Wrapper<E> value = container[child];
		while (parent >= 0 && value.compareTo(container[parent]) < 0) {
			container[child] = container[parent];
			child = parent;
			parent = (parent - 1) >> 1; // divide by 2 efficiently

		}
		container[child] = value;

	}*/

	// sort new element inserted at the top of the heap
	/*private void trickleDown() {
		int current = 0;// top of tree
		int child = getNextChild(current);
		
		Wrapper<E> tmp;
		
		  while (child != -1 && container[current].compareTo(container[child]) > 0) {
			tmp = container[current];
			container[current] = container[child];
			container[child] = tmp;
			current = child;
			child = getNextChild(current);
			
		}
		container[current] = container[currentCapacity-1];
			
	}*/

	// get smallest child of parent, based on data or entryNumber
	private int getNextChild(int current) {
		int left = (current << 1) + 1;// multiplies by 2, then adds 1
		int right = left + 1;
		if (right < currentCapacity) {// if there are two child
			if (container[left].compareTo(container[right]) < 0)
				return left;// left child is smaller
			return right;// right child is smaller
		}
		if (left < currentCapacity)
			return left;// if there is only one child
		return -1;// if no child
	}

	// Inserting new objects into the Priority Queue.
	// If successful, return true otherwise false if full
	// Helped by the T.A.'s
	public boolean insert(E object) {

		if (isFull()) {
			return false;
		} else {
			container[currentCapacity++] = new Wrapper<E>(object);
			//trickleUp();
			int child = currentCapacity - 1;
			int parent = (child - 1) >> 1; // divide by 2 efficiently
			Wrapper<E> value = container[child];
			while (parent >= 0 && value.compareTo(container[parent]) < 0) {
				container[child] = container[parent];
				child = parent;
				parent = (parent - 1) >> 1; // divide by 2 efficiently

			}
			container[child] = value;

			modCounter++;
			
		}
		
		return true;
	}

	// Objects with the highest priority that has been in the longest will be
	// removed.
	// Using a compareTo to compare between objects.
	// If Priority queue is empty, return null, otherwise the highest priority
	public E remove() {

		if (isEmpty()) {
			return null;
		}

		else {
			E temp = container[0].data; // element to return
			container[0] = container[currentCapacity - 1];
			
			//trickleDown(); // Being sorted in the heap
			int current = 0;// top of tree
			int child = getNextChild(current);
			
			Wrapper<E> tmp;
			
			  while (child != -1 && container[current].compareTo(container[child]) > 0) {
				tmp = container[current];
				container[current] = container[child];
				container[child] = tmp;
				current = child;
				child = getNextChild(current);
				
			}
			container[current] = container[currentCapacity-1];
			currentCapacity--;
			modCounter++;
			return temp;
		}

	}

	// Deleting all objects in the parameter from the Priority Queue if its there
	// Using a compareTo to compare between objects.
	// If found returns true otherwise false.

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean delete(E obj) {

		boolean flag = false;
		BH tmpHeap = new BH(currentCapacity);
		while (!isEmpty()) {
			E tmp = remove();
			if (tmp.compareTo(obj) >= 0) {
				tmpHeap.insert(tmp);
				currentCapacity--;
				modCounter++;
			}
			
			return true;
			
		}
		if (flag) {// if elements where deleted
			container = tmpHeap.container;
			currentCapacity = tmpHeap.currentCapacity;
			currentCapacity--;
			modCounter++;
		}
		return false;
	}

	// Peeks into the Priority Queue returning the highest priority
	// Using a compareTo to compare between objects.
	// if found, returns highest priority, otherwise false
	public E peek() {

		if (isEmpty()) {
			return null;
		}

		return container[0].data;

	}

	// Returns true if the priority queue contains the specified element
	// false otherwise.
	public boolean contains(E obj) {

		for (int i = 0; i < currentCapacity; i++) {
			if (((Comparable<E>) obj).compareTo(((E) container[i].data)) == 0)
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
		modCounter++;
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
		int iterIndex;
		long modNumber;

		public IteratorHelper() {
			iterIndex = 0;
			modNumber = modCounter;
		}

		public boolean hasNext() {
			if (modNumber != modCounter) // fail fast
				throw new ConcurrentModificationException();
			return iterIndex < currentCapacity;
		}

		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return container[iterIndex++].data;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	// needed to keep/preserve ordering of duplicate keys
	@SuppressWarnings("hiding")
	protected class Wrapper<E extends Comparable<E>> implements Comparable<Wrapper<E>> {

		long number;
		E data;

		public Wrapper(E obj) {
			number = entryNumber++;
			data = obj;
		}

		public int compareTo(Wrapper<E> obj) {
			if (((Comparable<E>)data).compareTo(obj.data) == 0) // if data is equal, use
				return (int) (number - obj.number); // sequence number
			return ((Comparable<E>)data).compareTo(obj.data);
		}

	}
}