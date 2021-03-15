package ru.job4j.tracker.strategy;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.memory.Tracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.*;

public class ReplaceItemActionStrategyTest {

    @Test
    public void execute() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));

        String replacedName = "Replaced name";
        UserActionStrategy action = new ReplaceItemActionStrategy();

        Tracker tracker = new Tracker();
        Item newItem = new Item(1, "Replaced item");
        tracker.add(newItem);
        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(newItem.getId());
        when(input.askStr(any(String.class))).thenReturn(replacedName);

        action.execute(input, tracker);

        assertThat(
                out.toString(),
                is(String.format("positionId: %s Успешно отредактирована!%n", newItem.getId()))
        );

        System.setOut(def);
    }

}