package ca.mcmaster.se2aa4.island.team105;

import eu.ace_design.island.bot.IExplorerRaid;
import ca.mcmaster.se2aa4.island.team105.Drone.BatteryLevel;
import ca.mcmaster.se2aa4.island.team105.Map.MapTile;


public class Explorer implements IExplorerRaid {

    private BatteryLevel level;
    JSONConfiguration initialize = new JSONConfiguration();
    MapTile test = new MapTile();

    @Override
    public void initialize(String s) {
        initialize.initializationWrap(s, level);
    }

    // not coded
    @Override
    public String takeDecision () {
        return initialize.takeDecisionWrap(level);
    }

    
    @Override
    public void acknowledgeResults(String s) {
        initialize.acknowledgeResultsWrap(s);
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
