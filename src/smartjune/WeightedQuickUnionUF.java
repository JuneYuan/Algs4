package smartjune;
/*
 * 增加了 componentSize 变量；
 * Q1. 能否换成 componentTreeHeight 呢？
 * A1. 可以，1.5.14 就提到，见 WeightedQuickUnionByHeightUF
 * Q2. 换成 componentTreeHeight, 效果会不会明显地好呢？
 * 
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF extends UFA {
	private int[] componentSize;

	public WeightedQuickUnionUF(int N) {
		super(N);
		
		componentSize = new int[N];
		for (int i = 0; i < componentSize.length; i++)
			componentSize[i] = 1;
	}

	@Override
	/*
	 * p act as Father Node by default
	 */
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)		
			return; 
		if (componentSize[pRoot] < componentSize[qRoot]) {
			id[pRoot] = qRoot;
			componentSize[qRoot] += componentSize[pRoot];
		} else {
			id[qRoot] = pRoot;
			componentSize[pRoot] += componentSize[qRoot];
		}
		count--;
	}

	@Override
	public int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
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
