package ca.mcmaster.se2aa4.island.team105.Drone;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.EnumMap;


import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class Drone {
    //This is private becase we will further create the services in this class
    private Direction heading;
    
    //sets the heading as soon as we create the object
    public Drone(String starting) {
        try{
            /*
            //Opens the file
            String jsonData = new String(Files.readAllBytes(Paths.get("./outputs/Explorer_Island.json")));
            //Reads the file into JSONArray
            JSONArray jsondata = new JSONArray(jsonData);
            //Goes into the JSONarray and finds [data] then [heading]
            JSONObject json = jsondata.getJSONObject(0);
            JSONObject data = json.getJSONObject("data");
            String starting = data.getString("heading");
            //takes the value found and sets the required enum
            */
            this.heading = Direction.valueOf(starting);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // call actions 
    //Simple return to check the functionality
    public Direction getHeading() {
        return(this.heading);
    }
    public void headRight(){
        Map<Direction, Direction> goingRight = new EnumMap<>(Direction.class);
        goingRight.put(Direction.N, Direction.E);
        goingRight.put(Direction.E, Direction.S);
        goingRight.put(Direction.S, Direction.W);
        goingRight.put(Direction.W, Direction.N);
        this.heading = goingRight.get(this.heading);
    }
    public void headLeft(){
        Map<Direction, Direction> goingLeft = new EnumMap<>(Direction.class);
        goingLeft.put(Direction.N, Direction.W);
        goingLeft.put(Direction.W, Direction.S);
        goingLeft.put(Direction.S, Direction.E);
        goingLeft.put(Direction.E, Direction.N);
        this.heading = goingLeft.get(this.heading);
    }
    
}
