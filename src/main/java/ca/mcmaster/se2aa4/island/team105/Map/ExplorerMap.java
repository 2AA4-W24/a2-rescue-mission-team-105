package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.ArrayList;
import java.util.List;

public class ExplorerMap {
    //list<int x, int y, int type:"0 =  water, 1 = land, 2 = creek,"
    List<int[]> xylist = new ArrayList<>();

    //relative position this is starting state
    private int cuurx, cuury = 0; //regular cartesian grid style coords

    //Sets a object to inputed location
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
                // Match found return the type 
                int type = location[2];
                return (type);
            }
        }
        //returns -1 if it was not able to find the locations
        return(-1);
    }


}
