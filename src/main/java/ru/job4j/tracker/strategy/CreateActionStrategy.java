package ru.job4j.tracker.strategy;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.input.Input;

public class CreateActionStrategy implements UserActionStrategy {

    @Override
    public String name() {
        return "=== Create a new Item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        return true;
    }
}
