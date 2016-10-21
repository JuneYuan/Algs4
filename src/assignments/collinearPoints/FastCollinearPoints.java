package assignments.collinearPoints;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] lines;	// all lines patterns that recognized
	private int n;		// points number
	private int m;		// lines number (at most)
	private int cnt;	// actual lines count	
	
	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		this.points = points;
		// Corner cases		
		checkForNullOrDuplicates(points);
		
		// Main task
		n = points.length;
		m = n * (n - 1) / 2;
		lines = new LineSegment[m];
		cnt = 0;
		
		for (Point p : points) {
			// find lines starting with point p
			System.out.println("Base p: " + p);
			helper(p);
		}
	}

	private void checkForNullOrDuplicates(Point[] points) {
		if (points == null)  throw new java.lang.NullPointerException();
		try {
			Arrays.sort(points);
			for (int i = 0; i < n - 1; i++) {
				if (points[i].compareTo(points[i + 1]) == 0) {
					throw new java.lang.IllegalArgumentException();
				}
			}
		} catch (java.lang.NullPointerException e) {
			throw e;
		}
	}
	
	private void helper(Point p) {
		if (p.compareTo(new Point(20000, 21000)) == 0) {
			System.out.print(" ");
		}
		
		Point[] copy = Arrays.copyOf(points, n);
		Arrays.sort(copy, p.slopeOrder());

        int idx = 1;  // index of slopes: iterating through 1..n-1
        int pos = 1;  // index of current continuous&equivalent slope values (keep growing)
        int len = 0;  // length of current continuous&equivalent slope values (set to 0 once in a while)
        while (true) {
        	if (idx >= n)  break;
        	
        	double slopeP2Curr = p.slopeTo(copy[idx]);
        	double slopeP2Pos = p.slopeTo(copy[pos]);
        	double deltaSlope = Math.abs(slopeP2Curr - slopeP2Pos);
        	boolean drop = false;  // drop when p is not the start of line
        	while (deltaSlope < Math.pow(10, -5)) {
        		if (p.compareTo(copy[idx]) > 0) {  //WRONGED
        			drop = true;
        		}
        		
        		idx++;
        		len++;

        		if (idx >= n)  break;
        		
        		slopeP2Curr = p.slopeTo(copy[idx]);
        		deltaSlope = Math.abs(slopeP2Curr - slopeP2Pos);
        	}
        	
        	// finds a line whose length>=4 AND p is the start point
        	if (len >= 3 && !drop) {
        		// copy[pos..pos+len-1] same slope
            	Point end = copy[pos];
System.out.print("line: " + p + "->");
        		for (int i = pos; i < pos + len; i++) {
System.out.print(copy[i] + "->");
        			if (end.compareTo(copy[i]) < 0) {
        				end = copy[i];
        			}
        		}
System.out.println();
    			lines[cnt++] = new LineSegment(p, end);
        	}
        	
        	// for next line segment
        	pos += len;
        	len = 0;
        }
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
System.out.println("done");
	}

}
