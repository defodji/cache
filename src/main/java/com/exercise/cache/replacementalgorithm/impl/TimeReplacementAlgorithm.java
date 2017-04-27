package com.exercise.cache.replacementalgorithm.impl;

import java.util.HashMap;
import java.util.Map;

import com.exercise.cache.replacementalgorithm.AccessType;
import com.exercise.cache.replacementalgorithm.CacheReplacementAlgorithm;

/**
 * Class describing common behaviour for cache replacement algorithms that need
 * a timestamp to the last access to the cache set. MRU and LRU are timestamp
 * replacement algorithms.
 * 
 * @param <E>
 */
public abstract class TimeReplacementAlgorithm<K>
		implements CacheReplacementAlgorithm<K> {
	protected Map<K, Node<K>> entryTimestamps = new HashMap<>();
	protected Node<K> head = null;
	protected Node<K> tail = null;

	@Override
	public void notifyCacheAccess(K entryKey, AccessType accessType) {
		if (accessType == AccessType.WRITE) {
			Node<K> newNode = new Node<>(entryKey);
			setHead(newNode);
			entryTimestamps.put(entryKey, newNode);
		} else if (accessType == AccessType.READ) {
			Node<K> old = entryTimestamps.get(entryKey);
			synchronized (this) {
				remove(old);
				setHead(old);
			}
		} else { // removal
			notifyRemoval();
		}
	}
	
    protected void remove(Node<K> node){
        // The only one item
        if (this.head.equals(this.tail) && this.head.equals(node)) {
            this.head = null;
            this.tail = null;
            return;
        }
     
        // Remove from head
        if (node.equals(this.head)) {
            this.head.next.previous = null;
            this.head = this.head.next;
            return;
        }
     
        // Remove from end
        if (node.equals(this.tail)) {
            this.tail.previous.next = null;
            this.tail = this.tail.previous;
            return;
        }
     
        // Remove in the middle
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }
	
    protected void setHead(Node<K> newNode){
    	newNode.next = head;
    	newNode.previous = null;
 
        if (head!=null) {
            head.previous = newNode;
        }
        head = newNode;
 
        if (tail == null) {
            tail = head;
        }
    }	
	
	protected abstract void notifyRemoval();
	
	static class Node<K> {
	    K key;
	    Node<K> previous;
	    Node<K> next;
	 
	    public Node(K key){
	        this.key = key;
	    }

		public Node<K> getPrevious() {
			return previous;
		}

		public Node<K> getNext() {
			return next;
		}

		public void setPrevious(Node<K> previous) {
			this.previous = previous;
		}

		public void setNext(Node<K> next) {
			this.next = next;
		}
		
		public K getKey() {
			return key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node<K> other = (Node<K>) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}
		
	}
}
