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
    


    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter, int count) {
        count++;
        direction = orientation(direction, drone);
        logger.info(direction);
        if (limitation.is180DegreeTurn(direction) == false) {
            logger.info("hee");
            if (count % 2 == 0) {
                action.echo(parameter, direction);
            }
            else {
                action.fly(drone);
            }
        }
        logger.info("Incorrect command, cannot echo in the opposite direction");
    }

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
