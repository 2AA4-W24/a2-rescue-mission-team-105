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

    public JSONObject stop() {
        decision = new JSONObject();
        decision.put("action", "stop");
        return decision;
    }

    public JSONObject fly (Drone drone) {
        decision = new JSONObject();
        decision.put("action", "fly");
        drone.updatedFlyingCoordinates();
        return decision;

    }

    public JSONObject heading(JSONObject parameter, Direction direction, Drone drone) {
        decision = new JSONObject();
        parameter = new JSONObject();
        decision.put("action", "heading");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        // drone.updatedFlyingCoordinates();
        drone.updatedHeadingCoordinates(direction);
        return decision;

    }

    public JSONObject echo(JSONObject parameter, Direction direction) {
        
        decision.put("action", "echo");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        return decision;

    }

    public JSONObject scan() {
        decision.put("action", "scan");
        return decision;

    }

    public JSONObject land(JSONObject parameter, JSONObject id) {
        decision.put("action", "land");
        decision.put("parameter", parameter);
        return decision;

    }

    public JSONObject moveTo(JSONObject parameter, Direction direction) {
        decision.put("action", "move_to");
        parameter.put("direction", direction);
        decision.put("parameter", parameter);
        return decision;

    }

    public JSONObject scout(JSONObject parameter, Direction direction) {
        decision.put("action", "scout");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        return decision;

    }

    // might not need this
    public JSONObject glimpse(JSONObject parameter, Direction direction, JSONObject range) {
        decision.put("action", "glimpse");
        parameter.put("direction", direction);
        decision.put("parameters", parameter);
        decision.put("range", 1);
        return decision;

    }

    public JSONObject explore() {
        decision.put("action", "explore");
        return decision;

    }

}
