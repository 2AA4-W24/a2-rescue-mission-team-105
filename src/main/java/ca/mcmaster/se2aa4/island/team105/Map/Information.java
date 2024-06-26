package ca.mcmaster.se2aa4.island.team105.map;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

// Christina Zhang, Victor Yu, Kevin Kim
// 24/03/2024
// 2AA4 <T01>
// Software Engineering
// Represents information objects to store and communicate exploration data 
// such as range, and biome information. Utilizes an observer pattern by extending `TranslateSubject` 
// in notifying other objects of data updates.

public class Information extends TranslateSubject {
    // private variables for information storing
    private JSONArray biomes;
    private JSONArray creeks;
    private JSONArray sites;
    private String found;
    private int range;
    private int cost;
    private List<SubObserver> subObservers = new ArrayList<>();

    // constructs instances with default values
    public Information() {
        this.biomes = new JSONArray();
        this.creeks = new JSONArray(); 
        this.sites = new JSONArray();
        this.cost = 0;
        this.found = "";
        this.range = 0;
    }

    // adds subObservers to the list of observers
    @Override
    public void addObserver(SubObserver subObserver) {
        subObservers.add(subObserver);
    }

    // Notifies all observers about the changes related to the map
    @Override
    public void notifyObservers() {
        for (SubObserver subObserver : subObservers) {
            subObserver.update(this.found, this.range, this.biomes, this.cost, this.sites, this.creeks);
        }
    }

    // updates information based on JSONObject
    public void update(JSONObject extras, int cost) {
        this.cost = cost;
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
