package ca.mcmaster.se2aa4.island.team105.Drone;

import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team105.Configuration.JSONConfiguration;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import java.util.Map;

public class Limitations extends JSONConfiguration {
    private Drone level;
    private final Logger logger = LogManager.getLogger();
    Drone drone = new Drone("E");
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
            action.stop(decision);
        } else {
            logger.info("Battery level is greater than 6800. Continuing exploration.");
        }
    }

    // checks bad command
    public void badCommand(Actions action, Direction desiredDirection) {
        if (desiredDirection == heading) {
            logger.info("Already heading in the desired direction. Continuing exploration.");
        } else {
            if (is180DegreeTurn(desiredDirection)) {
                logger.error("Attempting a 180-degree turn. Invalid command.");
            } else {
                action.heading(decision, parameter, desiredDirection);
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


    // checks radio range
    



}
