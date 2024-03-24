package ca.mcmaster.se2aa4.island.team105.drone;
import java.util.EnumMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.JSONConfiguration;
import ca.mcmaster.se2aa4.island.team105.enums.Direction;

import java.util.Map;

// Christina Zhang, Victor Yu, Kevin Kim
// 24/03/2024
// 2AA4 <T01>
// Software Engineering
// Facilitates and communicates with other classes in taking information and running rescue missions. Handles JSON-formatted data
// and response acknowledgement and decision processing

public class Limitations extends JSONConfiguration {
    private Drone level;
    private final Logger logger = LogManager.getLogger();
    Drone drone = new Drone(7000, "E");
    Direction heading = drone.getHeading();

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(Drone level) {
        this.level = level;
    }

    // checks battery level of the drone
    public boolean returnHome(Actions action) {
        // returns home if battery threshold is equal to or below 1% of original battery or min 50
        if (level.getLevel() <= 20 || level.getLevel() <= drone.getLevel()*0.01) {
            logger.info("Battery level is equal or below 1% Returning home");
            return true;
        } else {
            logger.info("Battery level is greater than 1%. Continuing exploration.");
        }
        return false;
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

    // returns true if current direction is the opposite direction where you want to head to
    public boolean is180DegreeTurn(Direction desiredDirection) {
        Map<Direction, Direction> oppositeDirections = new EnumMap<>(Direction.class);
        oppositeDirections.put(Direction.N, Direction.S);
        oppositeDirections.put(Direction.S, Direction.N);
        oppositeDirections.put(Direction.E, Direction.W);
        oppositeDirections.put(Direction.W, Direction.E);

        return oppositeDirections.get(heading) == desiredDirection;
    }

}
