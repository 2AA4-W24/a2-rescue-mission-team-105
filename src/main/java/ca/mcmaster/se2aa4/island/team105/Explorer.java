package ca.mcmaster.se2aa4.island.team105;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team105.Drone.BatteryLevel;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private BatteryLevel level;
    private Limitations limitations;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        this.level = new BatteryLevel(info.getInt("budget"));  // Create the BatteryLevel object
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.level.getLevel());
        this.limitations = new Limitations(this.level);

    }
    // not coded
    @Override
    public String takeDecision() {
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

    
    @Override
    public void acknowledgeResults(String s) {
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
        limitations.returnHome();
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
