package ca.mcmaster.se2aa4.island.team105.Drone;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class Drone {
    // save battery from json object
    // after every call 7000 - cost
    // once we hit 250 we stop and return home
    // create json object, key, value would be another json object, value for the cost would be 5
    private Direction heading;
    public Drone() {
        try{
            String jsonData = new String(Files.readAllBytes(Paths.get("./outputs/Explorer_Island.json")));
            JSONArray jsondata = new JSONArray(jsonData);
            JSONObject json = jsondata.getJSONObject(0);
            JSONObject data = json.getJSONObject("data");
            String starting = data.getString("heading");
            this.heading = Direction.valueOf(starting);
            System.out.println(this.heading);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // call actions 

    public Direction getHeading() {
        return(this.heading);
    }
    
}
