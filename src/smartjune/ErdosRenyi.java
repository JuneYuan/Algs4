package smartjune;
/*
 * 测试数据时，存在不该 union 的 union(), 和没有完成连通的情形
 * 原因：UF.java 的 union() 没写对， 
 * "for (int i = 0; i < id.length; i++)" // 遍历下标
 * 写成了
 * "for (int i : id)" // 遍历数组成员
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class ErdosRenyi {
	
	public static int count(int N) {
		QuickFindUF uf = new QuickFindUF(N);
		int cnt = 0;  // connections generated
		while (uf.count() > 1) {
			int p = StdRandom.uniform(0, N);
			int q;
			do {
			q = StdRandom.uniform(0, N);
			} while (p == q);
			cnt++;
System.out.println("generated: " + p + ", " + q);			
			
			if (!uf.connected(p, q))
				{ uf.union(p, q); StdOut.println("union"); }
System.out.println("uf.count() = " + uf.count());			
		}
		return cnt;
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		StdOut.println(count(N));
	}

}
