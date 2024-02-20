package ca.mcmaster.se2aa4.island.team105.Drone;

public class Limitations {
    private BatteryLevel level;

    // Constructor to initialize Limitations with a BatteryLevel instance
    public Limitations(BatteryLevel level) {
        this.level = level;
    }

    public void returnHome() {
        if (this.level.getLevel() == 2000) {
            System.out.println("Battery level is 2000. Returning home.");
        } else {
            System.out.println("Battery level is not 2000. Continuing exploration.");
        }
    }
}
