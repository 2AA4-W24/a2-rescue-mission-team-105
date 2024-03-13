package ca.mcmaster.se2aa4.island.team105.Drone;

import org.json.JSONObject;


import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class Actions {
    
    private JSONObject decision;
    // private JSONObject parameter;
    // private JSONObject direction;

    public Actions(JSONObject decision) {
        this.decision = decision;
        // this.direction = direction;
    }

    public void stop() {
        decision.put("action", "stop");
    }

    public void fly (Drone drone) {
        decision.put("action", "fly");
        drone.updatedFlyingCoordinates();
        
    }

    public void heading(JSONObject parameter, Direction direction) {
        decision.put("action", "heading");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    public void echo(JSONObject parameter, Direction direction) {
        decision.put("action", "echo");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    public void scan() {
        decision.put("action", "scan");
    }

    public void land(JSONObject parameter, JSONObject id) {
        decision.put("action", "land");
        decision.put("parameter", parameter);
    }

    public void moveTo(JSONObject parameter, Direction direction) {
        decision.put("action", "move_to");
        parameter.put("direction", direction);
        decision.put("parameter", parameter);
    }

    public void scout(JSONObject parameter, Direction direction) {
        decision.put("action", "scout");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
    }

    // might not need this
    public void glimpse(JSONObject parameter, Direction direction, JSONObject range) {
        decision.put("action", "glimpse");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        decision.put("range", 1);
    }

    public void explore() {
        decision.put("action", "explore");
    }

}
