package ca.mcmaster.se2aa4.island.team105.Drone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team105.Configuration.JSONConfiguration;

public class Limitations extends JSONConfiguration {
    private BatteryLevel level;
    private final Logger logger = LogManager.getLogger();

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(BatteryLevel level) {
        this.level = level;
    }

    public void returnHome(Actions action) {
        if (this.level.getLevel() <= 6800) {
            logger.info("Battery level is equal to or below 6800. Returning home");
            action.stop(decision);
        } else {
            logger.info("Battery level is greater than 6800. Continuing exploration.");
        }
    }
}
