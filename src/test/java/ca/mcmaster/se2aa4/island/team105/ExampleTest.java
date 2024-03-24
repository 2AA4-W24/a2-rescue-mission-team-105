package ca.mcmaster.se2aa4.island.team105;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static eu.ace_design.island.runner.Runner.run;

import java.io.File;

import ca.mcmaster.se2aa4.island.team105.Drone.*;
import ca.mcmaster.se2aa4.island.team105.Enums.Direction;

import ca.mcmaster.se2aa4.island.team105.Map.ExplorerMap;
public class ExampleTest {

    // A test for the drone class for the current jsonArray output

    @Test
    public void droneClass() {
        // Creates class
        Drone testDrone = new Drone(7000, "E");
        Direction heading = testDrone.getHeading();
        Direction east = Direction.E;
        // Checks if the heading was east because for this file it should be east
        assertTrue(heading == east);
    }

    @Test
    public void correctDirection() {
        Limitations limitations = new Limitations();
        Direction desiredDirection = Direction.W;
        boolean result = limitations.is180DegreeTurn(desiredDirection);
        assertTrue(result);
    }

    @Test
    public void wrongDirection() {
        Limitations limitations = new Limitations();
        Direction desiredDirection = Direction.N;
        boolean result = limitations.is180DegreeTurn(desiredDirection);
        assertFalse(result);
    }

    @Test
    public void setGetLocation(){
        Drone drone = new Drone(7000, "E");
        ExplorerMap testMap = new ExplorerMap(drone);
        testMap.setLocation(0, 0, "test_info");
        String info = testMap.getLocation(0, 0);
        assertTrue(info == "test_info");
    }
    
    @Test
    public void updateCurrentLoation(){
        Drone drone = new Drone(7000, "E");
        ExplorerMap testMove = new ExplorerMap(drone);
        testMove.updateCurrentPoint(2, 2);
        testMove.setLocation(0, 2, "Different location");
        String info = testMove.getLocation(2, 4);
        assertTrue(info == "Different location");
    }

    @Test
    public void radarTest(){
        Drone drone = new Drone(7000, "E");
        ExplorerMap testRadar = new ExplorerMap(drone);
        testRadar.setEchoInfo(0, 5, false);
        String info = testRadar.getLocation(0, 3);
        assertTrue(info == "Ocean");
    }
    @Test
    public void map03Test() {
        String filename = "./maps/map03.json";
        try {
            run(Explorer.class)
                    .exploring(new File(filename))
                    .withSeed(42L)
                    .startingAt(1, 1, "EAST")
                    .backBefore(7000)
                    .withCrew(5)
                    .collecting(1000, "WOOD")
                    .storingInto("./outputs")
                    .withName("Island")
                    .fire();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
