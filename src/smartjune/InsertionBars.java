package smartjune;

import java.util.Arrays;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class InsertionBars extends ExampleSort {

	public InsertionBars() {
	}

	public static void sort(Comparable[] a) {

		int N = a.length;
		double[] d = new double[N];
		// i 本来可以从 1 取起，这里是为了显示插入排序第一步，即 i ＝ 0 的情形
		for (int i = 0; i < N; i++) {			
			int position = i;
			
			if (i != 0) {
				// find position
				for (int j = i - 1; j >= 0 && less(a[i], a[j]); j--)
					position = j;

				// insert there
				Comparable temp = a[i];
				for (int k = i; k > position; k--) {
					a[k] = a[k - 1];
				}
				a[position] = temp;				
			}
			
			
			for (int s = 0; s < N; s++) {
				char ch = ((String)a[s]).charAt(0);
				int x = ch - 'A' + 1;
				d[s] = (ch - 'A' + 1) / 27.0;
			}
			
			show(d, i, position);
		}

	}

	private static void show(double[] d, int i, int position) {
		StdDraw.setYscale(-d.length + i + 2, i + 1);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int k = 0; k < position; k++)
			StdDraw.line(k, 0, k, d[k] * .5);
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.line(position, 0, position, d[position] * .6);
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int k = position + 1; k <= i; k++)
			StdDraw.line(k, 0, k, d[k] * .5);
		StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
		for (int k = i + 1; k < d.length; k++)
			StdDraw.line(k, 0, k, d[k] * .5);
	}

	/*
	 * 设置canvas; 排序的对象是字符串，这里预先将其处理为数字
	 */
	private static void setCanvas(Comparable[] a, int N) {
        StdDraw.setCanvasSize(320, 640);
        StdDraw.setXscale(-1, N+1);
        StdDraw.setPenRadius(.006);
	}
	
	public static void main(String[] args) {
		InsertionBars ins = new InsertionBars();
		String[] a = new In().readAllStrings();
		
		setCanvas(a, a.length);

		ins.sort(a);
		assert isSorted(a);

		show(a);

	}

}
