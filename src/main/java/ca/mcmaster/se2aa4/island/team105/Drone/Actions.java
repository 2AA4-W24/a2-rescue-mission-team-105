package ca.mcmaster.se2aa4.island.team105.Drone;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class Actions {
    
    Drone drone;
    RelativeCoordinate coordinates;

    public void stop(JSONObject decision) {
        decision.put("action", "stop");
    }

    public void fly (JSONObject decision) {
        decision.put("action", "fly");
        coordinates.updatedFlyingCoordinates(drone.getHeading());
        
    }

    public void heading(JSONObject decision, JSONObject parameter, Direction direction) {
        decision.put("action", "heading");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    public void echo(JSONObject decision, JSONObject parameter, Direction direction) {
        decision.put("action", "echo");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    public void scan(JSONObject decision) {
        decision.put("action", "scan");
    }

    public void land(JSONObject decision, JSONObject parameter, JSONObject id) {
        decision.put("action", "land");
        decision.put("parameter", parameter);
    }

    public void moveTo(JSONObject decision, JSONObject parameter, Direction direction) {
        decision.put("action", "move_to");
        parameter.put("direction", direction);
        decision.put("parameter", parameter);
    }

    public void scout(JSONObject decision, JSONObject parameter, Direction direction) {
        decision.put("action", "scout");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    // might not need this
    public void glimpse(JSONObject decision, JSONObject parameter, Direction direction, JSONObject range) {
        decision.put("action", "glimpse");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        decision.put("range", 1);
    }

    public void explore(JSONObject decision) {
        decision.put("action", "explore");
    }

}
