package imitate.fundamentals;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {

	// Brute-force: check each triple
	public static int count_brute(int[] a) {
		int N = a.length;
		int count = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					if (a[i] + a[j] + a[k] == 0)
						count++;
				}
			}
		}
		
		return count;
	}
	
	// DP
	public static int count_dp(int[] a) {
		
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		int[] a = in.readAllInts();
		
		Stopwatch timer = new Stopwatch();
		StdOut.println(ThreeSum.count_brute(a));
		StdOut.println("elapsed time = " + timer.elapsedTime());
	}
}
