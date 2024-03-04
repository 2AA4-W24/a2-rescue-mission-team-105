package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapTile {

    public String siteId;
    public String creekId;
    public String biomes;

    public MapTile() {
        this.creekId = "no creek found";
        this.siteId = "no site found";
        this.biomes = "no biomes found";
    }

    public void poiIdentifier(JSONObject extras) {
        if (extras.has("creeks")) { //whenver acknowledgeResults is called, call this one?
            JSONArray creekIds = extras.getJSONArray("creeks"); 
            JSONArray siteIds = extras.getJSONArray("sites");
            JSONArray biomeIds = extras.getJSONArray("biomes");


            if (creekIds.length() != 0) this.creekId = creekIds.getString(0);
            if (siteIds.length() != 0) this.siteId = siteIds.getString(0);
            this.biomes = biomeIds.getString(0);
           
        }
        
    }

    public boolean scanned = false; //if the area has been scanned

}
