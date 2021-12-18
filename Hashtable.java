/**
     *  Program # 4: Hash Table
     *  Using a implementation of Dictionary ADT. For using an ADT, this can handle larger data set than the previous programs
     *  CS310-01
     *  May 02, 2020
     *  @author Ariel Pingol cssc1259
     */
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K, V> implements DictionaryADT<K, V> {
	private int tableCapacity;
	private int currentCapacity;
	private int maxCapacity;
	private int modCounter;
	private LinkedListDS<DictionaryADT<K, V>>[] chaining;

	// Referenced Prof. Riggins Notes
	@SuppressWarnings("unchecked")
	public Hashtable(int n) {
		maxCapacity = n;
		tableCapacity = (int) (n * 1.3f);
		currentCapacity = 0;
		modCounter = 0;
		chaining = new LinkedListDS[tableCapacity];
		for (int i = 0; i < tableCapacity; i++)
			chaining[i] = new LinkedListDS<DictionaryADT<K, V>>();
	}

	// Adds the given key/value pair to the dictionary. Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean put(K key, V value) {

		if (isFull() || chaining[getHashCode(key)].contains(new DictionaryADT<K, V>(key, null))) {

			return false;
		}

		chaining[getHashCode(key)].addLast(new DictionaryADT<K, V>(key, value));
		currentCapacity++;
		modCounter++;
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {

		if (isEmpty() || !chaining[getHashCode(key)].contains(new DictionaryADT<K, V>(key, null))) {

			modCounter++;
			return false;
		}

		else if (chaining[getHashCode(key)].contains(new DictionaryADT<K, V>(key, null))) {
			chaining[getHashCode(key)].remove(new DictionaryADT<K, V>(key, null));
			currentCapacity--;
			modCounter++;
		}

		return true;
	}

	// Returns the value associated with the parameter key. Returns
	// null if the key is not found or the dictionary is empty.
	public V get(K key) {

		if (isEmpty() || !chaining[getHashCode(key)].contains(new DictionaryADT<K, V>(key, null))) {
			return null;
		}
		DictionaryADT<K, V> associatedKey = chaining[getHashCode(key)].search(new DictionaryADT<K, V>(key, null));
		if (associatedKey == null) {

			return null;
		} else {
			return associatedKey.value;
		}
	}

	// Returns the key associated with the parameter value. Returns
	// null if the value is not found in the dictionary. If more
	// than one key exists that matches the given value, returns the
	// first one found.
	@SuppressWarnings("unchecked")
	public K getKey(V value) {

		for (int i = 0; i < tableCapacity; i++) {

			for (DictionaryADT<K, V> associatedValue : chaining[i]) {

				if (((Comparable<V>) associatedValue.value).compareTo(value) == 0) {

					return associatedValue.key;
				}
			}
		}
		return null;
	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {

		return currentCapacity;
	}

	// Returns true if the dictionary is full
	public boolean isFull() {

		if (currentCapacity < maxCapacity) {
			return false;
		} else if (currentCapacity == maxCapacity) {
		}
		return true;
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
		for (int i = 0; i < tableCapacity; i++)
			chaining[i].makeEmpty();
		currentCapacity = 0;
		modCounter = 0;
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order
	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}

	// Returns an Iterator of the values in the dictionary. The
	// order of the values must match the order of the keys.
	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}

	// Referenced code from Prof. Riggins code
	private int getHashCode(K key) {
		return (key.hashCode() & 0x7FFFFFFF) % tableCapacity; // Forces the leading bit of the hash code to be zero,
																// making the number positive if it is negative.
	}

	// Implementing a hash table using Chaining
	// Creating a Wrapper class to hold two things: Key & Value
	// Referenced This code from Prof. Riggins notes
	@SuppressWarnings("hiding")
	private class DictionaryADT<K, V> implements Comparable<DictionaryADT<K, V>> {
		K key;
		V value;

		public DictionaryADT(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@SuppressWarnings("unchecked")
		public int compareTo(DictionaryADT<K, V> node) {
			return ((Comparable<K>) key).compareTo((K) node.key);
		}
	}

	// Referenced this Iterator in Prof. Riggins Notes
	abstract class IteratorHelper<E> implements Iterator<E> {
		@SuppressWarnings("rawtypes")
		protected DictionaryADT[] nodes;
		protected int index;
		protected long modCounter1;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public IteratorHelper() {
			modCounter1 = modCounter;
			nodes = new DictionaryADT[currentCapacity];
			index = 0;
			int j = 0;
			for (int i = 0; i < tableCapacity; i++) // fill in auxiliary array
				for (DictionaryADT k : chaining[i])
					nodes[j++] = k;
			nodes = (DictionaryADT[]) shellSort(nodes);
		}

		public boolean hasNext() {
			if (modCounter1 != modCounter)
				throw new ConcurrentModificationException();
			return index < currentCapacity;
		}

		public abstract E next();

		public void remove() {
			throw new UnsupportedOperationException();
		}

		// Referneced from Prof.Riggins Notes
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private DictionaryADT<K, V>[] shellSort(DictionaryADT<K, V>[] array) {
			int in, out, h = 1;
			DictionaryADT tmp = null;
			int size = array.length;
			while (h <= currentCapacity / 3) // Calculate gaps
				h = h * 3 + 1;
			while (h > 0) {
				for (out = h; out < size; out++) {
					tmp = array[out];
					in = out;

					while (in > h - 1 && array[in - h].compareTo(tmp) >= 0) {
						array[in] = array[in - h];
						in -= h;
					}
					array[in] = tmp;
				}
				h = (h - 1) / 3;
			}
			return array;
		}
	}

	// Referenced this Iterator in Prof. Riggins Notes
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
			return (K) nodes[index++].key;
		}

	}

	// Referenced this Iterator in Prof. Riggins Notes
	@SuppressWarnings("hiding")
	class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}

		@SuppressWarnings("unchecked")
		public V next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (V) nodes[index++].value;
		}
	}

}