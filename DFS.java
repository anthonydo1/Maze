package Do.cs146.project3;

import java.util.ArrayList;
import java.util.Stack;
/**
 * the following class is used to find the solution to the maze using the Depth first search algorithm
 * @author Rahul Krishan & Anthony Do
 *
 */
public class DFS {

	/**
	 * the DFS search is implemented as a static function.
	 * @param maze the maze that the program should solve using DFS
	 */
	public static void DFSearch(Maze maze) {
        int order = -1;
        maze.pathLength = 0;
        maze.visitedCells =0;
        maze.visited = new ArrayList<>();
        Stack<Cell> stack = new Stack<>(); // as each cell is visited, it is added to the stack in case a solution is
        									//not found going that path and program can "reverse its steps"
        maze.solutionType = "DFS";
        
        stack.add(maze.maze[0][0]);
        boolean found = false;
        
        while (found == false) {
            Cell current = stack.peek();    
            //check if the end is found, end loop if so
            if (current == maze.maze[maze.size - 1][maze.size - 1]) {
                found = true;
                maze.path = new ArrayList<>(stack);
            }
            // if the current cell has not been visited already, add it the visited ArrayList
            if (!maze.visited.contains(current)) {
                maze.visited.add(current);
                current.setOrder(-1);
                current.setOrder(++order);
            }
            
            ArrayList<Cell> neighbors = current.getNeighbors();
            boolean debounce = false;
            
            for (int i = 0; i < neighbors.size() && debounce == false; i++) {
                Cell cell = neighbors.get(i);
                //check which neighbor is missing a bordering wall so the algorithm can continue its search
                //for a solution in that path.
                if (!maze.visited.contains(cell)) {
                    if (current.getColumn() == cell.getColumn() + 1 && !current.getWestWall() && !cell.getEastWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getRow() == cell.getRow() - 1 &&  !current.getSouthWall() && !cell.getNorthWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getRow() == cell.getRow() + 1 && !current.getNorthWall() && !cell.getSouthWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getColumn() == cell.getColumn() - 1 && !current.getEastWall() && !cell.getWestWall()) {
                        debounce = true;
                        stack.push(cell);
                    }
                }
            }
            //if the algorithm hits a dead end and no neighbors are available that will lead to the 
            // end of maze, retrace step back to cell that had more than one potential neighbors for finding solution
            if (debounce == false && stack.size() > 0) {
                stack.pop();
            } 
        }
        
        maze.pathLength = maze.path.size();
        maze.visitedCells = maze.visited.size();
    }

}
