package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

public interface SearchMethods  {
    
    public void findMapBox(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter);

    public void gridSearch(Limitations limitation, Drone drone, Direction direction, Actions action, JSONObject parameter, Drone battery);    


}
