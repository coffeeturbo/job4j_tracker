package ru.job4j.tracker.strategy;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.input.Input;

import java.util.List;

public class FindItemByNameActionStrategy implements UserActionStrategy {
    @Override
    public String name() {
        return "=== Find Item by name ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        String name = input.askStr("Enter name: ");

        List<Item> items = tracker.findByName(name);

        for (Item item: items) {
            System.out.println("positionId: " + item.getId()
                    + " PosName: " + item.getName());
        }
        return true;
    }
}
