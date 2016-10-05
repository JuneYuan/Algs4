package smartjune;

public class Queue<Item> implements QueueI {
	private Node head, tail;
	private int N;
	
	private class Node {
		Item item;
		Node next;
	}

	@Override
	public void enqueue(Object item) {
		Node oldtail = tail;
		tail = new Node();
		tail.item = (Item) item;
		oldtail.next = tail;
		N++;
	}

	public Item dequeue() {
		Item ret = head.item;
		head = head.next;
		N--;
		return ret;
	}

	@Override
	public boolean isEmpty() {
		return N == 0;
	}

	@Override
	public int size() {
		return N;
	}
	
}
