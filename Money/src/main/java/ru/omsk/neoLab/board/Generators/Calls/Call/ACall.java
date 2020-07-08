package ru.omsk.neoLab.board.Generators.Calls.Call;

import ru.omsk.neoLab.race.ARace;

public abstract class ACall {

    protected String type;
    protected String belongs;
    protected int countUnits;
    protected ARace race;

    public String getType() {
        return type;
    }

    public int getMoney(){
        return 1;
    }

    //Можно ли забрать территорию
    public boolean getAbilityCapture(ARace race){
        return true;
    }

    //Сколько надо для захвата территории
    public int getRequirementsForCapture() {
        return 2;
    }

    public String getBelongs() {
        return belongs;
    }

    public int getCountUnits() {
        return countUnits;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    public void setCountUnits(int countUnits) {
        this.countUnits = countUnits;
    }
}
