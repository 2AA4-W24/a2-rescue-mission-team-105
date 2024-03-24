package ca.mcmaster.se2aa4.island.team105.Drone;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import ca.mcmaster.se2aa4.island.team105.Map.SubObserver;

import java.util.Map;
import java.util.EnumMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

public class Drone extends SubObserver{
    private final Logger logger = LogManager.getLogger();
    //This is private because we will further create the services in this class
    private Direction heading;
    private int level;
    private int x, y;

    public Drone(Integer level, String starting) {
        this.level = level;
        try{
            this.heading = Direction.valueOf(starting);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String found, int range, JSONArray biomes, int batteryLevel) {
        this.level -= batteryLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getX() {
        return x;
    }
    

    public Integer getY() {
        return y;
    }


    // call actions 
    //Simple return to check the functionality
    public Direction getHeading() {
        return (this.heading);
    }
  
    public void headRight(){
        Map<Direction, Direction> goingRight = new EnumMap<>(Direction.class);
        goingRight.put(Direction.N, Direction.E);
        goingRight.put(Direction.E, Direction.S);
        goingRight.put(Direction.S, Direction.W);
        goingRight.put(Direction.W, Direction.N);
        this.heading = goingRight.get(this.heading);
    }
    
  
    public void headLeft() {
        Map<Direction, Direction> goingLeft = new EnumMap<>(Direction.class);
        goingLeft.put(Direction.N, Direction.W);
        goingLeft.put(Direction.W, Direction.S);
        goingLeft.put(Direction.S, Direction.E);
        goingLeft.put(Direction.E, Direction.N);
        this.heading = goingLeft.get(this.heading);
    }

    public void updatedFlyingCoordinates() {
        switch(this.heading) {
            case Direction.N:
                logger.info("you ran it twice bro");
                this.y+=1;
                break;
            case Direction.S:
                this.y-=1;
                break;
            case Direction.E:
                logger.info("e deteted");
                this.x+=1;
                break;
            case Direction.W:
                this.x-=1;
                break;
            default:
                logger.info("The heading is wrong");
                break;
        }
    }

    public void updatedHeadingCoordinates(Direction direction) {
        updatedFlyingCoordinates();
        switch(direction) {
            case Direction.N:
                this.heading = direction;
                updatedFlyingCoordinates();
                break;
            case Direction.S:
                this.heading = direction;
                updatedFlyingCoordinates();
                break;
            case Direction.E:
                this.heading = direction;
                updatedFlyingCoordinates();
                break;
            case Direction.W:
                this.heading = direction;
                updatedFlyingCoordinates();
                break;
            default:
                logger.info("The heading is wrong");
                break;
        }
    }

    
    
}
