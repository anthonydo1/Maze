package Do.cs146.project3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * the following class is used to find the solution to the maze using the Depth first search algorithm
 * @author Rahul Krishan & Anthony Do
 *
 */
public class BFS {
	
	/**
	 * the BFS search is implemented as a static function.
	 * @param maze the maze that the program should solve using BFS
	 */
	public static void BFSearch(Maze maze) {
		maze.pathLength = 0;
        maze.visitedCells = 0;
        int order = -1;
        maze.visited = new ArrayList<>();
        Queue<Cell> queue = new LinkedList<>(); //stores the cell neighbors are visited to explore thier neighbors 
        										//according to the order they were discovered first
        Stack<Cell> stack = new Stack<>();	//store the unique solution of the maze
        maze.solutionType = "BFS";
        
        queue.add(maze.maze[0][0]);
        boolean found = false;
        
        while (found == false && !queue.isEmpty()) {
            Cell current = queue.remove();    
            //if end is reach, exit loop.
            if (current == maze.maze[maze.size - 1][maze.size - 1]) {
                found = true;
            }
         // if the current cell has not been visited already, add it the visited ArrayList
            if (!maze.visited.contains(current)) {
                maze.visited.add(current);
                current.setOrder(++order);
            }
            
            ArrayList<Cell> neighbors = current.getNeighbors();
            for (int i = 0; i < neighbors.size(); i++) {
                Cell cell = neighbors.get(i);
              //check which neighbor is missing a bordering wall so the algorithm can continue its search
                //for a solution in that path. adds all neighbors to queue to explore as well
                if (!maze.visited.contains(cell)) {
                    if (current.getColumn() == cell.getColumn() + 1 && !current.getWestWall() && !cell.getEastWall()) {
                    	cell.setHead(current);
                        queue.add(cell);
                    } else if (current.getRow() == cell.getRow() - 1 &&  !current.getSouthWall() && !cell.getNorthWall()) {
                    	cell.setHead(current);
                        queue.add(cell);
                    } else if (current.getRow() == cell.getRow() + 1 && !current.getNorthWall() && !cell.getSouthWall()) {
                    	cell.setHead(current);
                        queue.add(cell);
                    } else if (current.getColumn() == cell.getColumn() - 1 && !current.getEastWall() && !cell.getWestWall()) {
                    	cell.setHead(current);
                        queue.add(cell);
                    }
                }
            }
        
        }
        //start from the end of maze and iterate back to start to get the direct path
        Cell it = maze.maze[maze.size - 1][maze.size - 1];
        while(it != null)
        {
        	stack.add(it);
        	it = it.getHead();
        }
        maze.path = new ArrayList<>(stack); // store the path into maze.path
        maze.pathLength = maze.path.size();
        maze.visitedCells = maze.visited.size();
    }
    

}
