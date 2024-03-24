package ca.mcmaster.se2aa4.island.team105;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {
    // instantiates new JSONConfiguration object
    JSONConfiguration initialize = new JSONConfiguration();
    
    // runs all the methods encapsulated in JSONConfiguration class
    @Override
    public void initialize(String s) {
        initialize.initializationWrap(s);
    }

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
