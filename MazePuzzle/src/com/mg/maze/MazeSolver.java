package com.mg.maze;

public class MazeSolver {

	private int numRows = 10;
	private int numCols = 10;
	private int startRow = 5;
	private int startCol = 3;

	private int endRow = 1;
	private int endCol = 6;
	/* 1 = wall */
	/* 0 = space */
	private int myMaze[][] = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1, 1, 0, 1, 1, 1 }, { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
			{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private int shortestPath[] = new int[numRows * numCols];
	private int shortestLength;

	private boolean beenHere(int row, int col, int pathSoFar[], int lengthSoFar) {
		int target = row * numCols + col;
		for (int x = 0; x < lengthSoFar; x++) {
			if (pathSoFar[x] == target)
				return true;
		}
		return false;
	}

	private void showMyPath(int myPath[], int myLength) {
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				if (myMaze[r][c] == 1)
					System.out.print("|");
				else if (r == startRow && c == startCol)
					System.out.print("S");
				else if (r == endRow && c == endCol)
					System.out.print("X");
				else if (beenHere(r, c, myPath, myLength))
					System.out.print("o");
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
	}

	private void findPath(int row, int col, int pathSoFar[], int lengthSoFar) {
		if (row < 0 || col < 0 || row >= numRows || col >= numCols)
			return;

		if (myMaze[row][col] == 1)
			return;

		if (beenHere(row, col, pathSoFar, lengthSoFar))
			return;

		int myPath[] = new int[lengthSoFar + 1];

		System.arraycopy(pathSoFar, 0, myPath, 0, lengthSoFar);

		myPath[lengthSoFar++] = row * numCols + col; // ID

		if (row == endRow && col == endCol) {
			System.out.println("Found Path of length " + lengthSoFar + "!:");
			showMyPath(myPath, lengthSoFar);

			if (lengthSoFar <= shortestLength) {
				shortestLength = lengthSoFar;
				System.arraycopy(myPath, 0, shortestPath, 0, lengthSoFar);
				System.out.println(" (New Shortest path of length " + lengthSoFar + ")");
			}
			System.out.println("");
			return;
		}

		findPath(row - 1, col, myPath, lengthSoFar);
		findPath(row, col - 1, myPath, lengthSoFar);
		findPath(row, col + 1, myPath, lengthSoFar);
		findPath(row + 1, col, myPath, lengthSoFar);
	}

	public static void main(String[] args) {
		int r, c, x;
		int pathSoFar[];
		int lengthSoFar;

		MazeSolver mazeSolver = new MazeSolver();
		pathSoFar = new int[mazeSolver.numRows * mazeSolver.numCols];

		for (x = 0; x < mazeSolver.numRows * mazeSolver.numCols; x++) {
			mazeSolver.shortestPath[x] = -1;
			pathSoFar[x] = -1;
		}

		mazeSolver.shortestLength = mazeSolver.numRows * mazeSolver.numCols + 1;
		lengthSoFar = 0;

		System.out.println("Here's the maze:");

		for (r = 0; r < mazeSolver.numRows; r++) {
			for (c = 0; c < mazeSolver.numCols; c++) {
				if (r == mazeSolver.startRow && c == mazeSolver.startCol)
					System.out.print("S");
				else if (r == mazeSolver.endRow && c == mazeSolver.endCol)
					System.out.print("X");
				else if (mazeSolver.myMaze[r][c] == 0)
					System.out.print(" ");
				else
					System.out.print("|");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("Finding Paths ... ");

		mazeSolver.findPath(mazeSolver.startRow, mazeSolver.startCol, pathSoFar, lengthSoFar);

		System.out.println("");
		System.out.println("The shortest path found was the following of length: " + mazeSolver.shortestLength);
		mazeSolver.showMyPath(mazeSolver.shortestPath, mazeSolver.shortestLength);
	}
}
