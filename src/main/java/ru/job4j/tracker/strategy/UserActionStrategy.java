package ru.job4j.tracker.strategy;

import ru.job4j.tracker.Store;
import ru.job4j.tracker.input.Input;

public interface UserActionStrategy {
    String name();

    boolean execute(Input input, Store tracker);
}
