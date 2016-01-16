package smartjune;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickFindUF extends UFA  {
	
	private int[] id;
	private int count;

	public QuickFindUF(int N) {
		super(N);
	}

	@Override
	public void union(int p, int q) {
		if (!connected(p, q)) {
			// add one to another's set
			int pID = id[p];
			int qID = id[q];
			if (pID == qID)		return;  // 👀
			
			// 这里千万不能是 for (int i : id) !
			for (int i = 0; i < id.length; i++) {
				if (id[i] == pID)		id[i] = qID;
			}
			
			count--;  // 👀
		}
	}
	
	public int unionWithCostReturned(int p, int q) {
		if (!connected(p, q)) {
			int cost = 2;
			
			// add one to another's set
			int pID = id[p];
			int qID = id[q];
			if (pID == qID)		return cost;  // 👀
			
			// 这里千万不能是 for (int i : id) !
			for (int i = 0; i < id.length; i++) {
				if (id[i] == pID) {
					id[i] = qID;
					cost++;
				}
				cost++;
			}
			
			count--;  // 👀
			return cost;
		}
		return 0;
	}

	@Override
	public int find(int p) {
		return id[p];
	}

	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	@Override
	public int count() {
		return count;
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		QuickFindUF uf = new QuickFindUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " " + q);
			}
		}
		StdOut.println(uf.count() + " components");
	}

}
