package ca.mcmaster.se2aa4.island.team105.drone;
import java.util.Map;

import ca.mcmaster.se2aa4.island.team105.enums.Direction;

import java.util.EnumMap;

// Christina Zhang, Victor Yu, Kevin Kim
// 24/03/2024
// 2AA4 <T01>
// Software Engineering
// updates movement, battery level, current heading, and heading movement

public class Drone {
    private Direction heading;
    private int level;
    private int x;
    private int y;

    // updates heading of the drone
    public Drone(Integer level, String starting) {
        this.level = level;
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


    public Direction getHeading() {
        return this.heading;
    }
    
    // turns drone 90 degrees to the right
    public void headRight(){
        Map<Direction, Direction> goingRight = new EnumMap<>(Direction.class);
        goingRight.put(Direction.N, Direction.E);
        goingRight.put(Direction.E, Direction.S);
        goingRight.put(Direction.S, Direction.W);
        goingRight.put(Direction.W, Direction.N);
        this.heading = goingRight.get(this.heading);
    }
    
    // turns drone 90 degrees to the left
    public void headLeft() {
        Map<Direction, Direction> goingLeft = new EnumMap<>(Direction.class);
        goingLeft.put(Direction.N, Direction.W);
        goingLeft.put(Direction.W, Direction.S);
        goingLeft.put(Direction.S, Direction.E);
        goingLeft.put(Direction.E, Direction.N);
        this.heading = goingLeft.get(this.heading);
    }
    // updates drone location based on current heading
    public void updatedFlyingCoordinates() {
        switch(this.heading) {
            case Direction.N:
                this.y+=1;
                break;
            case Direction.S:
                this.y-=1;
                break;
            case Direction.E:
                this.x+=1;
                break;
            case Direction.W:
                this.x-=1;
                break;
            default:
                break;
        }
    }
    // updates drone location based on heading
    public void updatedHeadingCoordinates(Direction direction) {
        // takes into account of forward movement when calling the heading command
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
                break;
        }
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
