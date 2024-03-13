package ca.mcmaster.se2aa4.island.team105.Drone;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class RelativeCoordinate {

    private Integer x, y;
    Direction direction;

    public RelativeCoordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void updatedFlyingCoordinates(Direction direction2) {
        switch(direction) {
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
