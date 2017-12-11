import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double mean;
    private final double stddev;

    /**
     * Performs trials independent experiments on an n-by-n grid.
     *
     * @param nSize  an integer representing n-bt-n grid size.
     * @param trials an integer representing the times the experiment will run.
     */
    public PercolationStats(int nSize, int trials) {
        if (nSize <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        double[] samples = new double[trials];

        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(nSize);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, nSize + 1);
                int col = StdRandom.uniform(1, nSize + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            samples[i] = (percolation.numberOfOpenSites() * 1.0) / (nSize * nSize);
        }
        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
    }

    /**
     * Tests client (described below).
     *
     * @param args
     */
    public static void main(String[] args) {
        final int N = Integer.parseInt(args[0]);
        final int TRIALS = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, TRIALS);
        System.out.println("mean\t\t\t\t\t= " + ps.mean());
        System.out.println("stddev\t\t\t\t\t= " + ps.stddev());
        System.out.println("95% confidence interval\t= [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

    /**
     * Calculates sample mean of percolation threshold.
     *
     * @return a double representing the mean of percolation threashold.
     */
    public double mean() {
        return mean;
    }

    /**
     * Calculates sample standard deviation of percolation threshold.
     *
     * @return a double representing the standard deviation of percolation threshold.
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Calculates the low  endpoint of 95% confidence interval.
     *
     * @return a double representing the low  endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    /**
     * Calculates high endpoint of 95% confidence interval.
     *
     * @return a double representing the high endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }
}