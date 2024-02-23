package ca.mcmaster.se2aa4.island.team105.Map;


public class ExplorerMap {
    private MapTile[][] map;

    public ExplorerMap(int height, int width) {
        map = new MapTile[height][width];
    }

    public void setLocation(int row, int col, MapTile tile) {
        map[row][col] = tile;
    }

    public MapTile getLocation(int row, int col) {
        return map[row][col];
    }


}
