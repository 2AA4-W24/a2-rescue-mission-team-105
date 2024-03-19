package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DecisionMaker {

    private final static Logger logger = LogManager.getLogger();


    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter, int count) { // might be high coupling
        count++;
        direction = orientation(direction, drone);
        logger.info("Direction is: " + direction);
        if (limitation.is180DegreeTurn(direction) == false) {
            if (count < 2) {
                logger.info("will the heading ever change?");
                action.echo(parameter, direction);
            }
            logger.info("does this ever");
            action.fly(drone);
        }
        else {
            logger.info("Incorrect command, cannot echo in the opposite direction");
        }
    }

    // Echo in the same direction of heading, if there is no land then perform
        // Echo on sides left/right side, depending on the which side we hit land, we want to stick to only on that side afterwards
        // Fly after echoing
        // Keeping going until you hit the first and last land
        // Then turn in the same direction you echoed.
        // Echo direction is now changed to the opposite direction you were flying
        // Keep echo/flying until you hit first/last land
        // Store all these information
    // If there is a land then
        // Change heading to either east/west
        // fly forward, echo in the direction of the map until you get no land, then 
        // start the first state :D


    public Direction orientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
        switch(heading) {
            case Direction.N:
                return Direction.N;
            case Direction.S:
                return Direction.S;
            case Direction.E:
                return Direction.E;
            case Direction.W:
                return Direction.W;   
            default:
                throw new IllegalArgumentException("Invalid heading encountered: " + heading);
        }
    }

    public void rightOrientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
            switch(heading) {
                case Direction.N:
                    direction = Direction.E;
                    break;
                case Direction.S:
                    direction = Direction.W;
                    break;
                case Direction.E:
                    direction = Direction.S;
                    break;
                case Direction.W:
                    direction = Direction.N;   
                    break;
                default:
                    break;
            }

    }

    public void leftOrientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
            switch(heading) {
                case Direction.N:
                    direction = Direction.W;
                    break;
                case Direction.S:
                    direction = Direction.E;
                    break;
                case Direction.E:
                    direction = Direction.N;
                    break;
                case Direction.W:
                    direction = Direction.S;   
                    break;
                default:
                    break;
            }

    }


}
