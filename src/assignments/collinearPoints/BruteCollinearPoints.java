package assignments.collinearPoints;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
6
19000 10000
18000 10000
32000 10000
21000 10000
 1234  5678
14000 10000

8
 7000  3000
10000     0
    0 10000
 3000  7000
20000 21000
 3000  4000
14000 15000
 6000  7000
 */
public class BruteCollinearPoints {
	private LineSegment[] lines;	// all lines patterns that recognized
	private int n;		// points number
	private int m;		// lines number (at most)
	private int cnt;	// actual lines count

	public BruteCollinearPoints(Point[] points) {
		n = points.length;
		m = n * (n - 1) / 2;
		lines = new LineSegment[m];
		cnt = 0;
		
		Point[] copy = Arrays.copyOf(points, points.length);		
		// Corner cases
		checkForNullOrDuplicates(copy);
		// Iterate through all combinations of 4 points (N choose 4) and test
		Arrays.sort(copy);
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					for (int t = k + 1; t < n; t++) {
						if (areCollinear(copy[i], copy[j], copy[k], copy[t])) {
							lines[cnt++] = new LineSegment(copy[i], copy[t]);
// System.out.println(copy[i] + "->" + copy[j] + "->" + copy[k] + "->" + copy[t]);
							break;  // the problem spec ensures no more than 4 points in a line
						}
					}
				}
			}
		}
	}
	
	private void checkForNullOrDuplicates(Point[] points) {
		if (points == null)  throw new java.lang.NullPointerException();
		try {
			Arrays.sort(points);
			for (int i = 0; i < points.length - 1; i++) {
				if (points[i].compareTo(points[i + 1]) == 0) {
					throw new java.lang.IllegalArgumentException();  // duplicate point
				}
			}			
		} catch (java.lang.NullPointerException e) {
			throw new java.lang.NullPointerException();
		}
	}
	
	private boolean areCollinear(Point p0, Point p1, Point p2, Point p3) {
		double slope1 = p1.slopeTo(p0);
		double slope2 = p2.slopeTo(p0);
		double slope3 = p3.slopeTo(p0);
		boolean verticalCollinear = (slope1 == Double.POSITIVE_INFINITY
				&& slope2 == Double.POSITIVE_INFINITY
				&& slope3 == Double.POSITIVE_INFINITY);
		boolean nonverticalCollinear = (Math.abs(p2.slopeTo(p0) - p1.slopeTo(p0)) < Math.pow(10, -5)
				&& Math.abs(p3.slopeTo(p0) - p1.slopeTo(p0)) < Math.pow(10, -5));
		
		return (verticalCollinear || nonverticalCollinear);
	}
	
	public int numberOfSegments() {
		return cnt;
	}
	
	public LineSegment[] segments() {
		return Arrays.copyOf(lines, cnt);
	}

	public static void main(String[] args) {
		// read the n points from standard input
		int n = StdIn.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = StdIn.readInt();
			int y = StdIn.readInt();
			points[i] = new Point(x, y);
		}
		
		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
