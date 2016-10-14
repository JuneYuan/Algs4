package assignments.queues;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] a;
	private int n;
	
	// Construct an empty randomized queue
	public RandomizedQueue() {
		a = (Item[]) new Object[1];
		n = 0;
	}
	
	// Is the queue empty?
	public boolean isEmpty() {
		return n == 0;
	}
	
	// Return the number of items on the queue
	public int size() {
		return n;
	}
	
	// Add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.NullPointerException();
		}
		
		if (n == a.length) {
			resize(2 * a.length);
		}
		a[n++] = item;		
	}

	// Remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		
		int k = StdRandom.uniform(n);
		swap(k, n - 1);
		Item item = a[--n];
		a[n] = null;	// avoid memory loitering
		return item;
	}
	
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		System.arraycopy(a, 0, copy, 0, n);
		a = copy;
	}

	private void swap(int p, int q) {
		Item tmp = a[p];
		a[p] = a[q];
		a[q] = tmp;
	}
	
	// Return (but do not remove) a random item
	public Item sample() {
		if (isEmpty()) {
			throw new java.util.NoSuchElementException();
		}
		
		int k = StdRandom.uniform(n);
		Item item = a[k];
		return item;
	}
	
	// Return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<Item> {
		private Item[] copy = (Item[]) new Object[n];
		private int cnt = 0;
		
		public MyIterator() {
			System.arraycopy(a, 0, copy, 0, n);
			StdRandom.shuffle(copy);
/*
System.out.println("======RandomizedQueue.a = " + Arrays.toString(a));
System.out.println("======MyIterator.copy = " + Arrays.toString(copy));
*/
		}
		
		public boolean hasNext() {
			return cnt < n;
		}
		
		public Item next() {
			if (!hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			
			return copy[cnt++];
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
	
	// Unit testing
	public static void main(String[] args) {
		
	}
}
