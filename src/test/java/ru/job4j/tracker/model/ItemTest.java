package ru.job4j.tracker.model;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTest {
    @Test
    public void compareToSort() {
        List<Item> items = Arrays.asList(
                new Item("B"),
                new Item("F"),
                new Item("D")
        );

        List<Item> expectItems = Arrays.asList(
                new Item("B"),
                new Item("D"),
                new Item("F")
        );

        Collections.sort(items);
        assertThat(items, is(expectItems));
    }

    @Test
    public void compareToSortDesc() {
        List<Item> items = Arrays.asList(
                new Item("B"),
                new Item("F"),
                new Item("D")
        );

        List<Item> expectItems = Arrays.asList(
                new Item("F"),
                new Item("D"),
                new Item("B")
        );

        items.sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
        assertThat(items, is(expectItems));
    }

    @Test
    public void compareToSortAsc() {
        List<Item> items = Arrays.asList(
                new Item("B"),
                new Item("F"),
                new Item("D")
        );

        List<Item> expectItems = Arrays.asList(
                new Item("B"),
                new Item("D"),
                new Item("F")
        );

        Collections.sort(items);
        assertThat(items, is(expectItems));
    }
}