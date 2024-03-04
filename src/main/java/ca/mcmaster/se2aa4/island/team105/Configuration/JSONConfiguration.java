package ca.mcmaster.se2aa4.island.team105.Configuration;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.BatteryLevel;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Map.MapTile;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringReader;

public class JSONConfiguration {

    private final Logger logger = LogManager.getLogger();
    protected JSONObject decision = new JSONObject();
    protected JSONObject parameter = new JSONObject();
    private BatteryLevel level;
    private Limitations limitation;  // Declare the Limitations object
    private MapTile info = new MapTile();
    private int decisionCount;
    private Actions action;

    public void initializationWrap(String s, BatteryLevel level) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        this.level = new BatteryLevel(info.getInt("budget"));  // Create the BatteryLevel object
        this.limitation = new Limitations(this.level);  // Instantiate the Limitations object
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.level.getLevel());
    }

    public String takeDecisionWrap(BatteryLevel level) {
        decisionCount++;
        if (decisionCount == 20) {
            limitation.returnHome(action);  // Call returnHome at the appropriate place
        }
        
        else if (decisionCount == 10) {
            decision.put("action", "echo");
            parameter.put("direction", "N");
            decision.put("parameters", parameter);
        }

        else if (decisionCount == 5) {
            limitation.badCommand(action, Direction.W);
        }

        else if (decisionCount > 0)  {
            decision.put("action", "fly");
        }

        else {
            Direction desiredDirection = Direction.E; // Replace with the actual desired direction
            limitation.badCommand(action, desiredDirection);
        }

        // else if (decisionCount % 2 == 0) {
        //     action.echo(decision, parameter, direction);
        // }
        
        logger.info("** Decision: {}", decision.toString());
        // decrement battery level for each iteration
        logger.info("Battery level is now {}", this.level.getLevel());        
        return decision.toString();
    }

    public void acknowledgeResultsWrap(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n" + response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        // battery level after receiving results
        this.level.setLevel(this.level.getLevel() - cost);
        logger.info("Battery level is now {}", this.level.getLevel());
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        info.updateInfo(extraInfo);
        logger.info("Additional information received: {}", extraInfo);
        limitation.returnHome(action);
    }
}