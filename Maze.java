package Do.cs146.project3;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
    
    private int size;
    private Cell[][] maze;
    private Random random;
    
    public Maze(int size) {
        this.size = size;
        maze = new Cell[size][size];
        random = new Random();
    }
    
    public void generateCells() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                
                boolean northCell = (i + 1 >= 0) && (i + 1 < size) && (j >= 0) && (j < size);
                boolean southCell = (i - 1 >= 0) && (i - 1 < size) && (j >= 0) && (j < size);
                boolean eastCell = (i >= 0) && (i < size) && (j + 1 >= 0) && (j + 1 < size);
                boolean westCell = (i >= 0) && (i < size) && (j - 1 >= 0) && (j - 1 < size);
                
                if (northCell) cell.addNeighbor(maze[i+1][j]);
                if (southCell) cell.addNeighbor(maze[i-1][j]);
                if (eastCell) cell.addNeighbor(maze[i][j+1]);
                if (westCell) cell.addNeighbor(maze[i][j-1]);
            }
        }
    }
    
    public void generateMaze() {
        Stack<Cell> cellStack = new Stack<>();
        int totalCells = size * size;
        Cell currentCell = maze[0][0];
        int visitedCells = 1;
        
        
        while (visitedCells < totalCells) {
            ArrayList<Cell> neighbors = currentCell.getNeighbors();
            ArrayList<Cell> cells = new ArrayList<>();
            
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).hasFullWalls()) {
                    cells.add(neighbors.get(i));
                }
            }
            
            if (cells.size() > 0) {
                Cell cell = cells.get(random.nextInt(cells.size()));
                removeAdjacentWall(cell, currentCell);
                cellStack.push(cell);
                currentCell = cell;
                visitedCells++;
            } else {
                currentCell = cellStack.pop();
            }
        }
    }
    
    
    public static void removeAdjacentWall(Cell cell, Cell currentCell) {
        int cellRow = cell.getRow();
        int cellColumn = cell.getColumn();
        
        int currentCellRow = currentCell.getRow();
        int currentCellColumn = currentCell.getColumn();
        
        if (cellRow - currentCellRow == 1) { 
            cell.removeNorthWall();
            currentCell.removeSouthWall();
        } else if (cellRow - currentCellRow == -1) {
            cell.removeSouthWall();
            currentCell.removeNorthWall();
        } else if (cellColumn - currentCellColumn == 1) {
            cell.removeWestWall();
            currentCell.removeEastWall();
        } else if (cellColumn - currentCellColumn == -1) {
            cell.removeEastWall();
            currentCell.removeWestWall();
        }
    }
    
    
    
    public void printMaze() {
        maze[0][0].removeNorthWall();
        maze[size - 1][size - 1].removeSouthWall();
        /*
        for (int i = 0; i < size; i++) {
            System.out.println("\n");
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                System.out.print("\n");
                System.out.print(" North: " + cell.getNorthWall());
                System.out.print(" South: " + cell.getSouthWall());
                System.out.print(" East: " + cell.getEastWall());
                System.out.print(" West: " + cell.getWestWall());
            }
        }
        */
        
        for (int i = 0; i < size; i++) {
            System.out.print("\n");
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                if (cell.getNorthWall() == true) {
                    System.out.print("+-");
                } else if (cell.getNorthWall() == false) {
                    System.out.print("+ ");
                }
            }
            
            System.out.print("+");
            System.out.print("\n");
            System.out.print("|");
            
            for (int j = 0; j < size; j++) {
                Cell cell = maze[i][j];
                if (cell.getEastWall() == true) {
                    System.out.print(" |");
                } else if (cell.getEastWall() == false) {
                    System.out.print("  ");
                }
            }
        }
        
        System.out.print("\n");
        System.out.print("+");
        
        for (int j = 0; j < size; j++) {
            Cell cell = maze[size-1][j];
            if (cell.getSouthWall() == true) {
                System.out.print("-+");
            } else {
                System.out.print(" +");
            }
        }
        
    }
    
    public static void main(String[] args) {
        Maze maze1 = new Maze(10);
        maze1.generateCells();
        maze1.generateMaze();
        maze1.printMaze();
    }
}
