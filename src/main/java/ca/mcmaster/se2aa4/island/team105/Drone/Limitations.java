package ca.mcmaster.se2aa4.island.team105.Drone;

import ca.mcmaster.se2aa4.island.team105.JSONConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Limitations extends JSONConfiguration {
    private BatteryLevel level;
    private final Logger logger = LogManager.getLogger();

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(BatteryLevel level) {
        this.level = level;
    }

    public void returnHome() {
        if (this.level.getLevel() == 2000) {
            logger.info("Battery level is 2000. Returning home.");
        } else {
            logger.info("Battery level is not 2000. Continuing exploration.");
        }
    }
}
