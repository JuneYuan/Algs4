package imitate.sorting;

import edu.princeton.cs.algs4.StdRandom;

public class Quick {

	public static void sort(Comparable[] a) {
		// shuffle needed for performance guarantee
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo)  return;
		
		int j = partition(a, lo, hi);
		sort(a, lo, j - 1);
		sort(a, j + 1, hi);
	}

	private static int partition(Comparable[] a, int lo, int hi) {
		int i = lo + 1;
		int j = hi;
		while (true) {
			while (less(a[i], a[lo])) {
				i++;
				if (i == hi)  break;
			}
			while (less(a[lo], a[j])) {
				j--;
				if (j == lo)  break;  // redundant
			}
			
			if (i >= j)  break;  // WATCH 👀
		}
		
		exch(a, lo, j);
		return j;
	}

	/****** Helper funcitons ******/
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
}
