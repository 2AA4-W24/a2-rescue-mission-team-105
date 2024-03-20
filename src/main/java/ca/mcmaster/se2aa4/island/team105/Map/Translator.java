package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team105.Drone.Drone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Translator {
    private int cost;
    private JSONObject extras;
    //private Drone battery;
    //private Information info;
    private String creeks;
    private String sites;
    private boolean isGround;
    private final Logger logger = LogManager.getLogger();
    
    //creates a translator that takes the response from JSONConfiguration
    public Translator(JSONObject response, Drone battery) {
        this.cost = response.getInt("cost");
        this.extras = response.getJSONObject("extras");
        //this.battery = battery;
        this.creeks = "";
        this.sites = "";
        this.isGround = false;
    }

    //methods to update information class lmao this is so scuffed
    public boolean foundGround() {
        if (extras.has("found")) {
            if (extras.get("found").equals("GROUND")) {
                return true;
            } else return false;
        }
        return false;
    }

    public boolean foundOcean() {
        if (extras.has("found")) {
            if (extras.get("found").equals("OUT_OF_RANGE")) {
                return true;
            } else return false;
        }
        return false;
    }

    public int getRange() {
        if (extras.has("range")) {
            return extras.getInt("range");
        } 
        return 0;
    }

    public String getLand() {
        if (extras.has("range")) {
            JSONArray land = extras.getJSONArray("range");
            return land.getString(0);
        } else return null;

    }

    public void getCreek() {
        if (extras.has("creeks")) {
            JSONArray creekID = extras.getJSONArray("creeks");
            if (!creekID.isEmpty()) this.creeks = creekID.getString(0);
        } 

    }

    public void getSite() {
        if (extras.has("sites")) {
            JSONArray siteID = extras.getJSONArray("sites");
            if (!siteID.isEmpty()) this.sites = siteID.getString(0);
        }

    }

    //public int getBattery() {
      //  battery.setLevel(battery.getLevel() - cost);
        //return battery.getLevel();
    //}
    

}
