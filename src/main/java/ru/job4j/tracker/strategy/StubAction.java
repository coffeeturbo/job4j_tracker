package ru.job4j.tracker.strategy;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.strategy.UserActionStrategy;

public class StubAction implements UserActionStrategy {
    private boolean call = false;

    @Override
    public String name() {
        return "Stub name";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}
