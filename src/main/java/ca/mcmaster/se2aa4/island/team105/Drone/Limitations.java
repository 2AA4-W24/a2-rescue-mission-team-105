package ca.mcmaster.se2aa4.island.team105.Drone;

import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Configuration.JSONConfiguration;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import java.util.Map;

public class Limitations extends JSONConfiguration {
    private Drone level;
    private final Logger logger = LogManager.getLogger();
    Drone drone = new Drone(7000, "E");
    private boolean radioRange;

    Direction heading = drone.getHeading();

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(Drone level) {
        this.level = level;
    }

    public Limitations() {
        //TODO Auto-generated constructor stub
    }

    // checks battery
    public void returnHome(Actions action) {
        if (level.getLevel() <= 4000) {
            logger.info("Battery level is equal to or below 6800. Returning home");
            action.stop();
        } else {
            logger.info("Battery level is greater than 6800. Continuing exploration.");
        }
    }

    // checks bad command
    public void badCommand(Actions action, Direction desiredDirection, JSONObject parameter) {
        if (desiredDirection == heading) {
            logger.info("Already heading in the desired direction. Continuing exploration.");
        } else {
            if (is180DegreeTurn(desiredDirection)) {
                logger.error("Attempting a 180-degree turn. Invalid command.");
            } else {
                action.heading(parameter, desiredDirection, drone);
            }
        }
    }

    public boolean is180DegreeTurn(Direction desiredDirection) {
        Map<Direction, Direction> oppositeDirections = new EnumMap<>(Direction.class);
        oppositeDirections.put(Direction.N, Direction.S);
        oppositeDirections.put(Direction.S, Direction.N);
        oppositeDirections.put(Direction.E, Direction.W);
        oppositeDirections.put(Direction.W, Direction.E);

        return oppositeDirections.get(heading) == desiredDirection;
    }

    public boolean radioRange() {
        // gets the size of the map through the radio range
        // to get the size echo in all directions
        // conditionals if current x and y is less than the radio range results than you cannot fly in that direction
        return radioRange;

    }

    // checks radio range
    //check all direction for range 
    //make relative x and y borders when out of range response seen
    //to stay in range never fly or "heading" command out of range
    //Never fly or head into a postion where we are facing the out while on the edge


}
