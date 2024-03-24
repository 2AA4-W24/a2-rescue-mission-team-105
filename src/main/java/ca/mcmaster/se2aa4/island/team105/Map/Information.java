package ca.mcmaster.se2aa4.island.team105.Map;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

// Represents information objects to store and communicate exploration data 
// such as range, and biome information. Utilizes an observer pattern by extending `TranslateSubject` 
// in notifying other objects of data updates.
public class Information extends TranslateSubject{
    
    private JSONArray biomes;
    private JSONArray creeks;
    private JSONArray sites;
    public int batteryLevel;
    private String found;
    private int range;
    private List<SubObserver> subObservers = new ArrayList<>();

    public Information() {
        this.biomes = null;
        this.creeks = null; 
        this.sites = null;
        this.batteryLevel = 0;
        this.found = "";
        this.range = 0;
    }
    
    @Override
    public void addObserver(SubObserver subObserver) {
        subObservers.add(subObserver);
    }

    @Override
    public void notifyObservers()  {
        for (SubObserver subObserver : subObservers) {
            subObserver.update(this.found, this.range, this.biomes);
        }
    }

    @Override
    public void update(JSONObject extras) {
        if (extras.has("found")) {
            this.found = extras.getString("found");
            this.range = extras.getInt("range");
        } else if (extras.has("biomes")) {
            this.biomes = extras.getJSONArray("biomes");
            this.creeks = extras.getJSONArray("creeks");
            this.sites = extras.getJSONArray("sites");
        }
        notifyObservers();
    }

}
