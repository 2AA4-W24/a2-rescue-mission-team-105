package ca.mcmaster.se2aa4.island.team105.Map;


public interface TranslateSubject {
    void addObserver(SubObserver subObserver);
    void notifyObservers();
}
