package assignments.kdTrees;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private TreeNode root;
	private int size;
	private SET<Point2D> allPoints;

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
		allPoints = new SET<>();
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
			
			if (child == null) {  // insert (the first node, i.e. root)
				RectHV rect = new RectHV(0, 0, 1, 1);
				root = new TreeNode(p, rect, null, null, true);
				break;
			}
			
			if (child.point == null) {  // insert (some node below the root)
				child.point = p;
				break;
			}
			
			node = child;		// going to the insert position
		}
		allPoints.add(p);
		size++;
	}
	
	// return the child of node that is on the same side with p
	private TreeNode sameSideChildOfNodeWithP(TreeNode node, Point2D p) {
		if (node == null)  return null;
		
		TreeNode child;
		if (less(p, node)) {
			child = node.left;
		} else {
			child = node.right;
		}
		if (child != null)  return child;

		// if child doesn't exist, we fill it with a node of proper rectangle and NULL point
		RectHV rect = sameSideChildRectangle(p, node);
		child = new TreeNode(null, rect, null, null, !node.isVertical);
		if (less(p, node)) {
			node.left = child;
		} else {
			node.right = child;
		}
		return child;
	}
	
	private boolean less(Point2D p, TreeNode node) {
		return ((node.isVertical && p.x() < node.point.x()) 
				|| (!node.isVertical && p.y() < node.point.y()));
	}
	
	private RectHV sameSideChildRectangle(Point2D p, TreeNode node) {
		boolean xIsLess = p.x() < node.point.x();
		boolean yIsLess = p.y() < node.point.y(); 
		double xmin = node.rect.xmin();
		double ymin = node.rect.ymin();
		double xmax = node.rect.xmax();
		double ymax = node.rect.ymax();
		if (node.isVertical && xIsLess)   xmax = node.point.x();
		if (node.isVertical && !xIsLess)  xmin = node.point.x();
		if (!node.isVertical && yIsLess)  ymax = node.point.y();
		if (!node.isVertical && !yIsLess) ymin = node.point.y();
		
		return new RectHV(xmin, ymin, xmax, ymax);
	}
	
	// Does the set contain point p?
	public boolean contains(Point2D p) {
		if (p == null)  throw new java.lang.NullPointerException();
		return allPoints.contains(p);
	}
	
	// Draw all points to standard draw
	public void draw() {
		preOrder(root);
	}
	
	private void preOrder(TreeNode node) {
		if (node == null || node.point == null)  return;
		draw(node);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	private void draw(TreeNode node) {
		node.point.draw();
		
		if (node.isVertical) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
		} else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
		}
	}
	
	// All points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)  throw new java.lang.NullPointerException();
		
		List<Point2D> results = new ArrayList<>();
		dfsRange(root, rect, results);
		return results;
	}
	
	private void dfsRange(TreeNode node, RectHV query, List<Point2D> results) {		
		if (node == null)  return;
		
		collect(node, query, results);
		
		if (node.left != null && intersect(node.left.rect, query)) {
			dfsRange(node.left, query, results);
		}
		if (node.right != null && intersect(node.right.rect, query)) {
			dfsRange(node.right, query, results);
		}
	}
	
	private boolean intersect(RectHV rect1, RectHV rect2) {
		if (rect1 == null || rect2 == null)  return false;
		
		// consider the 4 edges of rectangle formed by intersection
		double left = Math.max(rect1.xmin(), rect2.xmin());
		double right = Math.min(rect1.xmax(), rect2.xmax());
		double top = Math.min(rect1.ymax(), rect2.ymax());
		double bottom = Math.max(rect1.ymin(), rect2.ymin());
		if (right < left || top < bottom)  return false;
		
		return true;
	}
	
	private void collect(TreeNode node, RectHV query, List<Point2D> results) {
		if (node.point == null)  return;
		
		// double distance = point2Rectangle(node.point, query);
		double distance = query.distanceTo(node.point);
		if (distance < Math.pow(10, -7)) {
			results.add(node.point);
		}
	}
	
	private double point2Rectangle(Point2D p, RectHV rect) {
		if (p == null || rect == null)  throw new java.lang.IllegalArgumentException("point or rect is null!");
		
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
			double answer = Math.min(Math.min(a1, a2), Math.min(b1, b2));
			return answer;
		}
	}
	
	// A nearest neighbor in the set to point p; null if the set if empty
	public Point2D nearest(Point2D p) {
		if (p == null)  throw new java.lang.NullPointerException();
		if (size() == 0)  return null;
		
		Point2D result = root.point;
		dfsNeighbor(root, p, result);
		return result;
	}
	
	private void dfsNeighbor(TreeNode node, Point2D p, Point2D result) {
		if (node == null || node.point == null)  return;
		
		double dist = p.distanceTo(node.point);
		double currMin = p.distanceTo(result);
		if (dist < currMin) {
			result = node.point;
		}
		
		TreeNode sameSideChild = sameSideChildOfNodeWithP(node, p);
		dfsNeighbor(sameSideChild, p, result);
		
		TreeNode diffSideChild = (sameSideChild == node.left) ? node.right : node.left;
		if (diffSideChild != null && point2Rectangle(p, diffSideChild.rect) < currMin) {
			dfsNeighbor(diffSideChild, p, result);
		}
	}

	// Unit testing (optional)
	public static void main(String[] args) {
		Point2D p = new Point2D(0.6565, 0.7553);
		RectHV rect = new RectHV(0.5578, 0.229, 0.7079, 0.7553);
		double dist = new KdTree().point2Rectangle(p, rect);
		System.out.println(dist);
	}
}
