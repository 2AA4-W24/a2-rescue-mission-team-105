package ca.mcmaster.se2aa4.island.team105;

public class Drone extends Explorer {
    // save battery from json object
    // after every call 7000 - cost
    // once we hit 250 we stop and return home
    // create json object, key, value would be another json object, value for the cost would be 5
    private int batteryLevel;
    Direction north = Direction.NORTH;
    Direction south = Direction.SOUTH;
    Direction east = Direction.EAST;
    Direction west = Direction.WEST;

    private void orientation() {
        
    }
    
}
