/**
     *  Program # 4: Binary Search Tree
     *  Using a implementation of Dictionary ADT. For using an ADT, this can handle larger data set than the previous programs
     *  CS310-01
     *  May 02, 2020
     *  @author Ariel Pingol cssc1259
     */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K, V> implements DictionaryADT<K, V> {

	private Node<K, V> root;
	private int currentCapacity;
	private int modCounter;
	private K findK;

	public BinarySearchTree() {

		root = null;
		currentCapacity = 0;
		modCounter = 0;
	}

	// Adds the given key/value pair to the dictionary.
	// 1) Returns false if the dictionary is full,
	// 2) or if the key is a duplicate.
	// 3) Returns true if addition succeeded.
	public boolean put(K key, V value) {

		if (isFull()) {

			return false;
		}
		if (findValue(key, root) != null) {

			return false;
		}
		if (root == null) {

			root = new Node<K, V>(key, value);
		} else {
			insert(key, value, root, null, false);
		}
		currentCapacity++;
		modCounter++;
		return true;

	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	@SuppressWarnings("unchecked")
	public boolean delete(K key) {

		if (isEmpty())
			return false;
		if (findValue(key, root) == null)
			return false;
		if (root == null)
			return false;

		Node<K, V> current = root;
		Node<K, V> previous = null;
		boolean left = false;
		while (((Comparable<K>) key).compareTo((K) current.key) != 0) {
			if (((Comparable<K>) key).compareTo((K) current.key) < 0) { // Go Left
				previous = current;
				current = current.leftChild;
				left = true;
			} else { // Go Right
				previous = current;
				current = current.rightChild;
				left = false;
			}
			if (current == null)
				return false;
		}

		if (current.leftChild == null && current.rightChild == null) { // No Children
			if (previous == null) {
				root = null;
			} else {
				if (left)
					previous.leftChild = null;
				else
					previous.rightChild = null;
			}
		} else if (current.leftChild == null && current.rightChild != null) { // 1 Right Child
			if (previous == null) {
				root = current.rightChild;
			} else {
				if (left)
					previous.leftChild = current.rightChild;
				else
					previous.rightChild = current.rightChild;
			}
		} else if (current.leftChild != null && current.rightChild == null) { // 1 Left Child
			if (previous == null) {
				root = current.leftChild;
			} else {
				if (left)
					previous.leftChild = current.leftChild;
				else
					previous.rightChild = current.leftChild;
			}
		} else {
			Node<K, V> tmp = current.rightChild; // Returns the successor in the dictionary
			Node<K, V> tmpP = current;
			boolean right = true;
			while (tmp.leftChild != null) {
				tmpP = tmp;
				tmp = tmp.leftChild;
				right = false;
			}

			if (previous == null) {
				root.key = tmp.key;
				root.value = tmp.value;
			} else {
				current.key = tmp.key;
				current.value = tmp.value;
			}
			if (tmp.rightChild != null) {
				if (!right)
					tmpP.leftChild = tmp.rightChild;
				else
					tmpP.rightChild = tmp.rightChild;
			} else {
				if (!right)
					tmpP.leftChild = null;
				else
					tmpP.rightChild = null;
			}

		}

		currentCapacity--;
		modCounter++;
		return true;

	}

	// Returns the value associated with the parameter key. Returns
	// null if the key is not found or the dictionary is empty.
	public V get(K key) {
		return findValue(key, root);
	}

	// Returns the key associated with the parameter value. Returns
	// null if the value is not found in the dictionary. If more
	// than one key exists that matches the given value, returns the
	// first one found.
	public K getKey(V value) {
		findK = null;
		findKey(root, value);
		return findK;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {
		return currentCapacity;
	}

	// Returns true if the dictionary is full
	public boolean isFull() {
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		if (currentCapacity > 0) {
			return false;
		} else if (currentCapacity == 0) {

		}
		return true;
	}

	// Makes the dictionary empty
	public void clear() {
		root = null;
		currentCapacity = 0;
		modCounter = 0;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order. The iterator must be fail-fast.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Iterator<K> keys() {
		return new KeyIteratorHelper();
	}

	// Returns an Iterator of the values in the dictionary. The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Iterator<V> values() {
		return new ValueIteratorHelper();
	}

	// Referenced code from Prof. Riggins code
	@SuppressWarnings("unchecked")
	private void insert(K key, V value, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
		if (n == null) {
			if (wasLeft)
				parent.leftChild = new Node<K, V>(key, value);
			else
				parent.rightChild = new Node<K, V>(key, value);
		} else if (((Comparable<K>) key).compareTo((K) n.key) < 0)
			insert(key, value, n.leftChild, n, true);
		else
			insert(key, value, n.rightChild, n, false);
	}

	// Referenced code from Prof. Riggins notes
	@SuppressWarnings("unchecked")
	private V findValue(K key, Node<K, V> value1) {
		if (value1 == null) {
			return null;
		}
		if (((Comparable<K>) key).compareTo(value1.key) < 0) {
			return findValue(key, value1.leftChild);
		} else if (((Comparable<K>) key).compareTo(value1.key) > 0) {
			return findValue(key, value1.rightChild);
		} else {
			return (V) value1.value;
		}
	}

	// Looking up the key: Recursively.
	// Search until key is found
	@SuppressWarnings("unchecked")
	private void findKey(Node<K, V> key1, V value) {

		if (key1 == null) {

			return;

		}
		if (((Comparable<V>) value).compareTo(key1.value) == 0) {

			findK = key1.key;
			return;

		}
		findKey(key1.leftChild, value);
		findKey(key1.rightChild, value);
	}

	// Referenced code from Prof. Riggins notes
	@SuppressWarnings("hiding")
	private class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> leftChild, rightChild;

		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}

	abstract class IteratorHelper<E> implements Iterator<E> {
		protected int i;
		protected int j;
		protected int modCount;
		Node<K, V>[] array;

		@SuppressWarnings("unchecked")
		public IteratorHelper() {
			i = 0;
			j = 0;
			modCount = modCounter;
			array = new Node[currentCapacity];
			inOrder(root);
		}

		public boolean hasNext() {
			if (modCount != modCounter) {

				throw new ConcurrentModificationException();
			}
			return i < currentCapacity;
		}

		public abstract E next();

		public void remove() {

			throw new UnsupportedOperationException();
		}

		// For iterators and other operations where you must Traverse
		// the entire tree. Recursion: best way
		// Referenced code Prof. Riggins notes
		private void inOrder(Node<K, V> n) {
			if (n != null) {
				inOrder(n.leftChild);
				array[j++] = n;
				inOrder(n.rightChild);
			}
		}
	}

	// Referenced code from Prof. Riggins in Hash table section
	@SuppressWarnings("hiding")
	class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}

		@SuppressWarnings("unchecked")
		public K next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (K) array[i++].key;
		}
	}

	@SuppressWarnings("hiding")
	private class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {

			super();

		}

		@SuppressWarnings("unchecked")
		public V next() {

			if (!hasNext()) {

				throw new NoSuchElementException();

			}

			return (V) array[i++].value;
		}
	}
}