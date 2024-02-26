package ca.mcmaster.se2aa4.island.team105.Map;

import java.util.List;

import org.json.JSONObject;

public class TileInfo extends MapTile{
    private List<String> creekIds;
    private List<String> siteIds;
    private List<String> biomes;

    public TileInfo(List<String> creekIds, List<String> siteIds, List<String> biomes) {
        this.creekIds = creekIds;
        this.siteIds = siteIds;
        this.biomes = biomes;
    }

    public List<String> getCreekIds() {
        return creekIds;
    }

    public List<String> getSiteIds() {
        return siteIds;
    }

    public List<String> getBiomes() {
        return biomes;
    }

    public void setCreekIds(JSONObject creekExtras) { //use this in acknowledgeresults perhaps hmmmmm....
        this.creekIds = creekIdentifier(creekExtras);
    }

    public void setSiteIds(JSONObject siteExtras) {
        this.siteIds = siteIdentifier(siteExtras);
    }

    public void setBiomes(JSONObject biomeExtras) {
        this.biomes = biomeType(biomeExtras);
    }

}
