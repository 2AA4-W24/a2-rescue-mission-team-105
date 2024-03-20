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

    protected JSONObject decision = new JSONObject();
    private int count; // need to keep this outside
    private int phase = 0;
    private boolean landFound;
    private int range;
    private boolean radar;
    Direction searchDirection;

    

    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) { // might be high coupling
        count++;
        if (phase == 5) {
            decision = action.stop();
            return;
        }
        // logger.info(drone.getX());
        // logger.info("counter is: " + count);
        direction = rightOrientation(direction, drone);
        logger.info("Direction is: " + direction);
        if(true){
        //if (limitation.is180DegreeTurn(direction) == false) {
            if (phase == 0){
                if(landFound){
                    phase = 1;
                    count = 0;
                }
            }
            if(radar){
                if(!landFound){
                    if (phase == 1){
                        phase = 2;
                        count = 0;
                    }
                    else if (phase == 2){
                        logger.info("phase 3");
                        phase = 3;
                        count = 0;
                    }
                    else if (phase == 4){
                        phase = 5;
                        count = 0;
                    }
                }
            }
            if(phase == 3 && radar && landFound){
                if (phase == 3){
                    logger.info("phase 3");
                    phase = 4;
                    count = 0;
                }
            }

            switch(phase) {
                case 0:
                   
                    if (count % 4 == 0) {
                        logger.info("will the heading ever change?");
                        decision = action.echo(parameter, Direction.N);
                        searchDirection = Direction.N;
                        // decision = action.scan();
                    }
                    else if (count % 4 == 1) {
                        logger.info("does this ever");
                        decision = action.fly(drone);
                    }
        
                    else if (count % 4 == 2){
                        decision = action.echo(parameter, Direction.S);
                        searchDirection = Direction.S;

                    }
                    
                    else if (count % 4 == 3){
                        decision = action.scan();
                    }
                    break;
                case 1:
                    logger.info("phase 2");
                    if (count % 3 == 0) {
                        logger.info("does this ever");
                        decision = action.fly(drone);
                        radar = false;
                    }
        
                    else if (count % 3 == 1){
                        decision = action.echo(parameter, searchDirection);
                        radar = true;
                    }
                    else if (count % 3 == 2){
                        decision = action.scan();
                        radar = false;
                    }
                    break;
                case 2:
                    decision = action.heading(parameter, searchDirection, drone);
                    if(searchDirection == leftOrientation(direction, drone)){
                        searchDirection = leftOrientation(searchDirection, drone);
                        direction = leftOrientation(direction, drone);
                        radar = false;
                    }
                    else{
                        searchDirection = rightOrientation(searchDirection, drone);
                        direction = leftOrientation(direction, drone);
                        radar = false;
                    }
                    phase = 3;
                    break;
                case 3:
                    logger.info("phase 4");
                    if (count % 2 == 0) {
                        logger.info("does this ever");
                        decision = action.fly(drone);
                        radar = false;
                    }
        
                    else if (count % 2 == 1){
                        decision = action.echo(parameter, searchDirection);
                        radar = true;

                    }
                    
                    break;
                case 4:
                    if (count % 3 == 0) {
                        logger.info("does this ever");
                        decision = action.fly(drone);
                        radar = false;
                    }
        
                    else if (count % 3 == 1){
                        decision = action.echo(parameter, searchDirection);
                        radar = true;

                    }
                    else if (count % 3 == 2){
                        decision = action.scan();
                        radar = false;

                    }
                    break;
                case 5:
                    decision = action.stop();
                default:
                  logger.info("not in phase");
              }
        }
        else {
            logger.info("Incorrect command, cannot echo in the opposite direction");
        }
    }

    public JSONObject getDecision() {
        return decision;
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

    public Direction rightOrientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
        switch(heading) {
            case Direction.N:
                return Direction.E;
            case Direction.S:
                return Direction.W;
            case Direction.E:
                return Direction.S;
            case Direction.W:
                return Direction.N;   
            default:
                throw new IllegalArgumentException("Invalid heading encountered: " + heading);
        }
    }

    public Direction leftOrientation(Direction direction, Drone drone) {
        Direction heading = drone.getHeading();
        switch(heading) {
            case Direction.N:
                return Direction.W;
            case Direction.S:
                return Direction.E;
            case Direction.E:
                return Direction.N;
            case Direction.W:
                return Direction.S;   
            default:
                throw new IllegalArgumentException("Invalid heading encountered: " + heading);
        }
    }
    
    public void decisionUpdate(boolean land_found, int distance){
        landFound = land_found;
        range = distance;
    }
}
