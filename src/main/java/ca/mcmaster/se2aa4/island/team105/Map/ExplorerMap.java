package ca.mcmaster.se2aa4.island.team105.Map;

public class ExplorerMap {
    private MapTile[][] map;

    // Creates a 2d array that stores MapTile Objects
    public ExplorerMap(int height, int width) {
        map = new MapTile[height][width];
    }

    // Sets a object to inputed location
    public void setLocation(int row, int col, MapTile tile) {
        map[row][col] = tile;
    }

    // gets object from location
    public MapTile getLocation(int row, int col) {
        return map[row][col];
    }

    

}


