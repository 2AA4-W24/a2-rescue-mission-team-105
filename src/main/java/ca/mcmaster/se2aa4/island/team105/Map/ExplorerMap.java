package ca.mcmaster.se2aa4.island.team105.Map;


public class ExplorerMap {
    private String[][] map;

    //relative position this is starting state
    private int x, y = 0; //regular cartesian grid style coords


    //Creates a 2d array that stores MapTile Objects
    public ExplorerMap(int height, int width) {
        map = new String[height][width];
    }

    //Sets a object to inputed location
    public void setLocation(int xdist, int ydist, String type) {

    }

    //gets object from location
    public String getLocation(int xdist, int ydist) {
        return "works";
    }


}
