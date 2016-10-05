package assignments.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

public class Percolation {
	private static int N;
	private static int ufSize;
	// true for "open", and false for "blocked"
	private static boolean[][] grid;
	// 0 site for the Virtual Top, (N*N-1) site for the Virtual Bottom, 
	// and the rest for the grid
	private static WeightedQuickUnionUF uf;

	// Create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n <= 0)
			throw new java.lang.IllegalArgumentException();
		N = n;
		ufSize = N * N + 2;
		grid = new boolean[N + 1][N + 1];
		uf = new WeightedQuickUnionUF(ufSize);
	}

	// Open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();

		grid[i][j] = true;

		if (i == 1) {
			uf.union(hash(i, j), 0);
		}
		if (i == N) {
			uf.union(hash(i, j), ufSize - 1);
		}
		
		// left, right, up, down
		final int[][] direction = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
		for (int d = 0; d < direction.length; d++) {
			int nextI = i + direction[d][0];
			int nextJ = j + direction[d][1];
			if (nextI >= 1 && nextI <= N && nextJ >= 1 && nextJ <= N && isOpen(nextI, nextJ)) {
				uf.union(hash(i, j), hash(nextI, nextJ));
			}
		}
	}

	// Is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		return grid[i][j];
	}

	// Is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
			throw new java.lang.IndexOutOfBoundsException();
		int k = hash(i, j);
		return uf.connected(k, 0);
	}

	// Does the system percolate?
	public boolean percolates() {
		return uf.connected(0, ufSize - 1);
	}

	private int hash(int i, int j) {
		return N * (i - 1) + j;
	}

	// Test client (optional)
	public static void main(String[] args) {
		System.out.println("hello");
	}
}
