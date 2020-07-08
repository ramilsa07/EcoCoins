package ru.omsk.neoLab.board.Generators.Calls;

import ru.omsk.neoLab.board.Generators.Calls.Call.ACall;

import java.util.Collection;
import java.util.Iterator;

public class ListCall{

    private ACall[] calls;

    public ListCall(ACall[] calls) {
        this.calls = calls;
    }

    public ACall getCall(int call) {
        return calls[call];
    }
}
