package ca.mcmaster.se2aa4.island.team105;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static eu.ace_design.island.runner.Runner.run;

import java.io.File;

import ca.mcmaster.se2aa4.island.team105.drone.*;
import ca.mcmaster.se2aa4.island.team105.enums.Direction;
import ca.mcmaster.se2aa4.island.team105.map.ExplorerMap;
public class ExampleTest {

    private Drone testDrone;
    private Limitations limitations;
    private Direction heading;
    private ExplorerMap testMap;
    private JSONObject decision1;
    private JSONObject decision2;
    private Actions actions;
    private int batteryLevel;
    private DecisionMaker decisionMaker;


    
    @BeforeEach
    public void setUp() {
        testDrone = new Drone(7000, "E");
        heading = testDrone.getHeading();
        testMap = new ExplorerMap();
        actions = new Actions(decision1); // Create new JSONObject for decision1
        decision2 = new JSONObject();
        decisionMaker = new DecisionMaker();
        limitations = new Limitations(testDrone);
    }

    @Test
    public void droneClass() {
        Direction east = Direction.E;
        // Checks if the heading was east because for this file it should be east
        assertTrue(heading == east);
    }

    @Test
    public void correctDirection() {
        Direction desiredDirection = Direction.W;
        boolean result = limitations.is180DegreeTurn(desiredDirection);
        assertTrue(result);
    }

    @Test
    public void wrongDirection() {
        Direction desiredDirection = Direction.N;
        boolean result = limitations.is180DegreeTurn(desiredDirection);
        assertFalse(result);
    }

    @Test
    public void setGetLocation(){
        testMap.setLocation(0, 0, "test_info");
        String info = testMap.getLocation(0, 0);
        assertTrue(info == "test_info");
    }
    
    @Test
    public void updateCurrentLoation(){
        testMap.updateCurrentPoint(2, 2);
        testMap.setLocation(0, 2, "Different location");
        String info = testMap.getLocation(2, 4);
        assertTrue(info == "Different location");
    }

    @Test
    public void radarTest(){
        testMap.setEchoInfo(0, 5, false);
        String info = testMap.getLocation(0, 3);
        assertTrue(info == "Ocean");
    }

    @Test
    public void actionStopTest() {
        decision1 = actions.stop();
        decision2.put("action", "stop");
        assertTrue(decision2.toString().equals(decision1.toString()));
    }

    @Test
    public void actionStopFalseTest() {
        decision1 = actions.scan();
        decision2.put("action", "stop");
        assertFalse(decision2.toString().equals(decision1.toString()));
    }

    @Test
    public void actionScanFalseTest() {
        decision1 = actions.scan();
        decision2.put("action", "fly");
        assertFalse(decision2.toString().equals(decision1.toString()));
    }

    @Test
    public void getBatteryLevel() {
        batteryLevel = testDrone.getLevel();
        assertTrue(batteryLevel == 7000);
    }

    @Test
    public void rightOrientationTest() {
        assertTrue(testDrone.rightOrientation(heading, testDrone).equals(Direction.S));
    }

    @Test
    public void leftOrientation() {
        assertTrue(testDrone.leftOrientation(heading, testDrone).equals(Direction.N));
    }

    @Test
    public void orientationTrue() {
        Direction newHeading = Direction.E;
        assertTrue(newHeading == testDrone.orientation(heading, testDrone));
    }

    @Test
    public void orientationFalse() {
        Direction newHeading = Direction.W;
        assertFalse(newHeading == testDrone.orientation(heading, testDrone));
    }

    @Test
    public void map03Test() {
        String filename = "./maps/map03.json";
        try {
            run(Explorer.class)
                    .exploring(new File(filename))
                    .withSeed(42L)
                    .startingAt(1, 100, "EAST")
                    .backBefore(2000)
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
