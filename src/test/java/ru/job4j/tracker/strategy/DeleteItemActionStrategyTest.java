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

public class DeleteItemActionStrategyTest {

    @Test
    public void whenExecuteSuccess() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));

        UserActionStrategy action = new DeleteItemActionStrategy();

        Tracker tracker = new Tracker();
        Item newItem = new Item(1, "deleted item");
        tracker.add(newItem);
        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(newItem.getId());

        action.execute(input, tracker);

        assertThat(out.toString(), is(String.format(
                "=== Delete Item by id ====%npositionId: %s Успешно удалена!%n",
                newItem.getId()
        )));

        System.setOut(def);
    }
}