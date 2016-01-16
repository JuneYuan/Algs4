package smartjune;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class Insertion extends ExampleSort {

	public Insertion() {
	}

	public static void sort(Comparable[] a) {
		
		int N = a.length;
		for (int i = 1; i < N; i++) {
			
			// find position
			int position = i;
			for (int j = i - 1; j >= 0 && less(a[i], a[j]); j--)
				position = j;
			
			// insert there
			Comparable temp = a[i];
			for (int k = i; k > position; k--) {
				a[k] = a[k - 1];
			}
			a[position] = temp;	
			
		}

	}	

	public static void main(String[] args) {
		String[] a = new In().readAllStrings();
		
		Insertion.sort(a);
		assert isSorted(a);
		
		show(a);
	}

}
