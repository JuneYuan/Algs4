package assignments.eightPuzzle;

import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	Board twin;		      // the initial board modified by swappint a
	boolean solvable;     // is the initial board solvable?
	MinPQ<Board> pqInit;  // priority queue for solving the initial board
	MinPQ<Board> pqTwin;  // priority queue for solving the twin board pair of blocks
	Queue<Board> seq = new Queue<>();  // the solution sequence
	
	// find a solution to the initial board (using A* algorithm)
	public Solver(Board initial) {
		final Comparator<Board> priority = new Order();
		pqInit = new MinPQ<>(priority);
		pqInit.insert(initial);
		
		pqTwin = new MinPQ<>(priority);
		twin = initial.twin();
		pqTwin.insert(twin);
		
		while (!pqInit.isEmpty() || !pqTwin.isEmpty()) {
			oneStepSearch(pqInit, 0);
			oneStepSearch(pqTwin, 1);
		}
	}
	
	private void oneStepSearch(MinPQ<Board> pq, int flag) {
		Iterable<Board> neighbors = pq.min().neighbors();
		for (Board neighbor : neighbors) {
			if (neighbor.isGoal()) {
				solvable = (flag == 0) ? true : false;
				return;
			}
			
			pq.insert(neighbor);
		}
		
		pq.delMin();
	}
	
	private static class Order implements Comparator<Board> {

		@Override
		public int compare(Board o1, Board o2) {
			return o1.manhattan() - o2.manhattan();
		}
		
	}
	
	// is the initial board solvable?
	public boolean isSolvable() {
		return solvable;
	}
	
	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return seq.size();
	}
	
	
	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		return seq;
	}
	
	// solve a slider puzzle
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				blocks[i][j] = in.readInt();
			}
		}
		Board initial = new Board(blocks);
		
		// solve the puzzle
		Solver solver = new Solver(initial);
		
		// print solution
		if (!solver.isSolvable()) {
			StdOut.println("No solution possible");
		} else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution()) {
				StdOut.println("board");
			}
		}
	}
}
