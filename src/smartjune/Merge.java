package smartjune;

public class Merge {
	private static final int CUTOFF = 7;
	
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
		assert isSorted(a);
	}

	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		/* Improvement 1: use insertion sort for small subarrays */
		if (hi <= lo + CUTOFF - 1) {	// rather than "hi <= lo"
			Insertion.sort(a, lo, hi);
			return;
		}
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		/* Improvement 2: stop if already sorted */
		if (!less(a[mid + 1], a[mid]))	return;
		merge(a, aux, lo, mid, hi);
	}
	
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		assert isSorted(a, lo, mid);	  // precondition: a[lo..mid] sorted
		assert isSorted(a, mid + 1, hi);  // precondition: a[mid+1..hi] sorted
		
		/*
		 *  Improvement 3: Eliminate copy from a[] to aux[]
		 *  by switching the role of input[] and aux[] in each recursive call
		 *  (NOT quite understand yet...)
		 */
		System.arraycopy(a, lo, aux, lo, hi - lo + 1);
		
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)		a[k] = aux[j++];
			else if (j > hi)	a[k] = aux[i++];
			else if (less(aux[j], aux[i]))	a[k] = a[j++];  // most common cases
			else 				a[k] = a[i++];
		}
		
		assert isSorted(a, lo, hi);		 // postcondition: a[lo..hi] sorted
	}
	
	/* Below are helper functions */
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}
	
	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo; i < hi; i++) {
			if (less(a[lo + 1], a[lo]))  return false;
		}		
		return true;
	}

}
