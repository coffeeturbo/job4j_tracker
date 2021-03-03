package ru.job4j.tracker.strategy;

import ru.job4j.tracker.Item;
import ru.job4j.tracker.Store;
import ru.job4j.tracker.input.Input;

public class ReplaceItemActionStrategy implements UserActionStrategy {
    @Override
    public String name() {
        return "=== Edit Item by id ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        Integer id = input.askInt("Enter id: ");
        String name = input.askStr("Введите имя item");

        Item item = new Item(name);

        if (tracker.replace(id, item)) {
            System.out.println("positionId: " + id + " Успешно отредактирована!");
        }
        return true;
    }
}
