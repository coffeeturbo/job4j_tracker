package ru.job4j.tracker.strategy;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.memory.Tracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateActionStrategyTest {


    @Test
    public void whenExecuteSuccess() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));

        UserActionStrategy action = new CreateActionStrategy();

        Tracker tracker = new Tracker();
        Input input = mock(Input.class);

        String itemName = "New create item";

        when(input.askStr(any(String.class))).thenReturn(itemName);

        action.execute(input, tracker);

        assertThat(out.toString(), is(String.format("=== Create a new Item ====%n")));

        System.setOut(def);
    }

}