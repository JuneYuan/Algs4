package assignments.kdTrees;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	private SET<Point2D> allPoints;

	// Construct an empty set of points
	public PointSET() {
		allPoints = new SET<>();
	}
	
	// Is the set empty?
	public boolean isEmpty() {
		return allPoints.isEmpty();
	}
	
	// Nubmer of points in the set
	public int size() {
		return allPoints.size();
	}
	
	// Add the point to the set (if it is not already in the set)
	public void insert(Point2D p){
		if (p == null)  throw new java.lang.NullPointerException();
		allPoints.add(p);
	}
	
	// Does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)  throw new java.lang.NullPointerException();
		return allPoints.contains(p);
	}
	
	// Draw all points to standard draw
	public void draw() {
		for (Point2D point : allPoints) {
			point.draw();
		}
	}
	
	// All points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)  throw new java.lang.NullPointerException();
		
		List<Point2D> results = new ArrayList<>();
		for (Point2D p : allPoints) {
			if (point2Rectangle(p, rect) < Math.pow(10, -7)) {
				results.add(p);
			}
		}
		return results;
	}
	
	private double point2Rectangle(Point2D p, RectHV rect) {
		boolean pInXrange = (p.x() >= rect.xmin() && p.x() <= rect.xmax());
		boolean pInYrange = (p.y() >= rect.ymin() && p.y() <= rect.ymax());
		
		if (pInXrange && pInYrange) {
			return 0;
		} else if (pInXrange) {
			double a = Math.abs(p.y() - rect.ymin());
			double b = Math.abs(p.y() - rect.ymax());
			return Math.min(a, b);
		} else if (pInYrange) {
			double a = Math.abs(p.x() - rect.xmin());
			double b = Math.abs(p.x() - rect.xmax());
			return Math.min(a, b);			
		} else {
			double a1 = p.distanceTo(new Point2D(rect.xmin(), rect.ymin()));
			double a2 = p.distanceTo(new Point2D(rect.xmax(), rect.ymin()));
			double b1 = p.distanceTo(new Point2D(rect.xmin(), rect.ymax()));
			double b2 = p.distanceTo(new Point2D(rect.xmax(), rect.ymax()));
			/*
			double a1 = Math.pow(p.x() - rect.xmin(), 2) + Math.pow(p.y() - rect.ymin(), 2);
			double a2 = Math.pow(p.x() - rect.xmax(), 2) + Math.pow(p.y() - rect.ymin(), 2);
			double b1 = Math.pow(p.x() - rect.xmin(), 2) + Math.pow(p.y() - rect.ymax(), 2);
			double b2 = Math.pow(p.x() - rect.xmax(), 2) + Math.pow(p.y() - rect.ymax(), 2);
			*/
			double answer = Math.min(Math.min(a1, a2), Math.min(b1, b2));
			return answer;
		}
	}

	// A nearest neighbor in the set to point p; null if the set if empty
	public Point2D nearest(Point2D p) {
		if (p == null)  throw new java.lang.NullPointerException();
		
		Point2D result = null;
		for (Point2D q : allPoints) {
			if (result == null || p.distanceTo(q) < p.distanceTo(result)) {
				result = q;
			}
		}
		return result;
	}
	
	// Unit testing (optional)
	public static void main(String[] args) {
		
	}
}
