package ru.job4j.tracker.strategy;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Store;
import ru.job4j.tracker.input.Input;

public class FindItemByIdActionStrategy implements UserActionStrategy {
    @Override
    public String name() {
        return "=== Find Item by id ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String id = input.askStr("Enter id: ");

        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println("positionId: " + item.getId()
                    + " PosName: " + item.getName());
        }
        return true;
    }
}
