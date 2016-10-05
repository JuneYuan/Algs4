package smartjune;

public interface StackI<Item> {
	
	public void push(Item item);
	
	public Item pop();
	
	public boolean isEmpty();
	
	public int size();

}
