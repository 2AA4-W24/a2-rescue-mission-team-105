package ca.mcmaster.se2aa4.island.team105.Drone;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import java.util.Map;
import java.util.EnumMap;


public class Drone {
    //This is private because we will further create the services in this class
    private Direction heading;
    private int level;
    private int x, y;

    public Drone(int level, String starting) {
        this.level = level;
        this.x = 0;
        this.y = 0;
        try{
            this.heading = Direction.valueOf(starting);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        switch(heading) {
            case N:
                y+=1;
            case S:
                y-=1;
            case E:
                x+=1;
            case W:
                x-=1;
        }
    }

    
    
}
