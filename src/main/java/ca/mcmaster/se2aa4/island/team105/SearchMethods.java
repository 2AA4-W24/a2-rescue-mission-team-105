package ca.mcmaster.se2aa4.island.team105;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.drone.Actions;
import ca.mcmaster.se2aa4.island.team105.drone.Drone;
import ca.mcmaster.se2aa4.island.team105.drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.enums.Direction;


// DecisionMaker takes on this interface for different search methods
public interface SearchMethods  {
    void setup(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);
    // uses all objects necessary to find edge of the map
    void findMapEdge(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);
    // uses all objects necessary for grid search
    void gridSearch(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);

}
