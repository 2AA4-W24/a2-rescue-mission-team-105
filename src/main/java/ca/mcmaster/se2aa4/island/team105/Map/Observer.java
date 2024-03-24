package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONObject;

// defines the contract for objects in observing changes in exploration data
public interface Observer {
    // updates observer with new data
    void update(JSONObject extras);

}
