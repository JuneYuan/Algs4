package smartjune;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionUF extends UFA {
	int costOfFind;
	
	public QuickUnionUF(int N) {
		super(N);
	}

	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)		
			return; 
		id[pRoot] = qRoot;
		count--;
	}

	@Override
	public int find(int p) {
		costOfFind = 1;
		while (p != id[p]) {
			p = id[p];
			costOfFind += 1;
		}			
		return p;
	}
	
	public boolean connected(int p, int q) {
		int findP = find(p);
		int t = costOfFind;
		int findQ = find(q);
		costOfFind += t;
		return findP == findQ;
		
		// return find(p) == find(q);
	}
	
	public int unionWithCostReturned(int p, int q) {
		int pRoot = find(p);
		int t = costOfFind;
		
		int qRoot = find(q);
		costOfFind += t;
		
		if (pRoot == qRoot)		
			return costOfFind;
		
		id[pRoot] = qRoot;
		count--;
		
		return costOfFind;
	}

	

	public static void main(String[] args) {
		int N = StdIn.readInt();
		QuickUnionUF uf = new QuickUnionUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " " + q);
			}
		}
		StdOut.println(uf.count() + " components");
		
		StdOut.println(Arrays.toString(uf.id));
	}

}
