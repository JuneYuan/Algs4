package assignments.kdTrees;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

	private static class TreeNode {
		Point2D point;
		RectHV rect;
		TreeNode left, right;
		
		public TreeNode() {
			
		}
	}
	
	// Construct an empty set of points
	public KdTree() {
		
	}
	
	// Is the set empty?
	public boolean isEmpty() {
		
	}
	
	// Nubmer of points in the set
	public int size() {
		
	}
	
	// Add the point to the set (if it is not already in the set)
	public void insert(Point2D p){
		
	}
	
	// Does the set contain point p?
	public boolean contains(Point2D p) {
		
	}
	
	// Draw all points to standard draw
	public void draw() {
		
	}
	
	// All points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> results = new ArrayList<>();
		dfs(root, rect, results);
		
		return results;
	}
	
	private void dfs(TreeNode node, RectHV queryRect, List<Point2D> results) {
		if (node.left == null && node.right == null) {
			collect(node.rect, queryRect, results);
		}
		
		if (node.left != null && intersect(node.left.rect, queryRect)) {
			dfs(node.left, queryRect, results);
		}
		if (node.right != null && intersect(node.right.rect, queryRect)) {
			dfs(node.right, queryRect, results);
		}
	}
	
	private boolean intersect(RectHV rect1, RectHV rect2) {
		return false;
	}
	
	private void collect(RectHV rect1, RectHV rect2, List<Point2D> results) {
		
	}
	
	// A nearest neighbor in the set to point p; null if the set if empty
	public Point2D nearest(Point2D p) {
		
	}
	
	// Unit testing (optional)
	public static void main(String[] args) {
		
	}
}
