package smartjune;

public abstract class UFA {
	int[] id;
	int count;

	public UFA(int N) {
		count = N;
		id = new int[N];
		for (int i = 0; i < N; i++)
			id[i] = i;
	}
	
	public abstract void union(int p, int q);
	public abstract int find(int p);
	
	public boolean connected(int p, int q)
	{ return find(p) == find(q); }
	
	public int count()
	{ return count; }
	

}
