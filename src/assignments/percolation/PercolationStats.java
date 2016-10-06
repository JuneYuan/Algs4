package assignments.percolation;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private int N;
	private int T;
	private double[] x;			// [x1...xT]: mu = (∑x) / T
	private double[] delta2;	// [(x1 - u)^2 ... (xT - u)^2]: sigma2 = (∑ delta2) / T - 1
	
	// Perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)  throw new java.lang.IllegalArgumentException();
		
		this.N = n;
		this.T = trials;
		x = new double[T];
		delta2 = new double[T];
		for (int i = 0; i < T; i++) {
			x[i] = oneTrial();
		}
		
		double mu = mean();
		for (int i = 0; i < T; i++) {
			double base = x[i] - mu;
			delta2[i] = Math.pow(base, 2);
		}
	}

	private double oneTrial() {
		double result = 0;
		
		Percolation perc = new Percolation(N);
		int openCnt = 0;
		while (!perc.percolates()) {
			int i = StdRandom.uniform(1, N + 1);
			int j = StdRandom.uniform(1, N + 1);
			if (!perc.isOpen(i, j)) {
				perc.open(i, j);
				openCnt++;
			}
		}
		
		result = openCnt / (N * N);		
		return result;
	}
	
	// Sample mean of percolation threshold
	public double mean() {
		double sum = 0;
		for (double d : x) {
			sum += d;
		}
		return (sum / T);
	}

	// Sample standard deviation of percolation threshold
	public double stddev() {
		double sum = 0;
		for (double d : x) {
			sum += d;
		}
		return Math.sqrt(sum / (T - 1));
	}

	// Low endpoint of 95% confidence interval
	public double confidenceLo() {
		double mu = mean();
		double tmp = 1.96 * stddev() / Math.sqrt(T);
		return mu - tmp;
	}

	// High endpoint of 95% confidence interval
	public double confidenceHi() {
		double mu = mean();
		double tmp = 1.96 * stddev() / Math.sqrt(T);
		return mu + tmp;
	}

	// test client (described below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println("mean\t = " + stats.mean());
		System.out.println("stddev\t = " + stats.stddev());
		System.out.printf("95% confidence interval\t = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
	}

}
