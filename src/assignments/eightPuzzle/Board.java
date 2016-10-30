package assignments.eightPuzzle;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private final int n;		// board dimension
	private int[][] copy;		// deep copy of blocks[][]
	private int[][] goal;		// the goal
	private int blankX, blankY;	// index of the blank square

	// construct a board from a n-by-n array of blocks
	public Board(int[][] blocks) {
		if (blocks == null || blocks.length == 0)  throw new java.lang.IllegalArgumentException();
		
		n = blocks.length;

		copy = new int[n][n];
		goal = new int[n][n];
		for (int i = 0; i < n; i++) {
			copy[i] = new int[n];
			goal[i] = new int[n];
			
			for (int j = 0; j < n; j++) {
				copy[i][j] = blocks[i][j];
				goal[i][j] = (i * n + j + 1) % (n * n);
				
				if (blocks[i][j] == 0) {
					blankX = i;
					blankY = j;
				}
			}
		}
	}
	
	// copy constructor
	private Board(Board board) {
		this(board.copy);
	}
	
	// board dimension n
	public int dimension() {
		return n;
	}
	
	// number of blocks out of place
	public int hamming() {
		int cnt = 0;
		
		for (int i = 0; i < n; i++) 
			for (int j = 0; j < n; j++)
				if (copy[i][j] != 0 && copy[i][j] != goal[i][j])  cnt++;
		
		return cnt;
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int cnt = 0;
		
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (copy[x][y] == 0)  continue;
				
				int refX, refY;
				if (copy[x][y] % n == 0) {
					refX = copy[x][y] / n - 1;
					refY = n - 1;
				} else {
					refX = copy[x][y] / n;
					refY = copy[x][y] % n - 1;
				}
				
				cnt += Math.abs(x - refX);
				cnt += Math.abs(y - refY);
			}
		}
		
		return cnt;
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		if (n < 2)  return new Board(copy);

		int[][] twin = new int[n][n];
		for (int i = 0; i < n; i++) {
			System.arraycopy(copy[i], 0, twin[i], 0, n);
		}
		
		int x1 = 0, y1 = 0;
		int x2 = n - 1, y2 = n - 1;
		if (x1 == blankX && y1 == blankY)  y1++;
		if (x2 == blankX && y2 == blankY)  y2--;
		swap(twin, x1, y1, x2, y2);
		
		return new Board(twin);
	}
	
	// does this board equal y
	public boolean equals(Object y) {
		if (this == y)  return true;
		if (y == null)  return false;
		if (this.getClass() != y.getClass())  return false;
		
		Board that = (Board) y;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.copy[i][j] != that.copy[i][j])  return false;
			}
		}
		
		return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		Queue<Board> result = new Queue<>();
		
		int[][] directions = new int[][]{
			{-1, 0}, {1, 0}, {0, -1}, {0, 1}
		};  // up, down, left, right

		for (int[] direc : directions) {
			Board board = new Board(this);
			boolean success = swap(board.copy, board.blankX, board.blankY, board.blankX + direc[0], board.blankY + direc[1]);
			if (success) {
				result.enqueue(board);
			}
		}
		
		return result;
	}
	
	private boolean swap(int[][] a, int x1, int y1, int x2, int y2) {
		if (x2 < 0 || x2 >= n || y2 < 0 || y2 >= n)  return false;
		int tmp = a[x1][y1];
		a[x1][y1] = a[x2][y2];
		a[x2][y2] = tmp;
		return true;
	}
	
	// string representation of this board (in specified format)
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(n + "\n");
		for (int[] arr : copy) {
			for (int t : arr) {
				String str = String.format("%2d ", t);
				sb.append(str);
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	// unit test
	public static void main(String[] args) {
		int[][] blocks = new int[][] {
			{0, 1, 3},
			{4, 2, 5},
			{7, 8, 6}
		};
		Board board = new Board(blocks);
		System.out.println("initial--");
		StdOut.println(board);
		
		System.out.println("goal--");
		System.out.println(new Board(board.goal));

		System.out.println("is board the goal? -- " + board.isGoal());
		System.out.println("is board.goal the goal? -- " + new Board(board.goal).isGoal());
		
		System.out.println("\ntwin--");
		System.out.println(board.twin());
		
		System.out.println("neighbors--");
		for (Board neighbor : board.neighbors())
			System.out.println(neighbor);
		
		System.out.println("Hamming = " + board.hamming());
		System.out.println("\nManhattan = " + board.manhattan());		
	}
}
