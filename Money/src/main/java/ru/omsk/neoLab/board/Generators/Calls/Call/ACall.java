package ru.omsk.neoLab.board.Generators.Calls.Call;

public abstract class ACall {

    protected String type;

    public String getType() {
        return type;
    }

    public int getMoney(){
        return 1;
    }

    //Можно ли забрать территорию
    abstract public boolean getAbilityCapture();

    //Сколько надо для захвата территории
    abstract public int getRequirementsForCapture();
}
