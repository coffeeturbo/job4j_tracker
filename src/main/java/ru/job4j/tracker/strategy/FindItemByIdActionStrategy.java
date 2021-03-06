package ru.job4j.tracker.strategy;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.input.Input;

public class FindItemByIdActionStrategy implements UserActionStrategy {
    @Override
    public String name() {
        return "=== Find Item by id ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");

        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println("positionId: " + item.getId()
                    + " PosName: " + item.getName());
        }
        return true;
    }
}
