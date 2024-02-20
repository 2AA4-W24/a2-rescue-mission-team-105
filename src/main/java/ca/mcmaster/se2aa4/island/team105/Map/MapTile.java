package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapTile {
    private List<String> creekIdentifier(JSONObject extras) {
        JSONArray ids = extras.getJSONArray("creeks");
        List<String> idlist = new ArrayList<String>();
        if (ids.length() > 0) {
            for (int i = 0; i < ids.length(); i++) {
                idlist.add(ids.getString(i));
            }
            return idlist;
        } 
        return null;
    }

    private List<String> siteIdentifier(JSONObject extras) {
        JSONArray ids = extras.getJSONArray("creeks");
        List<String> idlist = new ArrayList<String>();
        if (ids.length() > 0) {
            for (int i = 0; i < ids.length(); i++) {
                idlist.add(ids.getString(i));
            }
            return idlist;
        } 
        return null;
    }

    private String biomeType(JSONObject extras) {
        String biome = extras.getString("biomes");
        return biome;
    }

    private boolean scanned = false;

}
