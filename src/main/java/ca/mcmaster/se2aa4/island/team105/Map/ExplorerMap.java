package ca.mcmaster.se2aa4.island.team105.map;
import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

public class ExplorerMap{

    //relative position this is starting state
    private Point currentPoint = new Point(0, 0);
    private Map<Point, String> mapLayout = new HashMap<>();

    //Sets a object to inputed location
    public void setLocation(int xdist, int ydist, String infoString) {
        // Adding int arrays to the hashmap
        Point targetPoint = new Point(currentPoint);
        targetPoint.translate(xdist, ydist);
        mapLayout.put(new Point(targetPoint), infoString);
    }

    public void setEchoInfo(int xdist, int ydist, boolean landFound){
        Point targetPoint = new Point(currentPoint);
        int xStep = Integer.compare(xdist, 0);
        int yStep = Integer.compare(ydist, 0);
        for (int i = 0; i < Math.abs(xdist) + Math.abs(ydist); i++) {
            targetPoint.translate(xStep, yStep);
            mapLayout.put(new Point(targetPoint), "Ocean");
        }
        targetPoint.translate(-xStep, -yStep);
        mapLayout.put(new Point(targetPoint), landFound ? "Land" : "OB");
    }

    //gets object from location
    public String getLocation(int xdist, int ydist) {
        Point targetPoint = new Point(xdist, ydist);
        return mapLayout.getOrDefault(targetPoint, "Information not found");
    }
    // updates point
    public void updateCurrentPoint(int xdist, int ydist){
        currentPoint.translate(xdist, ydist);
    }
}
