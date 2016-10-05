package smartjune;

public class LinkedListStack<Item> implements StackI {
	private Node first;
	private int N;
	
	private class Node {
		Item item;
		Node next;
	}
	
	@Override
	public void push(Object item) {
		Node oldfirst = first;
		first = new Node();
		first.item = (Item) item;
		first.next = oldfirst;
		N++;
	}

	public Item pop() {
		Item ret = first.item;
		first = first.next;
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
