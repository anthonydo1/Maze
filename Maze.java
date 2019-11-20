package Do.cs146.project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Maze {

    private int size;
    private Cell[][] maze;
    private Random random;
    private ArrayList<Cell> visited;
    private ArrayList<Cell> path;
    private int pathLength;
    private int visitedCells;
    
    public Maze(int size) {
        this.size = size;
        maze = new Cell[size][size];
        random = new Random();
        pathLength = 0;
        visitedCells = 0;
    }


    public void generateMaze() {
        generateCells();
        
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
    
    
    private static void removeAdjacentWall(Cell cell, Cell currentCell) {
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
    
    
    public void DFS() {
        int order = -1;
        visited = new ArrayList<>();
        Stack<Cell> stack = new Stack<>();
        
        stack.add(maze[0][0]);
        boolean found = false;
        
        while (found == false) {
            Cell current = stack.peek();    
            
            if (current == maze[size - 1][size - 1]) {
                found = true;
                path = new ArrayList<>(stack);
            }
            
            if (!visited.contains(current)) {
                visited.add(current);
                current.setOrder(-1);
                current.setOrder(++order);
            }
            
            ArrayList<Cell> neighbors = current.getNeighbors();
            boolean debounce = false;
            
            for (int i = 0; i < neighbors.size() && debounce == false; i++) {
                Cell cell = neighbors.get(i);
                
                if (!visited.contains(cell)) {
                	if (current.getRow() == cell.getRow() + 1 && !current.getNorthWall() && !cell.getSouthWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getColumn() == cell.getColumn() + 1 && !current.getWestWall() && !cell.getEastWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getRow() == cell.getRow() - 1 &&  !current.getSouthWall() && !cell.getNorthWall()) {
                        debounce = true;
                        stack.push(cell);
                    } else if (current.getColumn() == cell.getColumn() - 1 && !current.getEastWall() && !cell.getWestWall()) {
                        debounce = true;
                        stack.push(cell);
                    }
                }
            }
            
            if (debounce == false && stack.size() > 0) {
                stack.pop();
            } 
        }
        
        pathLength = path.size();
        visitedCells = visited.size();
    }
    
   
    public void printMaze() {
        maze[0][0].removeNorthWall();
        maze[size - 1][size - 1].removeSouthWall();

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
                if (cell.getOrder() != -1) {
                    int order = cell.getOrder() % 10;
                    if (cell.getEastWall() == true) {
                        System.out.print(order + "|");
                    } else if (cell.getEastWall() == false) {
                        System.out.print(order + " ");
                    }
                } else if (cell.getEastWall() == true) {
                    System.out.print(" |");
                } else if (cell.getEastWall() == false) {
                    System.out.print("  ");
                }
            }
        }

        System.out.print("\n");
        System.out.print("+");

        for (int j = 0; j < size; j++) {
            Cell cell = maze[size - 1][j];
            if (cell.getSouthWall() == true) {
                System.out.print("-+");
            } else {
                System.out.print(" +");
            }
        }

    }

    
    public void printPath() {
        maze[0][0].removeNorthWall();
        maze[size - 1][size - 1].removeSouthWall();

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
                if (path != null) {
                    if (path.contains(cell) && cell.getEastWall() == true) {
                        System.out.print("#|");
                    } else if (path.contains(cell) && cell.getEastWall() == false) {
                        System.out.print("# ");
                    } else if (cell.getEastWall() == true) {
                        System.out.print(" |");
                    } else if (cell.getEastWall() == false) {
                        System.out.print("  ");
                    }
                } else {
                    if (cell.getEastWall() == true) {
                        System.out.print(" |");
                    } else if (cell.getEastWall() == false) {
                        System.out.print("  ");
                    }
                }
                
            }
        }

        System.out.print("\n");
        System.out.print("+");

        for (int j = 0; j < size; j++) {
            Cell cell = maze[size - 1][j];
            if (cell.getSouthWall() == true) {
                System.out.print("-+");
            } else {
                System.out.print(" +");
            }
        }
    }
    
    
    public void printVisited() {
        System.out.println("\n");
        for (int i = 0; i < visited.size(); i++) {
            System.out.println(visited.get(i).getColumn() + " " + visited.get(i).getRow());
        }
    }
    
    
    public static void main(String[] args) {
        Maze maze1 = new Maze(10);
        maze1.generateMaze();
        maze1.printMaze();
        System.out.println("\n");
        maze1.DFS();
        maze1.printPath();
        System.out.println("\n");
        maze1.printMaze();
        System.out.println("\n");
        System.out.println("Length of path: " + maze1.pathLength);
        System.out.println("Visited cells: " + maze1.visitedCells);
        
    }
}