package Do.cs146.project3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Rahul Krishan & Anthony Do
 * The purpose of this program is to create mazes, find solutions to them, and
 * then store them into a file. Each maze is created randomly. The program is also able to read mazes from a file and find
 * solutions to those read mazes. The program uses breadth first search or depth first searches
 * to find the solution to the maze. Each maze has a unique solution. 
 * The program store the maze, the process of finding the solution and the path of getting from
 * starting point to end the maze into a file.
 * By default, the start of the maze is the very first cell of the maze at maze[0][0] and
 * exit is the very last cell in the maze[size-1][size-1]
 */

public class Maze {
/**
 * size: stores the size of the maze, EX. a maze is comprised of n columns and m rows, in this program
 * 		 the columns and rows are always equal hence represented by on variable size
 * maze: the collection of cells that collectively make up the maze
 * random: Each maze is created randomly by using this random variable
 * visited: stores all the cells that are visited of the maze in the process of finding a solution
 * path: store the exact solution to the maze, after the program uses its searching algorithms to 
 * 			find a solution, that solution is save in here.
 * pathLength: the length of solution to the maze
 * visitedCells: the number of total cells that were visited in attempt to find the solution
 * solutionType: store the algorithm used to solve the maze
 * write: boolean to represent whether it is the first time output is written to a file, that way
 * 			the program knows whether the new output should replace in the output file or append to it
 * 
 */
    protected int size; 
    protected Cell[][] maze;
    protected ArrayList<Cell> visited;
    protected ArrayList<Cell> path;
    protected int pathLength = 0;
    protected int visitedCells = 0;
    protected String solutionType = "";
    private boolean write = false;
    
    /**
     * the constructor creates a random maze of the size provided. 
     * @param size: the size of how big of a maze should be created 
     */
    public Maze(int size) {
        this.size = size;
        maze = new Cell[size][size];
        generateMaze();
    }
    
    /**
     * the constructor can read from a file and create a maze representing the data in the file
     * @param file the file from which the maze should be read
     * @throws FileNotFoundException if the said file is not found, this exception is thrown
     */
    public Maze(File file) throws FileNotFoundException {
    	
    	Scanner scan = new Scanner(file);
    	if(scan.hasNextLine()) {
    		size = scan.nextInt();
    		maze = new Cell[size][size];
    		generateCells();
    		scan.nextLine(); 
    	}
    	if(scan.hasNextLine()) scan.nextLine(); //remove first line
    	maze[0][0].removeNorthWall(); //by default, start is the first cell in the maze
    	int m = 0;
    	
    	/*
    	 * the following code reads a maze from a file, the format of the input is known therefore
    	 * the code is optimized for the expected input
    	 * the following code reads the input and replicates if by modifying its own cells to match
    	 * the input. Walls of the cells are removed according to the input.
    	 */
    	
    	while(scan.hasNextLine())
    	{
    		String input = scan.nextLine();
    		String input2 = "" ;
    		if(scan.hasNextLine()) input2 = scan.nextLine();
    		
    		for(int i = 2, k = 1, j=0; j < size; j++, i=i+2,k = k+2)
    		{
    			if(input.length()>1) 
    			{
	    			if(input.charAt(i) != '|')
	    			{
	    				maze[m][j].removeEastWall();
	    				if(j+1<size)
	    					maze[m][j+1].removeWestWall();
	    			}
    			}
    			if(input2.length()>1)
    			{
	    			if(input2.charAt(k) != '-')
	    			{
	    				maze[m][j].removeSouthWall();
	    				if(m+1 < size) 
	    					maze[m+1][j].removeNorthWall();
	    			}
    			}
    		}
    		m++;
    	}
    	scan.close();
    }
    
    /**
     * random: used to decide which neighbor of the current Cell should the bordering wall
     * 			of current be remove. Chosen randomly to create a random maze
     * cellStack: stores cells whose wall has just been removed
     * totalCell: number of total Cells in the maze 
     * currentCell: the current cell the program is at as it modifies the cells of the maze
     * visitedCells: tracks how many cells have been visited to ensure all cells are visited.
     */

    public void generateMaze() {
        generateCells();
        Random random = new Random();
        Stack<Cell> cellStack = new Stack<>();
        int totalCells = size * size;
        Cell currentCell = maze[0][0];
        int visitedCells = 1;

        /*
         * neighbors stores the neighbors of current cell into an ArrayList
         * cells: stores all neighbors of current with all for walls to see which bordering wall
         * should removed.
         */
        while (visitedCells < totalCells) //while all cells have not been visited
        {
            ArrayList<Cell> neighbors = currentCell.getNeighbors();
            ArrayList<Cell> cells = new ArrayList<>();

            for (int i = 0; i < neighbors.size(); i++) 
            {
                if (neighbors.get(i).hasFullWalls())
                {
                    cells.add(neighbors.get(i));
                }
            }

            if (cells.size() > 0) 
            {
                Cell cell = cells.get(random.nextInt(cells.size())); //randomly choose neighbor to remove bordering wall
                Cell.removeAdjacentWall(cell, currentCell); 
                cellStack.push(cell); 
                currentCell = cell; //move to the cell whose wall was removed and repeat the process
                visitedCells++;
            }
            else 
			{
                currentCell = cellStack.pop(); //if a cell has no neighbors that have not been visited already, go back to previous cell
            }
        }
    }
    
    
    /**
     * resets the order of the cells in the maze so it doesn't conflict with the results of 
     * a different algorithm that might be used to a solution of the same maze
     */
    public void resetOrder()
    {
    	for(int i = 0; i < size; i++)
    	{
    		for(int k = 0; k < size; k++)
    		{
    			maze[i][k].setOrder(-1);
    		}
    	}
    }
    
    /**
     * initialized each cell in the maze and also finds all its neighbors and stores into ArrayList 
     * that is a member variable of that cell
     */
    private void generateCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];

                boolean northCell = (i - 1 >= 0) && (i - 1 < size) && (j >= 0) && (j < size);
                boolean southCell = (i + 1 >= 0) && (i + 1 < size) && (j >= 0) && (j < size);
                boolean eastCell = (i >= 0) && (i < size) && (j + 1 >= 0) && (j + 1 < size);
                boolean westCell = (i >= 0) && (i < size) && (j - 1 >= 0) && (j - 1 < size);
                
                if (eastCell)
                    cell.addNeighbor(maze[i][j + 1]);
                
                if (southCell)
                    cell.addNeighbor(maze[i + 1][j]);
                
                if (westCell)
                    cell.addNeighbor(maze[i][j - 1]);
                
                if (northCell)
                    cell.addNeighbor(maze[i - 1][j]);
            }
        }
    }
    
    /**
     * 
     * prints the maze to an output file, if the maze has not been solved, prints an empty.
     * if the maze has been solved, prints the order at which each cell was visited 
     * in the process of a finding a solution
     * @throws IOException if file being written to is not successfully open, throws Exception
     */
    public void printMaze() throws IOException {
       maze[0][0].removeNorthWall();
       maze[size - 1][size - 1].removeSouthWall();
        //write the maze to an output file
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt",write));
        if(write == false && solutionType.equals("")) writer.write(String.valueOf(this.size));
        else writer.write(solutionType);
        write = true;
        //print the maze as ASCII based on the walls of the cells
        for (int i = 0; i < size; i++) {
        		writer.newLine();
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                if (cell.getNorthWall() == true) {
                   writer.write("+-");
                } else if (cell.getNorthWall() == false) {
                	writer.write("+ ");
                }
            }

            writer.write("+");
            writer.newLine();
            writer.write("|");

            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                if (cell.getOrder() != -1) {
                    int order = cell.getOrder() % 10; //to keep maze looking clean, visited order kept to below 10 while printing maze
                    if (cell.getEastWall() == true) {
                    	writer.write(order + "|");
                    } else if (cell.getEastWall() == false) {
                    	writer.write(order + " ");
                    }
                } else if (cell.getEastWall() == true) {
                	writer.write(" |");
                } else if (cell.getEastWall() == false) {
                	writer.write("  ");
                }
            }
        }

        writer.newLine();
        writer.write("+");

        for (int j = 0; j < size; j++) {
            Cell cell = maze[size - 1][j];
            if (cell.getSouthWall() == true) {
            	writer.write("-+");
            } else {
            	writer.write(" +");
            }
        }
        	writer.newLine();
        
        writer.close();
    }

    /**
     * the following code prints the direct path from start of maze to the end of maze. 
     * basically prints the solution
     * @throws IOException if the file is not successfully written to
     */
    public void printPath() throws IOException {
    	
        maze[0][0].removeNorthWall();
        maze[size - 1][size - 1].removeSouthWall();
       BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt",true));
     	writer.write(solutionType);
        for (int i = 0; i < size; i++) {
            writer.newLine();
            for (int j = 0; j < size; j++) {
                
                Cell cell = maze[i][j];
                
                if (cell.getNorthWall() == true) {
                    writer.write("+-");
                } else if (cell.getNorthWall() == false) {
                    writer.write("+ ");
                }
            }

            writer.write("+");
            writer.newLine();
            writer.write("|");

            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                if (path != null) {
                    if (path.contains(cell) && cell.getEastWall() == true) {
                    	 writer.write("#|");
                    } else if (path.contains(cell) && cell.getEastWall() == false) {
                    	 writer.write("# ");
                    } else if (cell.getEastWall() == true) {
                    	 writer.write(" |");
                    } else if (cell.getEastWall() == false) {
                    	 writer.write("  ");
                    }
                } else {
                    if (cell.getEastWall() == true) {
                    	 writer.write(" |");
                    } else if (cell.getEastWall() == false) {
                    	 writer.write("  ");
                    }
                }
                
            }
        }

        writer.newLine();
        writer.write("+");

        for (int j = 0; j < size; j++) {
            Cell cell = maze[size - 1][j];
            if (cell.getSouthWall() == true) {
            	 writer.write("-+");
            } else {
            	 writer.write(" +");
            }
        }
        writer.newLine();
        writer.close();
   
    }
     
    /**
     * the following code can be used to see exact outputs of the program
     * @param args none used
     * @throws IOException if the file being written to is not opened
     */
    public static void main(String[] args) throws IOException
    {
       Maze maze = new Maze(20);
       maze.printMaze();
       DFS.DFSearch(maze);
       maze.printMaze();
       maze.printPath();
       maze.resetOrder();
       BFS.BFSearch(maze);
       maze.printMaze();
       maze.printPath();

        
    }
    
}