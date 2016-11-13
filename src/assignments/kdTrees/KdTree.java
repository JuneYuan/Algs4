package assignments.kdTrees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
	private TreeNode root;
	private int size;
	private HashSet<Point2D> allPoints;

	private static class TreeNode {
		Point2D point;
		RectHV rect;
		TreeNode left, right;
		boolean isVertical = true;
		
		public TreeNode(Point2D point, RectHV rect, 
						TreeNode left, TreeNode right, 
						boolean isVertical) {
			this.point = point;
			this.rect = rect;
			this.left = left;
			this.right = right;
			this.isVertical = isVertical;
		}
	}
	
	// Construct an empty set of points
	public KdTree() {
		root = null;
		size = 0;
		allPoints = new HashSet<>();
	}
	
	// Is the set empty?
	public boolean isEmpty() {
		return size == 0;
	}
	
	// Nubmer of points in the set
	public int size() {
		return size;
	}
	
	// Add the point to the set (if it is not already in the set)
	public void insert(Point2D p){
		if (p == null)  throw new java.lang.NullPointerException();
		
		if (contains(p))  return;
		
		TreeNode node = root;
		while (true) {
			TreeNode child = sameSideChildOfNodeWithP(node, p);
			if (child.point == null) {
				child.point = p;
				break;
			}
			
			node = child;
		}
		allPoints.add(p);
		size++;
	}
	
	// Does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)  throw new java.lang.NullPointerException();
		return allPoints.contains(p);
	}
	
	// Draw all points to standard draw
	public void draw() {
		
	}
	
	// All points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)  throw new java.lang.NullPointerException();
		List<Point2D> results = new ArrayList<>();
		dfsRange(root, rect, results);
		
		return results;
	}
	
	private void dfsRange(TreeNode node, RectHV queryRect, List<Point2D> results) {		
		if (node == null)  return;
		
		collect(node, queryRect, results);
		
		if (intersect(node.left.rect, queryRect)) {
			dfsRange(node.left, queryRect, results);
		}
		if (intersect(node.right.rect, queryRect)) {
			dfsRange(node.right, queryRect, results);
		}
	}
	
	private boolean intersect(RectHV rect1, RectHV rect2) {
		if (rect1 == null || rect2 == null)  return false;
		
		// consider the 4 edges of rectangle formed by intersection
		double left = Math.max(rect1.xmin(), rect2.xmin());
		double right = Math.min(rect1.xmax(), rect2.xmax());
		double top = Math.min(rect1.ymax(), rect2.ymax());
		double bottom = Math.max(rect1.ymin(), rect2.ymin());
		if (right <= left || top <= bottom)  return false;
		
		return true;
	}
	
	private void collect(TreeNode node, RectHV rect, List<Point2D> results) {
		double distance = point2Rectangle(node.point, rect);
		if (distance < Math.pow(10, -5)) {
			results.add(node.point);
		}
	}
	
	private double point2Rectangle(Point2D p, RectHV rect) {
		if (rect == null)  throw new java.lang.IllegalArgumentException("rect is null!");
		
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
		
		Point2D result = root.point;
		dfsNeighbor(root, p, result);
		return result;
	}
	
	private void dfsNeighbor(TreeNode node, Point2D p, Point2D result) {
		if (node == null || node.point == null)  return;
		
		double dist = p.distanceTo(node.point);
		if (dist < p.distanceTo(result)) {
			result = node.point;
		}
		
		TreeNode sameSide = sameSideChildOfNodeWithP(node, p);
		TreeNode diffSide = (sameSide == node.left) ? node.right : node.left;
		dfsNeighbor(sameSide, p, result);
		if (diffSide != null && point2Rectangle(p, diffSide.rect) < p.distanceTo(result)) {
			dfsNeighbor(diffSide, p, result);
		}
	}
	
	private TreeNode sameSideChildOfNodeWithP(TreeNode node, Point2D p) {
		TreeNode child;
		if (node.isVertical) {
			child = (p.x() < node.point.x()) ? node.left : node.right;
		} else {
			child = (p.y() < node.point.y()) ? node.left : node.right;
		}
		if (child != null)  return child;

		// if child doesn't exist, we fill it with a node of proper rectangle and NULL point 
		double xmin = node.rect.xmin();
		double ymin = node.rect.ymin();
		double xmax = node.rect.xmax();
		double ymax = node.rect.ymax();
		if (node.isVertical && p.x() < node.point.x())    xmax = node.point.x();
		if (node.isVertical && p.x() >= node.point.x())   xmin = node.point.x();
		if (!node.isVertical && p.y() < node.point.y())   ymax = node.point.y();
		if (!node.isVertical && p.y() >= node.point.y())  ymin = node.point.y();
		RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
		child = new TreeNode(null, rect, null, null, !node.isVertical);
		
		return child;
	}
	
	// Unit testing (optional)
	public static void main(String[] args) {
		
	}
}
