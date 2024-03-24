package ca.mcmaster.se2aa4.island.team105.map;
import java.util.HashMap;

import org.json.JSONArray;

import ca.mcmaster.se2aa4.island.team105.drone.Drone;

import java.awt.Point;

public class ExplorerMap extends SubObserver {

    //relative position this is starting state
    private Point currentPoint = new Point(0, 0);

    //Creates a 2d array that stores information Objects
    private HashMap<Point, String> mapLayout = new HashMap<>();
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

    public String getCreeks() {
        return this.mapLayout.values().toString();
    }
}
