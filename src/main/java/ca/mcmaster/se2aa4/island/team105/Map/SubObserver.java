package ca.mcmaster.se2aa4.island.team105.Map;

import org.json.JSONArray;

public interface SubObserver {
    void update(String found, int range, JSONArray biomes);
}
