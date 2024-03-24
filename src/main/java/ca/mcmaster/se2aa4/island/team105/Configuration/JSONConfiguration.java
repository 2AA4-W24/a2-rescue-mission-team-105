package ca.mcmaster.se2aa4.island.team105.Configuration;
import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team105.DecisionMaker;
import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import ca.mcmaster.se2aa4.island.team105.Map.SubObserver;
import ca.mcmaster.se2aa4.island.team105.Map.ExplorerMap;
import ca.mcmaster.se2aa4.island.team105.Map.Translator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringReader;

// Christina Zhang, Victor Yu, Kevin Kim
// 24/03/2024
// 2AA4 <T01>
// Software Engineering
// Facilitates and communicates with other classes in taking information and running rescue missions. Handles JSON-formatted data
// and response acknowledgement and decision processing

public class JSONConfiguration {

    // private objects communicating with the class
    private final Logger logger = LogManager.getLogger();
    protected JSONObject decision = new JSONObject();
    protected JSONObject parameter = new JSONObject();
    private Drone level;
    private Limitations limitation;
    private Actions action = new Actions(decision);
    private Direction direction;
    private SubObserver decisionMaker = new DecisionMaker(); 
    private Translator translate = new Translator();

    // sets up essential components for JSON configuration
    public void initializationWrap(String s) {
        translate.addObserver(decisionMaker);
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        this.level = new Drone(info.getInt("budget"), direction);// Create the BatteryLevel object
        this.limitation = new Limitations(this.level); // Instantiate the Limitations object
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.level.getLevel());
    }

    // takes users decision and returns it as a string
    public String takeDecisionWrap() {
        decision = ((DecisionMaker) decisionMaker).getDecision(limitation, level, direction, action, parameter);
        logger.info(level.getX() + " " + level.getY());
        logger.info("** Decision: {}", decision.toString());
        logger.info("Battery level is now {}", this.level.getLevel());
        return decision.toString();
    }

    // updates battery, communicates with translator, and process decision received
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
        logger.info(level.getX() + " " + level.getY());
        limitation.returnHome(action);
        translate.setInfo(response);
    }

}