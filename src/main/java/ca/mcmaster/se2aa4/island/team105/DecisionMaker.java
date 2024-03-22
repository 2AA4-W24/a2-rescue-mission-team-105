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
    private int count = -1; // need to keep this outside
    private int gridCount = -1;
    private int phase = 0;
    private boolean landFound;
    private int range;
    private boolean radar;
    private int state = 0;
    private Direction searchDirection;
    private Direction turnDirection;
    private boolean turnLeft;

    
    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) { // might be high coupling
        Direction left = leftOrientation(direction, drone);
        Direction right = rightOrientation(direction, drone);
        count++;
        //Stops when reaches the last state
        if (phase == 4) {
            decision = action.stop();
            return;
        }

        if(phase == 0) {
            if(landFound){
                phase = 1;
                count = 0;
            }
        }
        else if (phase == 1){
            if(landFound){
                phase = 2;
                count = 0;
            }
        }
        
        if(limitation.is180DegreeTurn(direction)== false){
            switch(phase) {
                case 0:
                    if (count % 5 == 0){
                        decision = action.echo(parameter, orientation(direction,drone));
                    }
                    
                    else if (count % 5 == 1) {
                        decision = action.echo(parameter, left);
                        searchDirection = left;
                    }
        
                    else if (count % 5 == 2){
                        decision = action.echo(parameter, right);
                        searchDirection = right;

                    }

                    else if (count % 5 == 3) {
                        decision = action.fly(drone);
                    }

                    else if (count % 5 == 4){
                        decision = action.scan();
                    }
                    break;

                case 1:
                    
                    if (count % 4 == 0) {
                        decision = action.echo(parameter, left);
                        searchDirection = left;
                        turnDirection = rightOrientation(searchDirection, drone);
                        turnLeft = true;
                        // decision = action.scan();
                    }
        
                    else if (count % 4 == 1){
                        decision = action.echo(parameter, right);
                        searchDirection = right;
                        turnDirection = leftOrientation(searchDirection, drone);
                        turnLeft = false;
                    }

                    else if (count % 4 == 2) {
                        decision = action.fly(drone);
                    }

                    else if (count % 4 == 3){
                        decision = action.scan();
                    }
                    break;

                case 2:
                    if (count == 0){
                        decision = action.fly(drone);
                    }
                    else if(count < 3){
                        if(turnLeft){
                            decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                            turnDirection = rightOrientation(turnDirection, drone);                        }
                        else{
                            decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                            turnDirection = leftOrientation(turnDirection, drone);                        }
                    }
                    else{
                        decision = action.heading(parameter, turnDirection, drone);
                        phase = 3;
                        count = 0;
                    }
                    break;

                case 3:
                    logger.info("phase 4");
                    if (count <= range+1) {
                        decision = action.fly(drone);
                    }
                    else{
                        phase = 4;
                        decision = action.scan();
                    }
                    
                    break;
                case 4:
                    if (count % 3 == 0) {
                        decision = action.fly(drone);
                        radar = false;
                    }
        
                    else if (count % 3 == 1){
                        decision = action.echo(parameter, searchDirection);
                        logger.info(searchDirection);
                        radar = true;

                    }
                    else if (count % 3 == 2){
                        decision = action.scan();
                        radar = false;

                    }
                    break;
                
                default:
                    logger.info("no case found");
            }
        }else{
                logger.info("Bad Command: echo in wrong direction");
            }
        }


        public void gridSearch(Actions action, Drone drone, Limitations limitation, Direction direction, JSONObject parameter) {
            Direction left = leftOrientation(direction, drone);
            Direction right = rightOrientation(direction, drone);
            gridCount++;
  
            if (state == 0) {
                if (landFound) {
                    state = 1;
                    gridCount = 0;
                }
            }

            else if (state == 1) {
                if (!landFound) {
                    state = 2;
                    gridCount = 0;
                    direction = Direction.E; // must be original heading you were facing when you first started
                }
            }

            else if (state == 2) {
                if (!landFound) {
                    state = 3;
                    gridCount = 0;
                }
            }

            else if (state == 3) {
                if (landFound) {
                    gridCount = 0;
                    state = 4;
                }
            }

            else if (state == 4) {
                if (!landFound) {
                    state = 3;
                    gridCount = 0;
                }
            }

            if (limitation.is180DegreeTurn(direction) == false) {
                switch(state) {
                    case 0:
                        action.echo(decision, direction);

                    case 1: 
                        if (gridCount < range) {
                            action.fly(drone);
                        }
                        else {
                            action.scan();
                            state = 0;
                        }
                        break;


                    case 2:
                        if (gridCount % 2 == 0) {
                            action.fly(drone);
                        }

                        else {
                            action.heading(decision, direction, drone);
                        }
                        break;

                    
                    case 3:
                        if (count == 0){
                            decision = action.fly(drone);
                        }
                        else if(count < 3){
                            if(turnLeft){
                                decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                                turnDirection = rightOrientation(turnDirection, drone);                        }
                            else{
                                decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                                turnDirection = leftOrientation(turnDirection, drone);                        }
                        }
                        else{
                            decision = action.heading(parameter, turnDirection, drone);
                            state = 4;
                            count = 0;
                        }
                        break;
                    
                    case 4:
                        if (count % 2 == 0) {
                            action.scan();
                        }
                        else {
                            action.fly(drone);
                        }
                        break;
                    }
                    
                }
            
        }


        // Scan, if land is found fly forward, and echo then
        // if land is not found then
        // echo in the direction you were heading in and the direction you're facing
        // if there is land in the front, then continue echoing else dont echo at all
        // if the is land in the side, then continue echoing, else don't echo at all
        // fly forward, and scan IF THERE IS LAND IN THE FRONT
        // else fly forward until you dont scan for land on the side anymore
        // make perfect u turn, then repeat
    

    

    public JSONObject getDecision() {
        return decision;
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
        if(landFound){
            range = distance;
        }
    }
}
