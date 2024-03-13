package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;

public class Translator {
    private int cost;
    private JSONObject extras;
    private Drone battery;
    
    //creates a translator that takes the response from JSONConfiguration
    public Translator(JSONObject response, Drone battery) {
        this.cost = response.getInt("cost");
        this.extras = response.getJSONObject("extras");
        this.battery = battery;
    }

    public Translator() {

    }

    //methods to update information class
    public String getBiome() {
        if (extras.has("biomes")) {
            JSONArray biomes = extras.getJSONArray("biomes");
            return biomes.getString(0);
        } else return null;

    }

    public String getCreek() {
        if (extras.has("creeks")) {
            JSONArray biomes = extras.getJSONArray("creeks");
            return biomes.getString(0);
        } else return null;

    }

    public String getSite() {
        if (extras.has("sites")) {
            JSONArray biomes = extras.getJSONArray("sites");
            return biomes.getString(0);
        } else return null;

    }

    public int getBattery() {
        battery.setLevel(battery.getLevel() - cost);
        return battery.getLevel();
    }
    

}
