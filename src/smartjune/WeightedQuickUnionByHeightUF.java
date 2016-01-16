package smartjune;
/*
 * 增加了 componentSize 变量；
 * 能否换成 componentTreeHeight 呢？
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionByHeightUF extends UFA {
	private int[] componentTreeHeight;

	public WeightedQuickUnionByHeightUF(int N) {
		super(N);
		
		componentTreeHeight = new int[N];  // inited to 0
		for (int i = 0; i < componentTreeHeight.length; i++)
			componentTreeHeight[i] = 1;
	}

	@Override
	public void union(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if (pRoot == qRoot)		
			return; 
		/*
		 * 这里有几种情况，即 p 和 q 的树高分别为：
		 * 1 ~ 1; 1 ~ n; n ~ 1; n ~ n
		 * 其中 n ~ 1 对应 if, 其余三种情况对应 else,
		 * 再加以归并，可以作如下处理
		 */
		if (componentTreeHeight[pRoot] < componentTreeHeight[qRoot]) {
			id[pRoot] = qRoot;
			unionHelper(qRoot, pRoot);
StdOut.printf("componentTreeHeight[%d] = %d\n", qRoot, componentTreeHeight[qRoot]);			
		} else {
			id[qRoot] = pRoot;
			if (p == pRoot && q == qRoot)
				componentTreeHeight[pRoot]++;
			else
				unionHelper(pRoot, qRoot);
StdOut.printf("componentTreeHeight[%d] = %d\n", pRoot, componentTreeHeight[pRoot]);
		}
		count--;
	}
	// 维护 componentTreeHeight
	private void unionHelper(int parentNode, int childNode) {
		if (componentTreeHeight[parentNode] >= componentTreeHeight[childNode] + 1)
			;
		else
			componentTreeHeight[parentNode] = componentTreeHeight[childNode] + 1;
	}

	@Override
	public int find(int p) {
		while (p != id[p])
			p = id[p];
		return p;
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		WeightedQuickUnionByHeightUF uf = new WeightedQuickUnionByHeightUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				StdOut.println(p + " " + q);
				uf.union(p, q);
			}
		}
		StdOut.println(uf.count() + " components");
	}

}
