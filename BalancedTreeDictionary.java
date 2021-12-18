/**
     *  Program # 4: Balanced Tree Dictionary
     *  Using a implementation of Dictionary ADT. For using an ADT, this can handle larger data set than the previous programs
     *  CS310-01
     *  May 02, 2020
     *  @author Ariel Pingol cssc1259
     */

package data_structures;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

public class BalancedTreeDictionary<K, V> implements DictionaryADT<K, V> {
	TreeMap<K, V> treemap;
	K key;

	public BalancedTreeDictionary() {
		treemap = new TreeMap<K, V>();
		key = null;
	}

	// Adds the given key/value pair to the dictionary. Returns
	// false if the dictionary is full, or if the key is a duplicate.
	// Returns true if addition succeeded.
	public boolean put(K key, V value) {
		if (treemap.containsKey(key)) {
			return false;
		} else {
			treemap.put(key, value);
		}
		return true;
	}

	// Deletes the key/value pair identified by the key parameter.
	// Returns true if the key/value pair was found and removed,
	// otherwise false.
	public boolean delete(K key) {
		if (treemap.remove(key) == null) {

			return false;

		} else if (treemap.remove(key) != null) {

		}
		return true;
	}

	// Returns the value associated with the parameter key. Returns
	// null if the key is not found or the dictionary is empty.
	public V get(K key) {

		if (!treemap.containsKey(key) || isEmpty()) {
			return null;
		} else {
			return treemap.get(key);
		}
	}

	// Returns the key associated with the parameter value. Returns
	// null if the value is not found in the dictionary. If more
	// than one key exists that matches the given value, returns the
	// first one found.
	@SuppressWarnings("unchecked")
	public K getKey(V value) {

		for (Map.Entry<K, V> entry : treemap.entrySet()) {
			if (((Comparable<V>) value).compareTo(entry.getValue()) == 0) {
				return entry.getKey();
			}
		}
		return null;

	}

	// Returns the number of key/value pairs currently stored
	// in the dictionary
	public int size() {
		return treemap.size();
	}

	// Returns true if the dictionary is full
	public boolean isFull() {
		return false;
	}

	// Returns true if the dictionary is empty
	public boolean isEmpty() {
		return treemap.isEmpty();
	}

	// Makes the dictionary empty
	public void clear() {
		treemap.clear();
	}

	// Returns an Iterator of the keys in the dictionary, in ascending
	// sorted order. The iterator must be fail-fast.
	public Iterator<K> keys() {
		return treemap.keySet().iterator();
	}

	// Returns an Iterator of the values in the dictionary. The
	// order of the values must match the order of the keys.
	// The iterator must be fail-fast.
	public Iterator<V> values() {
		return treemap.values().iterator();
	}

}