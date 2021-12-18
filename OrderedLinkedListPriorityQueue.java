/**
     *  Program # 2: Ordered Linked List Priority Queue
     *  Using a implementation of Priority Queue. For using an ADT, The removal operations always return the object in the queue of highest priority that has been in
     *  the queue the longest. That is, no object of a given priority is ever removed as long as the queue contains one or more object of a higher priority.
     *  CS310-01
     *  March 12, 2020
     *  @author Ariel Pingol cssc1259
     */
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedLinkedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {

	private Node<E> head;

	private int size;
	protected long modCounter;

	public OrderedLinkedListPriorityQueue() {
		head = null;
		size = 0;
		modCounter = 0;

	}

	@SuppressWarnings("hiding")
	public class Node<E extends Comparable<E>> {
		E data;
		Node<E> next;

		public Node(E object) {
			data = object;
			next = null;
		}
	}

	// Inserting new objects into the Priority Queue.
	// If successful, return true otherwise false if full
	//Helped by the TA
	public boolean insert(E object) {
		Node<E> newNode = new Node<>(object);
		Node<E> previous = null;
		Node<E> current = head;

		while (current != null && current.data.compareTo(object) <= 0) {
			previous = current;
			current = current.next;
		}

		if (previous == null) {
			// the node is now placed at the head
			newNode.next = head;
			head = newNode;
		} else {
			// the node is now placed in the end or middle of the Linked list
			previous.next = newNode;
			newNode.next = current;
		}

		size++;
		modCounter++;
		return true;
	}

	// Objects with the highest priority that has been in the longest will be
	// removed.
	// If Priority queue is empty, return null, otherwise the highest priority
	public E remove() {

		if (isEmpty())
			return null;
		E data = head.data;
		head = head.next;
		size--;
		modCounter++;
		return data;

	}

	// Deleting all objects in the parameter from the Priority Queue if its there
	// Using a compareTo to compare between objects.
	// If found returns true otherwise false.
	// Referenced Riggins videos
	public boolean delete(E obj) {
		Node<E> previous = head;
		Node<E> current = head;

		if (size == 0) {
			return false;
		}
		while (current != null && contains(obj)) {
			previous = current;
			previous.next = current;
			current = current.next;
			size--;

		}

		modCounter++;
		return true;
	}

	// Peeks into the Priority Queue returning the highest priority
	// Using a compareTo to compare between objects.
	// if found, returns highest priority, otherwise false

	public E peek() {

		if (isEmpty())
			return null;
		Node<E> current;
		E min;
		current = head;
		min = current.data;
		while (current != null) {
			if (current.data.compareTo(min) < 0)
				min = current.data;
			current = current.next;
		}
		return min;

	}

	// Returns true if the priority queue contains the specified element
	// false otherwise.
	public boolean contains(E obj) {

		Node<E> node = head;
		while (node.next != null && node.data.compareTo(obj) >= 0) {
			node = node.next;
			return true;
		}
		return false;
	}

	// Returns the number of objects currently in the PQ.
	public int size() {

		return size;
	}

	// Returns the PQ to an empty state.
	public void clear() {

		size = 0;
		head = null;
		modCounter++;
	}

	// Returns true if the PQ is empty, otherwise false
	public boolean isEmpty() {

		return size() == 0;
	}

	// Returns true if the PQ is full, otherwise false. List based
	// implementations should always return false.
	public boolean isFull() {

		return false;
	}

	// Returns an iterator of the objects in the PQ, in no particular
	// order.
	@Override
	public Iterator<E> iterator() {

		return new ListIteratorHelper();
	}

	private class ListIteratorHelper implements Iterator<E> {
		Node<E> nodePointer;
		private long count = modCounter;

		public ListIteratorHelper() {
			nodePointer = head;
		}

		public boolean hasNext() {
			return nodePointer != null;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (count != modCounter) {
				throw new ConcurrentModificationException();
			}
			E tmp = nodePointer.data;
			nodePointer = nodePointer.next;
			return tmp;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
