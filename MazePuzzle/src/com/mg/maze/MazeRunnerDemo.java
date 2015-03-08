package com.mg.maze;

public class MazeRunnerDemo {

	private int mNumOfRows = 10;
	private int mNumOfCols = 10;

	private int mStartRow = 5;
	private int mStartCol = 3;

	private int mEndRow = 1;
	private int mEndCol = 6;

	// 1 - Wall
	// 0 - Space

	private int myMaze[][] = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 1, 0, 0, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1, 1, 0, 1, 1, 1 }, { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
			{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private int shortestPath[] = new int[mNumOfRows * mNumOfCols];
	private int shortestLength;

	private boolean beenHere(int row, int col, int[] pathSoFar, int lengthSoFar) {
		int target = row * mNumOfCols + col;

		for (int i = 0; i < lengthSoFar; i++) {
			if (pathSoFar[i] == target)
				return true;
		}
		return false;
	}

	private void showPath(int myPath[], int myLength) {
		for (int r = 0; r < mNumOfRows; r++) {
			for (int c = 0; c < mNumOfCols; c++) {
				if (r == mStartRow && c == mStartCol)
					System.out.print("S");
				else if (r == mEndRow && c == mEndCol)
					System.out.print("X");
				else if (myMaze[r][c] == 1)
					System.out.print("|");
				else if (beenHere(r, c, myPath, myLength))
					System.out.print("o");
				else
					System.out.print(" ");
			}
			System.out.println("");
		}
	}

	private void findPath(int row, int col, int[] pathSoFar, int lengthSoFar) {
		if (row < 0 || col < 0 || row >= mNumOfRows || col >= mNumOfCols)
			return;

		if (myMaze[row][col] == 1)
			return;

		if (beenHere(row, col, pathSoFar, lengthSoFar))
			return;

		int[] myPath = new int[lengthSoFar + 1];

		System.arraycopy(pathSoFar, 0, myPath, 0, lengthSoFar);
		myPath[lengthSoFar++] = row * mNumOfCols + col;

		if (row == mEndRow && col == mEndCol) {
			System.out.println("Found path of length: " + lengthSoFar);
			showPath(myPath, lengthSoFar);

			if (lengthSoFar <= shortestLength) {
				shortestLength = lengthSoFar;
				System.arraycopy(myPath, 0, shortestPath, 0, lengthSoFar);
				System.out.println("New Path: " + lengthSoFar);
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
		MazeRunnerDemo demo = new MazeRunnerDemo();
		int[] pathSoFar = new int[demo.mNumOfRows * demo.mNumOfCols];
		int lengthSoFar = 0;

		for (int i = 0; i < demo.mNumOfRows * demo.mNumOfCols; i++) {
			pathSoFar[i] = -1;
			demo.shortestPath[i] = -1;
		}

		demo.shortestLength = demo.mNumOfRows * demo.mNumOfCols + 1;
		lengthSoFar = 0;
		System.out.println("Here is the maze: ");

		for (int r = 0; r < demo.mNumOfRows; r++) {
			for (int c = 0; c < demo.mNumOfCols; c++) {
				if (r == demo.mStartRow && c == demo.mStartCol)
					System.out.print("S");
				else if (r == demo.mEndRow && c == demo.mEndCol)
					System.out.print("X");
				else if (demo.myMaze[r][c] == 0)
					System.out.print(" ");
				else
					System.out.print("|");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("Finding Paths ...");
		demo.findPath(demo.mStartRow, demo.mStartCol, pathSoFar, lengthSoFar);

	}
}
