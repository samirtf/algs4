import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Programming Assignment 1: Percolation
 * <p>
 * Percolation. Given a composite systems comprised of randomly
 * distributed insulating and metallic materials: what fraction of
 * the materials need to be metallic so that the composite system
 * is an electrical conductor? Given a porous landscape with water
 * on the surface (or oil below), under what conditions will
 * the water be able to drain through to the bottom (or the oil
 * to gush through to the surface)? Scientists have defined
 * an abstract process known as percolation to model such situations.
 */
public class Percolation {

    /**
     * Site is closed site.
     */
    private static final int BLOCKED = 0;

    /**
     * Site is open site.
     */
    private static final int OPEN = 1;

    /**
     * Site is full open site.
     */
    private static final int FULL_OPEN = 2;

    /**
     * A size of nSize-by-nSize grid.
     */
    private final int nSize;

    /**
     * The WeightedQuickUnionUF class represents a union–find data type
     * (also known as the disjoint-sets data type). It supports the union
     * and find operations, along with a connected operation for determining
     * whether two sites are in the same component and a count operation
     * that returns the total number of components. The union–find data
     * type models connectivity among a set of nSize sites, named 0 through nSize–1.
     */
    private final WeightedQuickUnionUF unionUF;

    /**
     * Counts the number of open sites.
     */
    private int openSitesCount = 0;

    /**
     * Represents a virtual open site to connect all open sites on top.
     * Value -1 represents a null top site.
     */
    private int virtualTopSite = -1;

    /**
     * It's used to keep track of state of sites to avoid backwash.
     */
    private char[] sitesMemory;

    /**
     * Create nSize-by-nSize grid, with all sites blocked.
     *
     * @param nSize an integer representing a nSize-by-nSize grid size.
     */
    public Percolation(int nSize) {
        if (nSize <= 0) {
            throw new IllegalArgumentException();
        }
        this.nSize = nSize;
        this.unionUF = new WeightedQuickUnionUF(nSize * nSize);
        this.sitesMemory = new char[nSize * nSize];
    }

    /**
     * Tests client (optional).
     *
     * @param args
     */
    public static void main(final String[] args) {
        System.out.println("Tests client (optional).");
    }

    /**
     * Opens site (row, col) if it is not open already.
     *
     * @param row an integer representing x-position in grid.
     * @param col an integer representing y-position in grid.
     */
    public void open(int row, int col) {
        checkBoundaries(row, col);
        if (sitesMemory[rowColTo1D(row, col)] == BLOCKED) {
            sitesMemory[rowColTo1D(row, col)] = OPEN;
            openSitesCount++;
        }
        else {
            return;
        }
        connectToUp(row, col);
        connectToDown(row, col);
        connectToLeft(row, col);
        connectToRight(row, col);
        if (row == 1) {
            if (virtualTopSite != -1) {
                unionUF.union(rowColTo1D(row, col), virtualTopSite);
            }
            virtualTopSite = rowColTo1D(row, col);
            sitesMemory[rowColTo1D(row, col)] = FULL_OPEN;
        }
        if (virtualTopSite != -1 && unionUF.connected(rowColTo1D(row, col), virtualTopSite)) {
            sitesMemory[rowColTo1D(row, col)] = FULL_OPEN;
        }
    }

    /**
     * Tries to connect the current site to site on above.
     *
     * @param currentRow an integer representing x-position in grid.
     * @param currentCol an integer representing y-position in grid.
     */
    private void connectToUp(int currentRow, int currentCol) {
        connectToSiteIfBothOpen(currentRow, currentCol, currentRow - 1, currentCol);
    }

    /**
     * Tries to connect the current site to site on below.
     *
     * @param currentRow an integer representing x-position in grid.
     * @param currentCol an integer representing y-position in grid.
     */
    private void connectToDown(int currentRow, int currentCol) {
        connectToSiteIfBothOpen(currentRow, currentCol, currentRow + 1, currentCol);
    }

    /**
     * Tries to connect the current site to site on left.
     *
     * @param currentRow an integer representing x-position in grid.
     * @param currentCol an integer representing y-position in grid.
     */
    private void connectToLeft(int currentRow, int currentCol) {
        connectToSiteIfBothOpen(currentRow, currentCol, currentRow, currentCol - 1);
    }

    /**
     * Tries to connect the current site to site on right.
     *
     * @param currentRow an integer representing x-position in grid.
     * @param currentCol an integer representing y-position in grid.
     */
    private void connectToRight(int currentRow, int currentCol) {
        connectToSiteIfBothOpen(currentRow, currentCol, currentRow, currentCol + 1);
    }

    /**
     * Connect to adjacent site if only both is valid and already open.
     *
     * @param currentRow an integer representing x-position in grid.
     * @param currentCol an integer representing y-position in grid.
     * @param adjRow     an integer representing x-position in grid.
     * @param adjCol     an integer representing y-position in grid.
     */
    private void connectToSiteIfBothOpen(int currentRow, int currentCol, int adjRow, int adjCol) {
        try {
            checkBoundaries(currentRow, currentCol);
            checkBoundaries(adjRow, adjCol);
        }
        catch (IllegalArgumentException exc) {
            // Abort connect operation.
            return;
        }
        int currentOpenSite = rowColTo1D(currentRow, currentCol);
        int adjOpenSite = rowColTo1D(adjRow, adjCol);
        if (sitesMemory[currentOpenSite] != BLOCKED && sitesMemory[adjOpenSite] != BLOCKED &&
            currentOpenSite != adjOpenSite) {
            unionUF.union(currentOpenSite, adjOpenSite);
        }
    }

    /**
     * Parses an 2D representation to 1D representation of a site.
     *
     * @param row an integer representing x-position in grid.
     * @param col an integer representing y-position in grid.
     * @return an integer representing the position of site in an 1-dimensional array.
     */
    private int rowColTo1D(int row, int col) {
        return (row - 1) * nSize + (col - 1);
    }

    /**
     * Checks if site is on valid position.
     *
     * @param row an integer representing x-position in grid.
     * @param col an integer representing y-position in grid.
     */
    private void checkBoundaries(int row, int col) {
        if (row <= 0 || row > nSize || col <= 0 || col > nSize) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if site is on valid position.
     *
     * @param index an integer representing position in array.
     */
    private void checkBoundaries(int index) {
        if (index < 0 || index > nSize * nSize) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks if site is open.
     *
     * @param row an integer representing x-position in grid.
     * @param col an integer representing y-position in grid.
     * @return true if site is open; otherwise, false.
     */
    public boolean isOpen(int row, int col) {
        checkBoundaries(row, col);
        return sitesMemory[rowColTo1D(row, col)] != BLOCKED;
    }

    /**
     * Checks if site (row, col) is full.
     *
     * @param row an integer representing x-position in grid.
     * @param col an integer representing y-position in grid.
     * @return true if site is full; otherwise, false.
     */
    public boolean isFull(int row, int col) {
        checkBoundaries(row, col);
        boolean isFull = sitesMemory[rowColTo1D(row, col)] == FULL_OPEN;
        if (isFull) {
            return true;
        }
        if (virtualTopSite != -1 && unionUF.connected(rowColTo1D(row, col), virtualTopSite)) {
            sitesMemory[rowColTo1D(row, col)] = FULL_OPEN;
            return true;
        }
        return false;
    }


    /**
     * Calculates the number of open sites.
     *
     * @return an integer representing the number of open sites.
     */
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    /**
     * Checks if site (index) is full. A full site is
     * an open site that can be connected to an open site
     * in the top row via a chain of neighboring
     * (left, right, up, down) open sites.
     *
     * @param index an integer representing position in array.
     * @return true if site is full; otherwise, false.
     */
    private boolean isFull(int index) {
        checkBoundaries(index);
        if (virtualTopSite != -1 && unionUF.connected(index, virtualTopSite)) {
            sitesMemory[index] = FULL_OPEN;
            return true;
        }
        return false;
    }

    /**
     * Checks if system percolates. The system percolates if
     * there is a full site in the bottom row
     *
     * @return true if system percolates; otherwise, false.
     */
    public boolean percolates() {
        // Iterate through the bottom sites checking its connected to top.
        for (int i = nSize * (nSize - 1); i < nSize * nSize; i++) {
            if (sitesMemory[i] == BLOCKED) {
                continue;
            }
            if (sitesMemory[i] == FULL_OPEN) {
                return true;
            }
            if (isFull(i)) {
                return true;
            }
        }
        return false;
    }

}