package smartjune;

public class WeightedQuickUnionUF {
	private int[] id;	// identifier of each component
	private int[] sz;	// size of each component
	
	public WeightedQuickUnionUF(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}
	
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
	public void union(int p, int q) {
		int i = root(p);
		int j = root(q);
		if (i == j) {
			return;
		}
		// balance by linking root of smaller tree to root of larger tree
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
	}
	
	private int root(int i) {
		while (i != id[i]) {
			// path compression improvement
			// id[i] = id[id[i]];
			i = id[i];
		}
		
		return i;
	}
}
