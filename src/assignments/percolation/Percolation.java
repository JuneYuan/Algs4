package assignments.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private int ufSize;
	// state of n-by-n sites: true for "open", and false for "blocked"
	private boolean[][] grid;
	// site 0 for the Virtual Top,
	// site (N*N+1) for the Virtual Bottom, 
	// and the rest for the grid
	private WeightedQuickUnionUF uf;

	// Create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if (n <= 0)  throw new java.lang.IllegalArgumentException();
		
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
		
		// 4 adjacents: left, right, up, down
		final int[][] direction = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };
		for (int d = 0; d < direction.length; d++) {
			int adjaI = i + direction[d][0];
			int adjaJ = j + direction[d][1];
			if (adjaI >= 1 && adjaI <= N 
				&& adjaJ >= 1 && adjaJ <= N 
				&& isOpen(adjaI, adjaJ)) {
				uf.union(hash(i, j), hash(adjaI, adjaJ));
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
	}
}
