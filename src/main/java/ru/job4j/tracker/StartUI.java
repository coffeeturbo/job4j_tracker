package ru.job4j.tracker;

import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.strategy.*;

import java.util.Arrays;
import java.util.List;

public class StartUI {

    public void init(Input input, Store tracker, List<UserActionStrategy> actions) {
        boolean run = true;

        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            UserActionStrategy action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }


    private void showMenu(List<UserActionStrategy> actions) {
        System.out.println("Menu.");
        for (UserActionStrategy action: actions) {
            System.out.println(actions.indexOf(action) + " " + action.name());
        }
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Input scanner = new ValidateInput(input);
        Store tracker = new Tracker();

        List<UserActionStrategy> actionsList =  Arrays.asList(
            new CreateActionStrategy(),
            new ShowAllItemsActionStrategy(),
            new DeleteItemActionStrategy(),
            new ReplaceItemActionStrategy(),
            new FindItemByIdActionStrategy(),
            new FindItemByNameActionStrategy()
        );

        new StartUI().init(scanner, tracker, actionsList);
    }
}
