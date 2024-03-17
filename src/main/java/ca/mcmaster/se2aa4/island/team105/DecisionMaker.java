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
    private static int count;
    


    public static void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) {
        count++;
        if (limitation.is180DegreeTurn(direction) == false) {
            if (count % 2 == 0) {
                action.echo(parameter, Direction.N);
            }
            else {
                action.fly(drone);
            }
        }
        logger.info("Incorrect command, cannot echo in the opposite direction");
    }

    public void orientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
        switch(heading) {
            case Direction.N:
                break;
            case Direction.S:
                break;
            case Direction.E:
                break;
            case Direction.W:
                break;
            default:
                logger.info("The heading is wrong");
                break;
        }
    }


}
