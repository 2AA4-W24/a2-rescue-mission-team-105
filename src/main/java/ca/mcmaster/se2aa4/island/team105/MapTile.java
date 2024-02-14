package ca.mcmaster.se2aa4.island.team105;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapTile {
    private List<String> creekIdentifier(JSONObject extras) {
        JSONArray ids = extras.getJSONArray("creeks");  //gets "creeks" value from "extras" from acknowledgeResults
        List<String> creekList = new ArrayList<String>();
        if (ids.length() > 0) { //puts elements of the JSON array into an array list
            for (int i = 0; i < ids.length(); i++) {
                creekList.add(ids.getString(i));
            }
            return creekList; //returns the list
        } 
        return null; //if no creek is found, return null
    }

    private List<String> siteIdentifier(JSONObject extras) { //does the above with sites
        JSONArray ids = extras.getJSONArray("sites");   
        List<String> siteList = new ArrayList<String>();
        if (ids.length() > 0) {
            for (int i = 0; i < ids.length(); i++) {
                siteList.add(ids.getString(i));
            }
            return siteList;
        } 
        return null;
    }

    private List<String> biomeType(JSONObject extras) { //does the above with biomes
        JSONArray ids = extras.getJSONArray("biomes");
        List<String> biomeList = new ArrayList<String>();
        if (ids.length() > 0) {
            for (int i = 0; i < ids.length(); i++) {
                biomeList.add(ids.getString(i));
            }
            return biomeList;
        } 
        return null;
    }

    private boolean scanned = false; //if the area has been scanned

}
