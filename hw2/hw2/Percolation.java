package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int N;
    private int openNum;
    private int sentinelT;
    private int sentinelB;
    private boolean percolated;
    private boolean[] openIndex;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridB;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        openNum = 0;
        openIndex = new boolean[N * N];
        for (int i = 0; i < N * N; i += 1) {
            openIndex[i] = false;
        }
        // only virtual top site
        grid = new WeightedQuickUnionUF(N * N + 1);
        sentinelT = N * N;
        // virtual top and bottom sites
        gridB = new WeightedQuickUnionUF(N * N + 2);
        sentinelB = N * N + 1;
        percolated = false;
    }

    private boolean inRange(int row, int col) {
        return row >= 0 && row <= N - 1 && col >= 0 && col <= N - 1;
    }

    private int getIndex(int row, int col) {
        return row * N + col;
    }

    // open the site (row, col) if it is not open already             
    public void open(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arg: row " + row + ", col " + col + ".");
        }
        if (!openIndex[getIndex(row, col)]) {
            openIndex[getIndex(row, col)] = true;
            if (row == 0) {
                grid.union(getIndex(row, col), sentinelT);
                gridB.union(getIndex(row, col), sentinelT);
            }
            if (row == N - 1) {
                gridB.union(getIndex(row, col), sentinelB);
            }
            openNum += 1;
            if (inRange(row - 1, col) && isOpen(row - 1, col)) {
                grid.union(getIndex(row, col), getIndex(row - 1, col));
                gridB.union(getIndex(row, col), getIndex(row - 1, col));
            }
            if (inRange(row + 1, col) && isOpen(row + 1, col)) {
                grid.union(getIndex(row, col), getIndex(row + 1, col));
                gridB.union(getIndex(row, col), getIndex(row + 1, col));
            }
            if (inRange(row, col - 1) && isOpen(row, col - 1)) {
                grid.union(getIndex(row, col), getIndex(row, col - 1));
                gridB.union(getIndex(row, col), getIndex(row, col - 1));
            }
            if (inRange(row, col + 1) && isOpen(row, col + 1)) {
                grid.union(getIndex(row, col), getIndex(row, col + 1));
                gridB.union(getIndex(row, col), getIndex(row, col + 1));
            }
            if (gridB.connected(sentinelT, sentinelB)) {
                percolated = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arg: row " + row + ", col " + col + ".");
        }
        return openIndex[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException("Invalid arg: row " + row + ", col " + col + ".");
        }
        return grid.connected(getIndex(row, col), sentinelT);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        // see TestPercolation.java
        // @source https://github.com/aviatesk/cs61b-sp18/blob/master/hw2/hw2/TestPercolation.java
    }
}
