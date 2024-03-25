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
    private int maxX, maxY, minX, minY;

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(Drone level) {
        this.level = level;
    }

    // checks battery level of the drone
    public boolean returnHome(Actions action) {
        // returns home if battery threshold is equal to or below 1% of original battery or min 50
        if (level.getLevel() <= 40 || level.getLevel() <= drone.getLevel()*0.007) {
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

    public void setBound(Direction desiredDirection, int range){
        
        switch (desiredDirection){
            case Direction.N:
                this.maxY = (level.getY()+ range);
                break;
            case Direction.E:
                this.maxX = (level.getX()+ range);
                break;
            case Direction.S:
                this.minY = (level.getY()- range);
                break;
            case Direction.W:
                this.minX = (level.getX()- range);
                break;
        default:
            logger.info("Direction not found!");
            break;
        }
        
    }
    public boolean isOutOfBounds(){
        if (this.minX > level.getX() || level.getX() > this.maxX ){
            logger.info(this.minX + " " + level.getX() + " " + this.maxX);
            return true;
        }
        else if(this.minY > level.getY() || level.getY() > this.maxY ){
            logger.info(this.minY + " " + level.getY() + " " + this.maxY);
            return true;
        }
        logger.info(this.minX + " " + level.getX() + " " + this.maxX);
        logger.info(this.minY + " " + level.getY() + " " + this.maxY);
        return false;
    }
}
