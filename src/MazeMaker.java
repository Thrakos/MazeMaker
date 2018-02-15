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

	static ArrayList<Cell> cells = new ArrayList<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// select a random cell to start
		Random rand = new Random();
		int ranW = rand.nextInt(w);
		int ranH = rand.nextInt(h);
		
		maze.getCell(rand.nextInt(width), 0).setNorthWall(false);
		maze.getCell(rand.nextInt(width), height - 1).setSouthWall(false);

		// call selectNextPath method with the randomly selected cell
		curCell = maze.getCell(ranW, ranH);
		selectNextPath(curCell);

		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		
		// mark current cell as visited
		currentCell.setBeenVisited(true);

		// check for unvisited neighbors
		int curX = currentCell.getX();
		int curY = currentCell.getY();

		cells = getUnvisitedNeighbors(maze.getCell(curX, curY));

		// if has unvisited neighbors,
		// select one at random.
		// push it to the stack
		// remove the wall between the two cells
		// make the new cell the current cell and mark it as visited

		Random rand = new Random();

		if (!cells.isEmpty()) {

			int ran = rand.nextInt(cells.size());
			uncheckedCells.push(cells.get(ran));
			removeWalls(curCell, cells.get(ran));
			curCell = cells.get(ran);
			selectNextPath(curCell);

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

		int curX = c1.getX();
		int curY = c1.getY();

		if (c2.getX() == c1.getX()) {
			if (c2.getY() > c1.getY()) {
				c1.setSouthWall(false);
				c2.setNorthWall(false);
			} else {
				c1.setNorthWall(false);
				c2.setSouthWall(false);
			}
		} else {
			if (c2.getX() > c1.getX()) {
				c1.setEastWall(false);
				c2.setWestWall(false);
			} else {
				c1.setWestWall(false);
				c2.setEastWall(false);
			}
		}
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {

		ArrayList<Cell> cells = new ArrayList<Cell>();

		int curX = c.getX();
		int curY = c.getY();

		System.out.println(curX);
		System.out.println(curY);

		if (curX > 0 && curX < width - 1 && !maze.getCell(curX + 1, curY).hasBeenVisited()) {
			cells.add(maze.getCell(curX + 1, curY));
		} else if (curX < width && curX > 0 && !maze.getCell(curX - 1, curY).hasBeenVisited()) {
			cells.add(maze.getCell(curX - 1, curY));
		}
		if (curY > 0 && curY < height - 1 && !maze.getCell(curX, curY + 1).hasBeenVisited()) {
			cells.add(maze.getCell(curX, curY + 1));
		} else if (curY < height && curY > 0 && !maze.getCell(curX, curY - 1).hasBeenVisited()) {
			cells.add(maze.getCell(curX, curY - 1));
		}
		return cells;
	}
}