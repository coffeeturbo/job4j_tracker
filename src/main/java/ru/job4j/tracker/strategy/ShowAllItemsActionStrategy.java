package ru.job4j.tracker.strategy;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Store;
import ru.job4j.tracker.input.Input;

import java.util.List;

public class ShowAllItemsActionStrategy implements UserActionStrategy {
    @Override
    public String name() {
        return "=== Show all items ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        int size = tracker.findAll().size();
        List<Item> items = tracker.findAll();
        for (Item item: items) {
            System.out.printf("id: %s, name: %s%n", item.getId(), item.getName());
        }
        return true;
    }
}
