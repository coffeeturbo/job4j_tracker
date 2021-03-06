package ru.job4j.tracker.store.sql;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.StubInput;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.memory.StartUI;
import ru.job4j.tracker.store.memory.Tracker;
import ru.job4j.tracker.strategy.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringJoiner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StartSqlUITest {
    @Test
    public void init() {

        StartUI ui = new StartUI();
        String[] answers = {"0"};
        StubInput input = new StubInput(answers);
        StubAction action = new StubAction();
        ArrayList<UserActionStrategy> strategies = new ArrayList<>();
        strategies.add(action);
        ui.init(input, new Tracker(), strategies);

        assertThat(action.isCall(), is(true));
    }

    @Test
    public void whenPrtMenu() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream def = System.out;
        System.setOut(new PrintStream(out));
        StubInput input = new StubInput(
                new String[] {"0"}
        );
        StubAction action = new StubAction();
        ArrayList<UserActionStrategy> strategies = new ArrayList<>();
        strategies.add(action);
        new StartUI().init(input, new Tracker(), strategies);
        String expect = new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                .add("Menu.")
                .add("0 Stub name")
                .toString();
        assertThat(new String(out.toByteArray()), is(expect));
        System.setOut(def);
    }

    @Test
    public void whenAddItem() {
        String[] answers = {"Fix Pc"};
        Input input = new StubInput(answers);
        Tracker tracker = new Tracker();

        UserActionStrategy create = new CreateActionStrategy();
        create.execute(input, tracker);

        Item created = tracker.findAll().get(0);
        Item expected = new Item("Fix Pc");
        assertThat(created.getName(), is(expected.getName()));
    }

    @Test
    public void whenReplaceItem() {

        Tracker tracker = new Tracker();
        Item item = new Item(" Item");
        tracker.add(item);

        String[] answers = {item.getId().toString(), "Replace Item"};
        Input input = new StubInput(answers);

        UserActionStrategy replace = new ReplaceItemActionStrategy();
        replace.execute(input, tracker);

        Item replaced = tracker.findById(item.getId());
        assertThat(replaced.getName(), is("Replace Item"));
    }

    @Test
    public void whenDeleteItem() {

        Tracker tracker = new Tracker();
        Item item = new Item(" Item");
        tracker.add(item);

        String[] answers = {item.getId().toString(), "Replace Item"};
        Input input = new StubInput(answers);

        UserActionStrategy delete = new DeleteItemActionStrategy();
        delete.execute(input, tracker);

        Item replaced = tracker.findById(item.getId());
        Assert.assertNull(replaced);
    }
}