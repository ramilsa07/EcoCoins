package ru.omsk.neoLab.board.Generators.Calls.Call;

import ru.omsk.neoLab.race.ARace;

public abstract class ACall {

    protected String type;

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
}
