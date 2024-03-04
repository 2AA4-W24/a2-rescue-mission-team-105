package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MapTile {

    public String siteId;
    public String creekId;
    public String biomes;
    public String echoFound;
    public int echoRange;
    public boolean ground;

    public MapTile() {
        this.creekId = "no creek found";
        this.siteId = "no site found";
        this.biomes = "no biomes found";
        this.echoFound = "OUT_OF_RANGE";
        this.echoRange = 0;
        this.ground = false;
    }

    public void updateInfo(JSONObject extras) {
        if (extras.has("creeks")) { // if the action taken is scanned update the scan attributes (we can change this later to something to like a boolean idk)
            JSONArray creekIds = extras.getJSONArray("creeks"); 
            JSONArray siteIds = extras.getJSONArray("sites");
            JSONArray biomeIds = extras.getJSONArray("biomes");


            if (creekIds.length() != 0) this.creekId = creekIds.getString(0);
            if (siteIds.length() != 0) this.siteId = siteIds.getString(0);
            this.biomes = biomeIds.getString(0);
           
        } else if (extras.has("range")) { // if the action taken is echo update echo attributes
            this.echoFound = extras.getString("found");
            this.ground = echoFound == "OUT_OF_RANGE" ? false : true;
            this.echoRange = extras.getInt("range");
        }
        
    }

    public boolean scanned = false; //if the area has been scanned

}
