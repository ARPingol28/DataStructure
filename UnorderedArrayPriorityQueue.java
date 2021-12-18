/**
     *  Program # 1: Unordered Array Priority Queue
     *  Using a implementation of Priority Queue. For using an ADT, The removal operations always return the object in the queue of highest priority that has been in
     *  the queue the longest. That is, no object of a given priority is ever removed as long as the queue contains one or more object of a higher priority.
     *  CS310-01
     *  Febraury 20, 2020
     *  @author Ariel Pingol cssc1259
     */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private E[] container;
	private int maxCapacity;
	private int currentCapacity;
	protected long deleteCounter;

	public UnorderedArrayPriorityQueue() {

		this(DEFAULT_MAX_CAPACITY);

	}

	@SuppressWarnings("unchecked")
	public UnorderedArrayPriorityQueue(int maxContainer) {
		currentCapacity = 0;
		maxCapacity = maxContainer;
		container = (E[]) new Comparable[maxCapacity];

	}

	// Inserting new objects into the Priority Queue.
	// If successful, return true otherwise false if full
	public boolean insert(E object) {

		if (isFull()) {
			return false;
		} else {

			container[currentCapacity++] = object;

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

			if (((Comparable<E>) container[i]).compareTo(highPriority) < 0) {
				storedValue = i;
				highPriority = container[i];
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

		for (int i = 0; i < currentCapacity; i++) {
			if (((Comparable<E>) container[i]).compareTo(obj) == 0) {
				

				for (int j = i; j < currentCapacity - 1; j++) {
					container[j] = container[j + 1];
				}
				currentCapacity--;
				
			}
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
		private int counter = 0;

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







/*

	public boolean delete(K key){
        if(isEmpty()) return false;
	if(!remove(key,root,null,false))
	    return false;
	currentSize--;
	modCounter++;
	return true;
	}

	private boolean remove(K k, Node<K,V> n, Node<K,V> parent, boolean wasLeft){
		if(n == null) return false;
		int comp = ((Comparable<K>)k).compareTo(n.key);
		if(comp < 0)
			return remove(k,n.leftChild,n,true);		//go left
		else if(comp > 0)
			return remove(k,n.rightChild,n,false);	//go right
		else{						//found!
			if(n.leftChild == null && n.rightChild == null){	//no children
			if(parent == null)
				root = null;
			else if(wasLeft)
				parent.leftChild = null;
			else
				parent.rightChild = null;
		}
		else if(n.leftChild == null){	//1 right child
			if(parent == null)
				root = n.rightChild;
			else if(wasLeft)
				parent.leftChild = n.rightChild;
			else
				parent.rightChild = n.rightChild;
		}
		else if(n.rightChild == null){	//1 left child
			if(parent == null)
			 root = n.leftChild;
		 else if(wasLeft)
			 parent.leftChild = n.leftChild;
		 else
			 parent.rightChild = n.leftChild;
		}
		else{				//two children
			Node<K,V> tmp = getSuccessor(n.rightChild);
		 if(tmp == null){
			 n.key = n.rightChild.key;
			 n.value = n.rightChild.value;
			 n.rightChild = n.rightChild.rightChild;
		 }
		 else{
			 n.key = tmp.key;
			 n.value = tmp.value;
		 }
		}
		}
		return true;
	 }
 // getSuccessor method returns the successor in the dictionary.
	 private Node<K,V> getSuccessor(Node<K,V> n){
		 Node<K,V> parent = null;
	 while(n.leftChild != null){
		 parent = n;
		 n = n.leftChild;
	 }
	 if(parent == null)
		 return null;
	 else
		 parent.leftChild = n.rightChild;
	 return n;
	 }
	


*/






















}
