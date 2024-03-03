package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team105.Drone.BatteryLevel;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Map.MapTile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringReader;

public class JSONConfiguration {


    private final Logger logger = LogManager.getLogger();
    private JSONObject decision = new JSONObject();
    private JSONObject parameter = new JSONObject();
    private BatteryLevel level;
    private Limitations limitation;  // Declare the Limitations object
    private MapTile scaninfo = new MapTile();

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
        decision.put("action", "scan");
        //parameter.put("direction", "S");
        //decision.put("parameters", parameter);
        logger.info("** Decision: {}", decision.toString());
        // decrement battery level for each iteration
        logger.info("Battery level is now {}", this.level.getLevel());
        limitation.returnHome();  // Call returnHome at the appropriate place
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
        scaninfo.poiIdentifier(extraInfo);
        logger.info("Additional information received: {}", extraInfo);
        limitation.returnHome();
    }
}