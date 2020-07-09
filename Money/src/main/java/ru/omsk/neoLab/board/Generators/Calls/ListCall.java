package ru.omsk.neoLab.board.Generators.Calls;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

import java.util.ArrayList;

public class ListCall {
    private ArrayList<ACall> calls;


    public ListCall() {
        calls = new ArrayList<>();
    }

    public void addCall(ACall call){
        calls.add(call);
    }

    public void removeCall(ACall call){
        calls.remove(call);
    }

    public ArrayList<ACall> getCalls() {
        return calls;
    }
}
