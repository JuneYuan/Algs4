package smartjune;

public interface UFI {
	// public UF(int N);
	public void union(int p, int q);
	public int find(int p);
	public boolean connected(int p, int q);
	public int count();
}
