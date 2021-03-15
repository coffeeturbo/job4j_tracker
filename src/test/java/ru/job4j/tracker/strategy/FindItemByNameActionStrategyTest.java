package ru.job4j.tracker.strategy;

import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.memory.Tracker;
import ru.job4j.tracker.input.StubInput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindItemByNameActionStrategyTest {

    @Test
    public void whenCheckOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));
        Tracker tracker = new Tracker();
        Item item = new Item("item 1");
        Item item2 = new Item("item 2");
        tracker.add(item);
        tracker.add(item2);
        FindItemByNameActionStrategy act = new FindItemByNameActionStrategy();
        act.execute(new StubInput(new String[]{"item 2"}), tracker);

        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("=== Find Item by name ====")
                .add("positionId: " + item2.getId() + " PosName: " + item2.getName())
                .toString();

        String actual = new String(out.toByteArray());

        assertThat(actual, is(expect));
        System.setOut(def);
    }

    @Test
    public void whenExecuteSuccess() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));

        UserActionStrategy action = new FindItemByNameActionStrategy();

        Tracker tracker = new Tracker();
        Item newItem = new Item(1, "Name item");
        tracker.add(newItem);
        Input input = mock(Input.class);

        when(input.askStr(any(String.class))).thenReturn(newItem.getName());

        action.execute(input, tracker);

        assertThat(out.toString(), is(String.format(
                "=== Find Item by name ====%npositionId: %s PosName: %s%n",
                newItem.getId(),
                newItem.getName()
        )));

        System.setOut(def);
    }

}