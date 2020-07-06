package ru.omsk.neoLab.board.Generators.Calls;

public class Call extends ACall {

    private String type;

    public Call(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
