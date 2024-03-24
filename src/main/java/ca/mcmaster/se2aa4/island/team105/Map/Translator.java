package ca.mcmaster.se2aa4.island.team105.map;

import org.json.JSONObject;


public class Translator extends TranslateSubject{
    private int cost;
    private JSONObject extras;
    private Information info = new Information();
    
    //creates a translator that takes the response from JSONConfiguration
    public Translator() {
        this.cost = 0;
        this.extras = null;
    }

    //to be called in main
    //adds any class that needs the info from response as an observer
    @Override
    public void addObserver(SubObserver subObserver) { 
        info.addObserver(subObserver);
    }

    // notifies the information observer
    @Override
    public void notifyObservers() {
        info.update(this.extras, this.cost);
    }

    // sets info in this class from JSONConfiguration
    public void setInfo(JSONObject response) {
        this.extras = response.getJSONObject("extras");
        this.cost = response.getInt("cost");
        notifyObservers();
    } 

}