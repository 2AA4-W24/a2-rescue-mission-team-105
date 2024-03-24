package ca.mcmaster.se2aa4.island.team105;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;


// DecisionMaker takes on this interface for different search methods
public interface SearchMethods  {
    // uses all objects necessary to find edge of the map
    public void findMapEdge(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);
    // uses all objects necessary for grid search
    public void gridSearch(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);

}
