package ca.mcmaster.se2aa4.island.team105;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Integer batteryLevel;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        this.batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.batteryLevel);
    }
    // not coded
    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        // JSONObject params = new JSONObject();
        // params.put("direction", "E");
        // decision.put("parameters", params); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());
        // decrement battery level for each iteration
        logger.info("Battery level is now {}", this.batteryLevel);
        return decision.toString();
    }

    
    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        // battery level after receiving results
        this.batteryLevel -= cost;
        logger.info("Battery level is now {}", this.batteryLevel);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
