package ca.mcmaster.se2aa4.island.team105.map;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import ca.mcmaster.se2aa4.island.team105.drone.Drone;

import java.awt.Point;

public class ExplorerMap extends SubObserver {

    //relative position this is starting state
    private Point currentPoint = new Point(0, 0);

    //Creates a HashMap that stores creek IDs and their coordinates
    private Map<Point, String> mapLayout = new HashMap<>();
    private Drone drone;


    
    public ExplorerMap(Drone drone) {
        currentPoint.setLocation(0, 0);
        this.drone = drone;
    }

    //updates the hashmap if a new creek is found
    @Override
    public void update(String found, int range, JSONArray biomes, int batteryLevel, JSONArray siteList, JSONArray creekList) {
        if (creekList != null && !creekList.isEmpty()) {
            for (int i = 0; i < creekList.length(); i++) {
                if (!mapLayout.containsValue(creekList.getString(i))) {
                    Point currentPoint = new Point(drone.getX(), drone.getY());
                    mapLayout.put(currentPoint, creekList.getString(i));
                }
            }
        }
    }

    //returns a list of creek IDs
    public String getCreeks() {
        return this.mapLayout.values().toString();
    }
}
