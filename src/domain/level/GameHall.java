package domain.level;

import domain.objects.ObjectType;

public class GameHall {
    private ObjectType[][]  grid;

    public GameHall(int rows, int cols) {
        grid = new ObjectType[rows][cols];
    }

    public  boolean placeObject(int row, int col, ObjectType type) {
        if(isValid(row,col) && grid[row][col] == null) {
            grid[row][col] = type;
            return  true;
        }
        return false;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public ObjectType[][] getGrid() {
        return grid;
    }
}
