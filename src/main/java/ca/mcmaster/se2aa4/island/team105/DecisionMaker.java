package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import ca.mcmaster.se2aa4.island.team105.Map.SubObserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DecisionMaker implements SubObserver {


    private final static Logger logger = LogManager.getLogger();

    protected JSONObject decision = new JSONObject();
    private int count = -1; // need to keep this outside
    private int gridCount = -1;

    private boolean foundGround; //if ground is found from echo
    private int echoRange; //range gotten from echo
    private int phase = 0;
    private boolean radar;
    Direction searchDirection;
    private int state = 0;
    private boolean inOcean;
    
    @Override
    public void update(String found, int range, JSONArray biomes) {
        this.foundGround = (found.equals("GROUND"));
        this.echoRange = range;
        for (int i = 0; i < biomes.length(); i++) {
            if (!biomes.getString(i).equals("OCEAN")) {
                this.inOcean = true;
            }
        }       
        logger.info(foundGround);
        logger.info(echoRange);
    }

    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) { // might be high coupling
        Direction left = leftOrientation(direction, drone);
        Direction right = rightOrientation(direction, drone);
        count++;
        //Stops when reaches the last state
        if (phase == 2) {
            decision = action.stop();
            return;
        }

        if(phase == 0){
            if(this.foundGround){
                phase = 1;
                count = 0;
            }
        }
        else if (phase == 1){
            if(this.foundGround){
                phase = 2;
                count = 0;
            }
        }
        if(radar){
            if(!this.foundGround){
                if (phase == 2){
                    logger.info("phase 3");
                    phase = 3;
                    count = 0;
                }
                else if (phase == 5){
                    phase = 6;
                    count = 0;
                }
                else if (phase == 4){
                    phase = 5;
                    count = 0;
                }
                else if (phase == 7){
                    phase = 8;
                    count = 0;
                }
            }
        }
        if(radar && this.foundGround){
            if (phase == 3){
                logger.info("phase 3");
                phase = 4;
                count = 0;
            }
            if (phase == 6){
                phase = 7;
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
                        // decision = action.scan();
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
                        // decision = action.scan();
                    }
        
                    else if (count % 4 == 1){
                        decision = action.echo(parameter, right);
                        searchDirection = right;

                    }

                    else if (count % 4 == 2) {
                        decision = action.fly(drone);
                    }

                    else if (count % 4 == 3){
                        decision = action.scan();
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
                        direction = rightOrientation(direction, drone);
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
                case 5:
                    decision = action.heading(parameter, searchDirection, drone);
                    if(searchDirection == leftOrientation(direction, drone)){
                        searchDirection = leftOrientation(searchDirection, drone);
                        direction = leftOrientation(direction, drone);
                        radar = false;
                    }
                    else{
                        searchDirection = rightOrientation(searchDirection, drone);
                        direction = rightOrientation(direction, drone);
                        radar = false;
                    }
                    phase = 6;
                    break;
                case 6:
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
                case 7:
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


        public void gridSearch(Actions action, Drone drone, Limitations limitation, Direction direction) {
            Direction left = leftOrientation(direction, drone);
            Direction right = rightOrientation(direction, drone);
            gridCount++;
            if (limitation.is180DegreeTurn(direction) == false) {
                



                switch(state) {
                    case 1: 
                        if (gridCount % 2 == 0) {
                            action.scan();
                        }
                        else {
                            action.fly(drone);
                        }

                    case 2:
                        if (gridCount % 2 == 0) {
                            action.fly(drone);
                        }

                        else {
                            action.scan();
                        }
                    
                    case 3:
                        action.echo(decision, direction);

                    case 4:
                        direction = leftOrientation(direction, drone);
                        action.echo(decision, direction);
                    case 5:
                        action.fly(drone);
                    case 6: // when we're facing in the northern direction
                        if (count % 5 == 0) {
                            action.heading(decision, direction, drone); // head left
                            direction = rightOrientation(direction, drone);
                        }
        
                        else if (count % 5 == 1) {
                            action.heading(decision, direction, drone); // head right
                        }
        
                        else if (count % 5 == 2) {
                            action.echo(decision, direction); // head right
                        }
        
                        else if (count % 5 == 3) {
                            action.fly(drone); 
                        }
        
                        else if (count % 5 == 4) {
                            action.heading(decision, direction, drone); // head right
                        }
        
                        else {
                            action.fly(drone);
                        }

                    case 7: // when we're facing the southern direction
                        if (count % 5 == 0) {
                            direction = rightOrientation(direction, drone);
                            action.heading(decision, direction, drone); // head right
                            direction = leftOrientation(direction, drone);
                        }
        
                        else if (count % 5 == 1) {
                            action.heading(decision, direction, drone); // head left
                        }
        
                        else if (count % 5 == 2) {
                            action.echo(decision, direction); // head left
                        }
        
                        else if (count % 5 == 3) {
                            action.fly(drone); 
                        }
        
                        else if (count % 5 == 4) {
                            action.heading(decision, direction, drone); // head left
                        }
        
                        else {
                            action.fly(drone);
                        }
    
                }
                
                
                
                
                

    
            }
        }


        // Scan, if land is found fly forward, and scan then
        // if land if not found then
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
}
