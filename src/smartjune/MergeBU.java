package smartjune;

public class MergeBU {
	public static void sort(Comparable[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		/*
		 *  sz代表已有序分组的长度，起初仅每个元素单独有序，
		 *  一轮遍历后2个一组有序，再一轮遍历后4个一组有序，以此类推……
		 */
		for (int sz = 1; sz < N; sz *= 2) {
			for (int lo = 0; lo + sz < N; lo += (sz * 2)) {
				merge(a, aux, lo, lo + sz - 1, Math.max(lo + sz + sz - 1,  N - 1));
			}
		}
	}

	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		assert isSorted(a, lo, mid);	  // precondition: a[lo..mid] sorted
		assert isSorted(a, mid + 1, hi);  // precondition: a[mid+1..hi] sorted
		
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
