package assignments.queues;

import edu.princeton.cs.algs4.StdIn;

// Unit testing for RandomizedQueue
public class Subset {
	
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> ranque = new RandomizedQueue<>();
		
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			ranque.enqueue(s);
		}
		
		for (int i = 0; i < k; i++) {
			System.out.println(ranque.dequeue());
		}
/*
for (String item : ranque) {
	System.out.println(item);
}
*/
	}
}
