package Do.cs146.project3;

import java.util.ArrayList;

public class Cell {
    private int row;
    private int column;
    private boolean north;
    private boolean south;
    private boolean east;
    private boolean west;
    private ArrayList<Cell> neighbors;
    private int order;
    
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.north = true;
        this.south = true;
        this.east = true;
        this.west = true;
        this.neighbors = new ArrayList<>();
        this.order = -1;
    }
    
    public void setOrder(int num) {
        order = num;
    }
    
    
    public int getOrder() {
        return order;
    }
    
    public void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }
    

    public ArrayList<Cell> getNeighbors() {
        return neighbors;
    }
    
    
    public boolean hasFullWalls() {
        return north && south && east && west;
    }
    
    public int getRow() {
        return row;
    }
    
    
    public int getColumn() {
        return column;
    }
    
    
    public void removeNorthWall() {
        north = false;
    }
    
    
    public void removeSouthWall() {
        south = false;
    }
    
    
    public void removeEastWall() {
        east = false;
    }
    
    
    public void removeWestWall() {
        west = false;
    }
    
    
    public boolean getNorthWall() {
        return north;
    }
    
    
    public boolean getSouthWall() {
        return south;
    }
    
    
    public boolean getEastWall() {
        return east;
    }
    
    
    public boolean getWestWall() {
        return west;
    }
}
