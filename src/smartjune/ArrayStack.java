package smartjune;

public class ArrayStack<Item> implements StackI{
	private Item[] a;
	private int N;
	
	public ArrayStack() {
		a = (Item[]) new Object[1];  // Java不支持范型数组，需写成Object[]再做类型转换
		N = 0;
	}

	@Override
	public void push(Object item) {
		if (N == a.length)
			resize(2 * a.length);
		a[N++] = (Item) item;
	}

	@Override
	public Item pop() {
		Item item = a[--N];
		a[N] = null;  //避免内存泄漏
		if (N == a.length/4)
			resize(a.length / 2);		
		return item;
	}

	@Override
	public boolean isEmpty() {
		return N == 0;
	}

	@Override
	public int size() {
		return N;
	}

	private void resize(int n) {
		Item[] temp = (Item[]) new Object[n];
		for (int i = 0; i < n; i++)
			temp[i] = a[i];
		a = temp;
	}
	
}
