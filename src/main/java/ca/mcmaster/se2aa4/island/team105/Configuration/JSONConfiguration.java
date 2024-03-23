package ca.mcmaster.se2aa4.island.team105.Configuration;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team105.DecisionMaker;
import ca.mcmaster.se2aa4.island.team105.Drone.Actions;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import ca.mcmaster.se2aa4.island.team105.Drone.Limitations;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;
import ca.mcmaster.se2aa4.island.team105.Map.Translator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.StringReader;

public class JSONConfiguration {

    private final Logger logger = LogManager.getLogger();
    protected JSONObject decision = new JSONObject();
    protected JSONObject parameter = new JSONObject();
    private Drone level;
    private Limitations limitation;  // Declare the Limitations object
    Actions action = new Actions(decision);
    Direction direction;
    private DecisionMaker decisionMaker = new DecisionMaker(); // need to keep it outside
    private Direction originalDirection;
    

     // maybe change later

    public void initializationWrap(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}", info.toString(2));
        String direction = info.getString("heading");
        this.level = new Drone(info.getInt("budget"), direction);// Create the BatteryLevel object
        this.limitation = new Limitations(this.level);  // Instantiate the Limitations object
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", this.level.getLevel());
    }

    public String takeDecisionWrap() {
        originalDirection = level.getHeading();
        decisionMaker.findMapBox(limitation, level, direction, action, parameter);
        // decisionMaker.gridSearch(action, level, limitation, direction, parameter);
        // decision = action.scan();
        decision = decisionMaker.getDecision();
        logger.info(level.getX() + " " + level.getY());
        logger.info("** Decision: {}", decision.toString());
        logger.info("Battery level is now {}", this.level.getLevel());       
        return decision.toString();
    }

    // have a variable make it equal to whatever the action is, then
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

        Translator translator = new Translator(response, level);
        logger.info("found ground is " + translator.foundGround());
        decisionMaker.decisionUpdate(translator.foundGround(), translator.getRange());
    }


}