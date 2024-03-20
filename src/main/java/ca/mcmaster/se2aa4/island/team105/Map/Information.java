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
    private List<SubObserver> subObservers = new ArrayList<>();

    public Information() {
        this.biome = "";
        this.creek = ""; //whenever a new information object is made, methods to update Information from Translator runs
        this.site = "";
        this.batteryLevel = 0;
        this.found = "";
    }

    public void addObserver(SubObserver subObserver) {
        subObservers.add(subObserver);
    }

    public void notifyObservers()  {
        for (SubObserver subObserver : subObservers) {
            subObserver.update(this.found);
        }
    }

    @Override
    public void update(JSONObject extras) {
        if (extras.has("found")) {
            this.found = extras.getString("found");
            notifyObservers();
        }
    }



    
    //more methods, ex. isground or whateva 

}
