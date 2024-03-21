package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Information implements Observer, TranslateSubject{
    public String biome;
    public String creek;
    public String site;
    public int batteryLevel;
    private String found;
    private int range;
    private List<SubObserver> subObservers = new ArrayList<>();

    public Information() {
        this.biome = "";
        this.creek = ""; 
        this.site = "";
        this.batteryLevel = 0;
        this.found = "";
        this.range = 0;
    }

    public void addObserver(SubObserver subObserver) {
        subObservers.add(subObserver);
    }

    public void notifyObservers()  {
        for (SubObserver subObserver : subObservers) {
            subObserver.update(this.found, this.range);
        }
    }

    @Override
    public void update(JSONObject extras) {
        if (extras.has("found")) {
            this.found = extras.getString("found");
            this.range = extras.getInt("range");
            notifyObservers();
        }
    }

}
