package ru.omsk.neoLab.race;

import java.util.HashSet;

public class RaceContainer{

    private HashSet<ARace> setRace = new HashSet<ARace>();

    public void addRace(ARace race){
        setRace.add(race);
    }

    public int getContainerSize(){
        return setRace.size();
    }

    public void removeElement(ARace race){
        setRace.remove(race);
    }

    public HashSet<ARace> getSetRace() {
        return setRace;
    }

    public boolean contains(String race){
       // setRace.contains(race);
        return true;
    }
}
