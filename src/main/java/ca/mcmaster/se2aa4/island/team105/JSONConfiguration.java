package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team105.Drone.BatteryLevel;
import ca.mcmaster.se2aa4.island.team105.Enums.Decision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringReader;

public class JSONConfiguration {
    
    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    JSONObject parameter = new JSONObject();
    private BatteryLevel level;

    
    public String decisionWrap(Decision takeDecision) {
        
        switch (takeDecision) {
            
            case FLY:
                decision.put("action", "fly");
                logger.info("** Decision: {}",decision.toString());
                return decision.toString();
            // change heading for limitation case
            case HEADING:
                decision.put("action", "heading");
                parameter.put("direction", "S");
                decision.put("parameters", parameter);
                logger.info("** Decision: {}",decision.toString());
                return decision.toString();
            // need direction for echo
            case ECHO:
                decision.put("action", "echo");
                parameter.put("direction", "S");
                decision.put("parameters", parameter);
                logger.info("** Decision: {}",decision.toString());
                return decision.toString();
            case SCAN:
                decision.put("action", "scan");
                return decision.toString();
            case STOP:
                decision.put("action", "stop");
                return decision.toString();
            // need to reconfigure
            case LAND:
                decision.put("action", "land");
                parameter.put("direction", "S");
                decision.put("parameters", parameter);
                logger.info("** Decision: {}",decision.toString());
                return decision.toString();
            



        }
        
        decision.put("action", "fly");
        return null;

    }

    public void initializationWrap(String s, BatteryLevel level) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        this.level = new BatteryLevel(info.getInt("budget"));  // Create the BatteryLevel object
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.level.getLevel());
    }

    public String takeDecisionWrap(BatteryLevel level) {
        JSONObject decision = new JSONObject();
        JSONObject parameter = new JSONObject();
        decision.put("action", "heading");
        parameter.put("direction", "S");
        decision.put("parameters", parameter);
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
        logger.info("Additional information received: {}", extraInfo);
    }

}