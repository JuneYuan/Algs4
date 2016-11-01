package assignments.eightPuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Node initNode;		 // the initial Node
	private Node twinNode;		 // the initial Node modified by swapping a pair of blocks
	private Node goalNode;       // the goal Node, used to backtrace the solution sequence
	private boolean solvable;    // is the initial board solvable?
	private boolean quit;        // helps quit the (lockstep) search loop
	private MinPQ<Node> pqInit;  // priority queue for solving the initial board
	private MinPQ<Node> pqTwin;  // priority queue for solving the twin board pair of blocks
	
	private class Node implements Comparable<Node> {
		final Board board;  // current board
		final int moves;    // moves made to reach this board
		final Node prev;    // previous search node
		
		public Node(Board board, int moves, Node prev) {
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}
		
		@Override
		public int compareTo(Node that) {
			int h1 = this.board.manhattan() + this.moves;
			int h2 = that.board.manhattan() + that.moves;
			return (h1 - h2);
		}
		
		public String toString() {
			return board.toString();
		}
	}
	
	// find a solution to the initial board (using A* algorithm)
	public Solver(Board initial) {
		if (initial == null)  throw new java.lang.NullPointerException();
		
		// if initial is already the goal, or a twin of the goal, conclude instantly
		if (initial.isGoal()) {
			solvable = true;
			goalNode = new Node(initial, 0, null);
			return;
		}
		if (initial.twin().isGoal()) {
			solvable = false;
			return;
		}

		// create priority queues for the initial board and the twin board, respectively
		// then apply the A* algorithm to above two boards in lockstep
		initNode = new Node(initial, 0, null);
		pqInit = new MinPQ<>();
		pqInit.insert(initNode);

		twinNode = new Node(initial.twin(), 0, null);
		pqTwin = new MinPQ<>();
		pqTwin.insert(twinNode);
		
		quit = false;
		while (true) {
			if (quit)  break;
			
			oneStepSearch(pqInit, 0);
			oneStepSearch(pqTwin, 1);
		}
	}
	
	// search the possible next node starting from pq.min()
	private void oneStepSearch(MinPQ<Node> pq, int flag) {
		Node min = pq.delMin();
		
		// the dequeued search node corresponds to a goal board
		if (min.board.isGoal()) {
			if (flag == 0) {
				solvable = true;
				goalNode = min;
			} else {
				solvable = false;
			}
			quit = true;
		}
		
		// insert onto the priority queue the neighboring search nodes
		Iterable<Board> neighbors = min.board.neighbors();
		for (Board neighbor : neighbors) {
			Node node = new Node(neighbor, min.moves + 1, min);
			
			// filter the duplicates: min's neighbor that repeats min's previous
			if (min.prev == null || !neighbor.equals(min.prev.board)) {
				pq.insert(node);
			}
		}
	}
		
	// is the initial board solvable?
	public boolean isSolvable() {
		return solvable;
	}
	
	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return solvable ? goalNode.moves : -1;
	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!solvable)  return null;

		Queue<Board> result = new Queue<>();
		Stack<Node> stack = new Stack<>();
		// backtracing from the goalNode, resulting in a stack
		Node tmp = goalNode;
		while (tmp != null) {
			stack.push(tmp);
			tmp = tmp.prev;
		}
		
		// get queue from the stack
		while (!stack.isEmpty()) {
			result.enqueue(stack.pop().board);
		}
		
		return result;
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
				StdOut.println(board);
			}
		}
	}
}
