package smartjune;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class ExampleSort {

	public static void sort(Comparable[] a) {
		System.out.println("This a method defined in Super class ExampleSort, but "
				+ "Super class didnot implement it.");
	}
	
	static boolean less(Comparable v, Comparable w)
	{ return v.compareTo(w) < 0; }
	
	static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
	}
	
	public static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(i, i - 1))
				return false;
		return true;
	}



}
