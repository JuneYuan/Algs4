package smartjune;

import edu.princeton.cs.algs4.In;

public class Selection extends ExampleSort {

	public Selection() {
		// TODO Auto-generated constructor stub
	}

	public static void sort(Comparable[] a) {
		int N = a.length;
		
		for (int k = 0; k < N; k++) {
			int min = k;
			
			for (int i = k + 1; i < N; i++) {
				// if (less(a[i], a[k]))
				if (less(a[i], a[min]))
					min = i;
			}
			
			exch(a, k, min);
		}
	}
	


	
	public static void main(String[] args) {
		Selection sel = new Selection();		
		String[] a = new In().readAllStrings();
		
		sel.sort(a);
		assert isSorted(a);
		
		show(a);
	}
}
