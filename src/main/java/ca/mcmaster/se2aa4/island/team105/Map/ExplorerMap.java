package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import ca.mcmaster.se2aa4.island.team105.Drone.Drone;

import java.awt.Point;

public class ExplorerMap extends SubObserver {

    //relative position this is starting state
    private Point currentPoint = new Point(0, 0);

    //Creates a 2d array that stores information Objects
    public HashMap<Point, String> mapLayout = new HashMap<>();
    private Drone drone;
    private boolean siteFound = false;
    private Point siteCoordinate;

    
    public ExplorerMap(Drone drone) {
        currentPoint.setLocation(0, 0);
        this.drone = drone;
    }

    @Override
    public void update(String found, int range, JSONArray biomes, int batteryLevel, JSONArray siteList, JSONArray creekList) {
        if (siteList != null && !siteList.isEmpty()) {
            siteFound = true;
            siteCoordinate = new Point(drone.getX(), drone.getY());
        }
        if (creekList != null && !creekList.isEmpty()) {
            for (int i = 0; i < creekList.length(); i++) {
                if (!mapLayout.containsValue(creekList.getString(i))) {
                    Point currentPoint = new Point(drone.getX(), drone.getY());
                    mapLayout.put(currentPoint, creekList.getString(i));
                }
            }
        }

    }

    //ok so the point be key and the value is the creek id
    //we can have another variable like sitefound and sitecoordinate

    //Sets a object to inputed location
    public void setLocation(int xdist, int ydist, String infoString) {
        // Adding int arrays to the hashmap
        Point targetPoint = currentPoint.getLocation();
        targetPoint.translate(xdist, ydist);
        mapLayout.put(new Point(targetPoint), infoString);
    }

    public void setEchoInfo(int xdist, int ydist, boolean land_found){
        Point targetPoint = currentPoint.getLocation();
        if (xdist< 0){
            for(int i = 0; i > xdist+1; i--){
                targetPoint.translate(-1, 0);
                mapLayout.put(new Point(targetPoint), "Ocean");
            }
        }
        else if(xdist > 0){
            for(int i = 0; i < xdist-1; i++){
                targetPoint.translate(1, 0);
                mapLayout.put(new Point(targetPoint), "Ocean");
            }
        }
        else if (ydist< 0){
            for(int i = 0; i > ydist+1; i--){
                targetPoint.translate(0, -1);
                mapLayout.put(new Point(targetPoint), "Ocean");
            }
        }
        else{
            for(int i = 0; i < ydist-1; i++){
                targetPoint.translate(0, 1);
                mapLayout.put(new Point(targetPoint), "Ocean");
            }
        }
        Point finalPoint = currentPoint.getLocation();
        finalPoint.translate(xdist, ydist);
        if(land_found){
            mapLayout.put(finalPoint, "Land");
        }
        else{
            mapLayout.put(finalPoint, "OB");
        }
    }

    //gets object from location
    public String getLocation(int xdist, int ydist) {
        try {
            Point targetPoint = new Point(xdist, ydist);
            String result = mapLayout.get(targetPoint);
            return result;
        } catch (Exception e) {
            return "Information not found"; // 
        }
    }

    public void updateCurrentPoint(int xdist, int ydist){
        currentPoint.translate(xdist, ydist);
    }
}
