package ca.mcmaster.se2aa4.island.team105;

import static eu.ace_design.island.runner.Runner.run;

import java.io.File;
// runs the rescue mission, can modify information for the mission
public final class Runner {

    private Runner() {
        
    }
    public static void main(String[] args) {
        String filename = args[0];
        try {
            run(Explorer.class)
                    .exploring(new File(filename))
                    .withSeed(42L)
                    // .startingAt(1, 150, "NORTH")
                    // .startingAt(80, 160, "NORTH")
                    // .startingAt(160, 30, "WEST")
                    .startingAt(1, 130, "EAST")
                    .backBefore(7000)
                    .withCrew(5)
                    .collecting(1000, "WOOD")
                    .storingInto("./outputs")
                    .withName("Island")
                    .fire();
        } catch(Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
