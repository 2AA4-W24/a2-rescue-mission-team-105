package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.HashMap;
import java.util.List;
import java.awt.Point;

public class ExplorerMap {

    //relative position this is starting state
    Point currentPoint = new Point(0, 0);

    //Creates a 2d array that stores information Objects
    HashMap<Point, String> mapLayout = new HashMap<>();
    public ExplorerMap() {
    }


    //Sets a object to inputed location
    //list<int x, int y, int type:"0 =  water, 1 = land, 2 = creek,"
    public void setLocation(int xdist, int ydist, String infoString) {
        // Adding int arrays to the hashmap
        Point targetPoint = currentPoint.getLocation();
        targetPoint.translate(xdist, ydist);
        mapLayout.put(targetPoint, infoString);
    }

    //gets object from location
    public String getLocation(int xdist, int ydist) {
        Point targetPoint = new Point(xdist, ydist);
        String result = mapLayout.get(targetPoint);
        return result;
    }

    public void updateCurrentPoint(int xdist, int ydist){
        currentPoint.translate(xdist, ydist);
    }
}
