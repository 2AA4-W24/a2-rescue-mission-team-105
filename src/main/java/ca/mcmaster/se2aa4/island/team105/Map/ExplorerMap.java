package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.ArrayList;
import java.util.List;

public class ExplorerMap {
    List<int[]> xylist = new ArrayList<>();

    //relative position this is starting state
    private int cuurx, cuury = 0; //regular cartesian grid style coords


    //Creates a 2d array that stores MapTile Objects
    public ExplorerMap() {
    }

    //Sets a object to inputed location
    //list<int x, int y, int type:"0 =  water, 1 = land, 2 = creek,"
    public void setLocation(int xdist, int ydist, int type) {
        // Adding int arrays to the ArrayList
        this.xylist.add(new int[]{xdist, ydist, type});
    }

    //gets object from location
    public int getLocation(int xdist, int ydist) {
        for (int[] location : this.xylist) {
            int x = location[0];
            int y = location[1];
            if (x == xdist && y == ydist) {
                // Match found, return the type (for demonstration purposes, you can modify as needed)
                int type = location[2];
                return (type);
            }
        }
        return(-1);
    }


}
