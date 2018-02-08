import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	private static Cell curCell;

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// select a random cell to start
		Random rand = new Random();
		int ranW = rand.nextInt(w);
		int ranH = rand.nextInt(h);

		// call selectNextPath method with the randomly selected cell
		curCell = maze.getCell(ranW, ranH);
		selectNextPath(maze.getCell(ranW, ranH));

		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);

		// check for unvisited neighbors

		Random rand = new Random();
		ArrayList<Cell> cells = new ArrayList<Cell>();

		int curX = currentCell.getX();
		int curY = currentCell.getY();

		getUnvisitedNeighbors(maze.getCell(curX, curY));

		// if has unvisited neighbors,
		// select one at random.
		// push it to the stack
		// remove the wall between the two cells
		// make the new cell the current cell and mark it as visited
		if (!cells.isEmpty()) {

			int ran = rand.nextInt(cells.size());
			curCell = cells.get(ran);
			selectNextPath(curCell);
			uncheckedCells.push(cells.get(ran));

			if (cells.get(ran).getX() == curX) {
				if (cells.get(ran).getY() < curY) {
					currentCell.setSouthWall(false);
					cells.get(ran).setNorthWall(false);
				} else {
					currentCell.setNorthWall(false);
					cells.get(ran).setSouthWall(false);
				}
			} else {
				if (cells.get(ran).getX() < curX) {
					currentCell.setWestWall(false);
					cells.get(ran).setEastWall(false);
				} else {
					currentCell.setEastWall(false);
					cells.get(ran).setWestWall(false);
				}
			}
			selectNextPath(cells.get(ran));
		} else {
			if (!uncheckedCells.isEmpty()) {
				curCell = uncheckedCells.pop();
				selectNextPath(curCell);
			}
		}

		// call the selectNextPath method with the current cell

		// if all neighbors are visited
		// if the stack is not empty
		// pop a cell from the stack
		// make that the current cell

		// call the selectNextPath method with the current cell
	}

	private static void removeWalls(Cell c1, Cell c2) {

	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {

		ArrayList<Cell> cells = new ArrayList<Cell>();

		int curX = curCell.getX();
		int curY = curCell.getY();

		if (curX > 0 && !maze.getCell(curX + 1, curY).hasBeenVisited()) {
			cells.add(maze.getCell(curX + 1, curY));
		} else if (curX < width && curX > 0 && !maze.getCell(curX - 1, curY).hasBeenVisited()) {
			cells.add(maze.getCell(curX - 1, curY));
		}
		if (curY > 0 && !maze.getCell(curX, curY + 1).hasBeenVisited()) {
			cells.add(maze.getCell(curX, curY + 1));
		} else if (curY < height && curY > 0 && !maze.getCell(curX, curY - 1).hasBeenVisited()) {
			cells.add(maze.getCell(curX, curY - 1));
		}
		return cells;
	}
}