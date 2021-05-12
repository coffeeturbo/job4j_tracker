package ru.job4j.tracker.store.hibernate;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HibernateTrackerTest {

    @Test
    public void add() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("New item").descripion("New add item").build();
        store.add(item);
        assertThat(item.getId(), is(1));
        assertThat(item.getName(), is("New item"));
        assertThat(item.getDescripion(), is("New add item"));
    }

    @Test
    public void replace() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("New item").descripion("New add item").build();
        store.add(item);

        assertThat(item.getId(), is(1));
        assertThat(item.getName(), is("New item"));
        assertThat(item.getDescripion(), is("New add item"));

        item.setName("searched");
        item.setDescripion("searched descriprion");

        store.replace(item.getId(), item);
        Item searched = store.findById(item.getId());

        assertThat(searched.getId(), is(1));
        assertThat(searched.getName(), is("searched"));
        assertThat(searched.getDescripion(), is("searched descriprion"));
    }

    @Test
    public void delete() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("New item").build();
        Item item2 = Item.builder().name("Search Item").build();
        store.add(item);
        store.add(item2);

        store.delete(item.getId());

        List<Item> items = store.findAll();
        assertThat(items.size(), is(1));
    }

    @Test
    public void findAll() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("New item").build();
        Item item2 = Item.builder().name("Search Item").build();
        store.add(item);
        store.add(item2);

        List<Item> items = store.findAll();
        assertThat(items.size(), is(2));
    }

    @Test
    public void findByName() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("search name").descripion("findByName").build();
        store.add(item);
        List<Item> search = store.findByName("search name");

        assertThat(search.size(), is(1));
        assertThat(search.get(0).getName(), is("search name"));
        assertThat(search.get(0).getDescripion(), is("findByName"));
    }

    @Test
    public void findById() {
        Store store = new HibernateTracker();
        Item item = Item.builder().name("New item").build();
        Item item2 = Item.builder().name("Search Item").descripion("description").build();
        store.add(item);
        store.add(item2);
        Item search = store.findById(2);

        System.out.println(search);

        assertThat(search.getId(), is(2));
        assertThat(search.getName(), is("Search Item"));
        assertThat(search.getDescripion(), is("description"));
    }
}