package ca.mcmaster.se2aa4.island.team105;

import eu.ace_design.island.bot.IExplorerRaid;
import ca.mcmaster.se2aa4.island.team105.Configuration.JSONConfiguration;

public class Explorer implements IExplorerRaid {

    JSONConfiguration initialize = new JSONConfiguration();

    @Override
    public void initialize(String s) {
        initialize.initializationWrap(s);
    }

    // not coded
    @Override
    public String takeDecision () {
        return initialize.takeDecisionWrap();
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
