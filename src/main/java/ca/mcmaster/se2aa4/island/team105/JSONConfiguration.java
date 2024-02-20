package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team105.Enums.Decision;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JSONConfiguration {
    
    private final Logger logger = LogManager.getLogger();
    JSONObject decision = new JSONObject();
    JSONObject parameter = new JSONObject();

    
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
}
