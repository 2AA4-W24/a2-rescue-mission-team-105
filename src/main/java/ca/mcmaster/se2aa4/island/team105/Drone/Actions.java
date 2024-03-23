package ca.mcmaster.se2aa4.island.team105.Drone;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public class Actions {
    
    private JSONObject decision;

    public Actions(JSONObject decision) {
        this.decision = decision;
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
        decision = new JSONObject();
        decision.put("action", "scan");
        return decision;

    }

}
