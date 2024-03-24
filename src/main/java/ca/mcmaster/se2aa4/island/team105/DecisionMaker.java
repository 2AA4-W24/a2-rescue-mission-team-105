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

public class DecisionMaker extends SubObserver {


    private final static Logger logger = LogManager.getLogger();


    protected JSONObject decision = new JSONObject();
    private int count = -1; // need to keep this outside
    private int gridCount = -1;
    private int phase = 0;
    private boolean radar;
    private int state = 0;
    private Direction searchDirection;
    private Direction turnDirection;
    private boolean turnLeft;
    private boolean inOcean; //we scan, if there is land in the biomes array we are not in the ocean
    private boolean foundGround; //if ground is found when we echo
    private int echoRange; //if we echo, the range
    private boolean boxfound;

    
    @Override
    public void update(String found, int range, JSONArray biomes, int batteryLevel, JSONArray siteList, JSONArray creekList) {
        this.inOcean = true;
        this.foundGround = (found.equals("GROUND"));
        this.echoRange = range;
        if (biomes != null) {
            for (int i = 0; i < biomes.length(); i++) {
                if (!biomes.getString(i).equals("OCEAN")) {
                    this.inOcean = false;
                }
            }   
        }    
        logger.info(foundGround);
        logger.info(echoRange);
        logger.info(inOcean);
    }

    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) { // might be high coupling
        Direction left = leftOrientation(direction, drone);
        Direction right = rightOrientation(direction, drone);
        count++;
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
                        this.turnLeft = true;
                        // decision = action.scan();
                    }
        
                    else if (count % 4 == 1){
                        decision = action.echo(parameter, right);
                        searchDirection = right;
                        turnDirection = leftOrientation(searchDirection, drone);
                        this.turnLeft = false;
                    }

                    else if (count % 4 == 2) {
                        decision = action.fly(drone);
                    }

                    else if (count % 4 == 3){
                        decision = action.scan();
                    }
                    break;

                case 2:
                    if (count < 3){
                        decision = action.fly(drone);
                    }
                    else if(count < 6){
                        if(this.turnLeft){
                            decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                            turnDirection = leftOrientation(turnDirection, drone);
                        }
                        else{
                            decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                            turnDirection = rightOrientation(turnDirection, drone);  
                        }
                    }
                    else if(count < 7){
                        if(this.turnLeft){
                            turnDirection = rightOrientation(turnDirection, drone);
                            decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                            turnDirection = rightOrientation(turnDirection, drone);
                            
                        }
                        else{
                            turnDirection = leftOrientation(turnDirection, drone);
                            decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                            turnDirection = leftOrientation(turnDirection, drone);
                        }
                    }
                    else{
                        decision = action.heading(parameter, turnDirection, drone);
                        phase = 3;
                        count = 0;
                    }
                    break;

                case 3:
                    logger.info("phase 4");
                    if (count <= this.echoRange) {
                        decision = action.fly(drone);
                    }
                    else{
                        phase = 4;
                        this.turnLeft = !this.turnLeft;
                        this.boxfound = true;
                        decision = action.scan();
                    }
                    
                    break;
                default:
                    logger.info("no case found");
            }
        }else{
                logger.info("Bad Command: echo in wrong direction");
            }
        }







        public void gridSearch(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) {
            gridCount++;    
            
            
            if (state == 0 && radar) {
                logger.info("Im in state 0");
                logger.info(this.foundGround);
                if (this.foundGround) {
                    state = 1;
                    gridCount = 0;
                }

                else {
                    state = 2;
                    gridCount = 0;
                }
            }

            else if (state == 2 && radar) {
                logger.info("Im in state 2");
                if (!this.foundGround) {
                    state = 3;
                    gridCount = 0;
                }
            }
            if (state == 4 && radar) {
                logger.info("Im in state 4");
                if(this.foundGround){
                    state = 1;
                    gridCount = 1;
                    this.turnLeft = !this.turnLeft;
                }else{
                    decision = action.stop();
                    return;
                }
            }


            
            if (limitation.is180DegreeTurn(direction) == false) {
                switch(state) {
                    case 0:
                        logger.info("This is case 0");
                        decision = action.echo(parameter, orientation(turnDirection, drone));
                        radar = true;
                        break;
                    
                    case 1:
                        logger.info("This is case 1"); 
                        if (gridCount <= this.echoRange) {
                            logger.info("phase 1");
                            decision = action.fly(drone);
                            radar = false;
                        }
                        else {
                            decision = action.scan();
                            state = 0;
                            radar = false;
                        }
                        break;


                    case 2:
                        if (gridCount == 0){
                            decision = action.scan(); // starting heading
                            radar = false;
                        }
                        else if (gridCount % 3 == 0) {
                            logger.info("phase 3");
                            if(this.turnLeft){
                                decision = action.echo(parameter, leftOrientation(turnDirection, drone)); // starting heading

                            }
                            else {
                                decision = action.echo(parameter, rightOrientation(turnDirection, drone)); // starting heading

                            }
                            radar = true;
                        }
                        else if (gridCount % 3 > 0) {
                            decision = action.fly(drone);
                            radar = false;
                        }
                        break;

                    
                    case 3:
                        radar = false;
                        logger.info("This is case 3");
                        if (gridCount == 0){
                            decision = action.fly(drone);
                        }
                        else if(gridCount < 4){
                            if(this.turnLeft){
                                decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                                turnDirection = rightOrientation(turnDirection, drone);
                            }
                            else {
                                decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                                turnDirection = leftOrientation(turnDirection, drone);
                            }
                        }
                        else if(gridCount < 5){
                            decision = action.fly(drone);
                        }
                        else{
                            if(!this.turnLeft){
                                turnDirection = rightOrientation(turnDirection, drone);
                                decision = action.heading(parameter, rightOrientation(turnDirection, drone), drone);
                                turnDirection = rightOrientation(turnDirection, drone);
                            }
                            else {
                                turnDirection = leftOrientation(turnDirection, drone);
                                decision = action.heading(parameter, leftOrientation(turnDirection, drone), drone);
                                turnDirection = leftOrientation(turnDirection, drone);
                            }
                            gridCount = -1;
                            state = 4;
                        }
                        break;
                    
                    case 4:
                        logger.info("This is case 4"); 
                        decision = action.echo(parameter, orientation(turnDirection, drone));
                        radar = true;
                        break;
                    
                    default:
                    logger.info("no case found");
                    }
                }
            }




        // state 0
        //      echo forward
        //      if you find land go to state 1
        //      if you dont find land go to state 2
        // state 1
        //      fly until the range of the echo distance
        //      scan
        // state 2
        //      echo in the direction of starting heading
        //      keeping flying/echoing until land is not found

    

    

    public JSONObject getDecision(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter) {
        if(!this.boxfound){
            findMapBox(limitation, drone, direction, action, parameter);
        }else{
            gridSearch(limitation, drone, direction, action, parameter);
        }

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
