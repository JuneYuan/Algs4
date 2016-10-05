package assignment;


import java.util.Arrays;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph G, R;
	
	private int sap, acs;
	
	/* 时间复杂度 E + V */

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		//this.G = G;
		this.G = new Digraph(G);
		R = G.reverse();
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		ancestor(v, w);
		return sap != 2*G.E() ? sap : -1;	
	}
	
	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
			throw new IndexOutOfBoundsException();
		sap = 2 * G.E();
		acs = -1;
		DirectedDFS dfs1 = new DirectedDFS(G, v);
		DirectedDFS dfs2 = new DirectedDFS(G, w);
		for (int k = 0; k < G.V(); k++) {	 // 遍历v所有祖先中同时也是w祖先的那些节点
			if (dfs1.marked(k) && dfs2.marked(k)) {
				int len = pathLen(k, v, w);
				if (len < sap)
				{ acs = k;	sap = len; } 
			}
		}	
		return acs;		
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		ancestor(v, w);
		return sap;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)	throw new NullPointerException();
		if (!v.iterator().hasNext() || !w.iterator().hasNext())	 
		{ sap = -1;  return -1; }
		
		sap = 2 * G.E();
		acs = -1;
		DirectedDFS dfs1 = new DirectedDFS(G, v);
		DirectedDFS dfs2 = new DirectedDFS(G, w);
		for (int k = 0; k < G.V(); k++) {	 // 遍历同时是v祖先和w祖先的那些节点
			if (dfs1.marked(k) && dfs2.marked(k)) {			
				int len = pathLen(k, v, w);
				if (len < sap) {
					acs = k;
					sap = len;
				}
			}
		}	
		return acs;	
	}
	
	private int pathLen(int ancestor, int v, int w) {
		// 对 ancestor 作 BFS ，所得路径之和即 ShortestAncestorPath
		BreadthFirstDirectedPaths bfsAcs = new BreadthFirstDirectedPaths(R, ancestor);		
		int dist1 = bfsAcs.distTo(v);
		int dist2 = bfsAcs.distTo(w);
		return dist1 + dist2;		
	}
	
	private int pathLen(int ancestor, Iterable<Integer> v, Iterable<Integer> w){
		BreadthFirstDirectedPaths bfsAcs = new BreadthFirstDirectedPaths(R, ancestor);
		int len1 = fun(bfsAcs, v);
		int len2 = fun(bfsAcs, w);
		return len1 + len2;
	}
	
	private int fun(BreadthFirstDirectedPaths bfs, Iterable<Integer> v) {	// 找到v中距离ancestor最近的节点
		int ret = G.E(), tmp;
		for (int k : v) {
			tmp = bfs.distTo(k);
			if (tmp < ret)
				ret = tmp;
		}
		return ret;				
	}
	

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);		

		Integer[] b = {};
		Integer[] a = {};
		Iterable<Integer> v = Arrays.asList(a);
		Iterable<Integer> w = Arrays.asList(b);
		int ancestor = sap.ancestor(v, w);
		int length = sap.length(v, w);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		
		G.addEdge(6, 0);
		ancestor = sap.ancestor(v, w);
		length = sap.length(v, w);
		StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		
		
/*		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int ancestor = sap.ancestor(v, w);
			int length = sap.length(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}*/
	}
}