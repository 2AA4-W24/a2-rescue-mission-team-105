package ca.mcmaster.se2aa4.island.team105.Map;

public class Information extends Translator{
    private String biome;
    private String creek;
    private String site;
    private int batteryLevel;

    public Information() {
        this.biome = getBiome(); //maybe make an enum of this
        this.creek = getCreek(); //whenever a new information object is made, methods to update Information from Translator runs
        this.site = getSite();
        this.batteryLevel = getBattery();
    }

}