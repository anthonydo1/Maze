package Do.cs146.project3;

import java.util.ArrayList;

public class Cell {
	/**
	 * row: stores the row 
	 * column: stores the column
	 * order: stores the order at which the cell was visited in the algorithm
	 * north,south,west,east: represent the walls of the cell, true if wall exists and fall in wall doesn't exist
	 * neighbors: stores all the neighbors of this cell
	 * head: stores the cell that lead to the discovery of this cell
	 */
    private int row, column, order;
    private boolean north, south, west, east;
    private ArrayList<Cell> neighbors;
    private Cell head;
    
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.north = true;
        this.south = true;
        this.east = true;
        this.west = true;
        this.neighbors = new ArrayList<>();
        this.order = -1;
        head = null;
        
    }
    /**
     * 
     * @param num set order to this integer 
     */
    public void setOrder(int num) {
        order = num;
    }

    /**
     * 
     * @return get the order
     */
    public int getOrder() {
        return order;
    }
    /**
     * 
     * @param cell add this cell as neighbor to this cell
     */
    public void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }
    
    /**
     * 
     * @return get all the neighbors of this cell in ArrayList
     */
    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }
    
    /**
     * 
     * @param head set the head of this cell
     */
    public void setHead(Cell head)
    {
    	this.head = head;
    }
    
    /**
     * 
     * @return obtain the head of this cell
     */
    public Cell getHead()
    {
    	return head;
    }
    
    /**
     * 
     * @return true if the cell has all its walls, false if even one wall is false
     */
    public boolean hasFullWalls() {
        return north && south && east && west;
    }
    
    /**
     * 
     * @return the row of this cell
     */
    public int getRow() {
        return row;
    }
    
    /**
     * 
     * @return the column of this cell
     */
    public int getColumn() {
        return column;
    }  
    /**
     * remove the north wall of this cell
     */
    public void removeNorthWall() {
        north = false;
    }
    
    /**
     * remove the south wall of this cell
     */
    public void removeSouthWall() {
        south = false;
    }

    /**
     * remove the east wall of this cell
     */
    public void removeEastWall() {
        east = false;
    }
    
    /**
     * remove the west wall of this cell
     */
    public void removeWestWall() {
        west = false;
    }  
    
    /**
     * 
     * @return true is the wall exist, else, false
     */
    public boolean getNorthWall() {
        return north;
    }
   
    /**
     * 
     * @return true is the wall exist, else, false
     */
    public boolean getSouthWall() {
        return south;
    }
    
    /**
     * 
     * @return true is the wall exist, else, false
     */
    public boolean getEastWall() {
        return east;
    }
    
    /**
     * 
     * @return true is the wall exist, else, false
     */
    public boolean getWestWall() {
        return west;
    }
    
    /**
     * removes the adjacent was of two bordering cells.
     * @param cell the neighbor cell
     * @param currentCell the current cell as its running the program
     */
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
}