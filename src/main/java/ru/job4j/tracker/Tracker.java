package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Tracker.
 */
public class Tracker implements Store {
    private final ArrayList<Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    /**
     * Add item.
     *
     * @param item the item
     * @return the item
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        return item;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Item> findAll() {
        return items;
    }

    /**
     * Find by name list.
     *
     * @param key the key
     * @return the list
     */
    public List<Item> findByName(String key) {
        List<Item> itemsByNames = new ArrayList<>();

        for (Item item: items) {
            if (item.getName().equals(key)) {
                itemsByNames.add(item);
            }
        }
        return itemsByNames;
    }

    /**
     * Find by id item.
     *
     * @param id the id
     * @return the item
     */
    public Item findById(Integer id) {
        Item resultItem = null;
        for (Item item: items) {
            if (item.getId().equals(id)) {
                resultItem = item;
            }
        }
        return resultItem;
    }

    /**
     * Replace boolean.
     *
     * @param id   the id
     * @param item the item
     * @return the boolean
     */
    public boolean replace(Integer id, Item item) {
        boolean result = false;

        if (id >= 0) {
            item.setId(id);
            items.set(id, item);
            result = true;
        }

        return result;
    }


    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public boolean delete(Integer id) {
        boolean result = false;
        Item item = findById(id);
        if (item != null) {
            items.remove(item);
            result = true;
        }
        return result;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private Integer generateId() {
        Random rm = new Random();
        return Integer.parseInt(String.valueOf(rm.nextLong() + System.currentTimeMillis()));
    }

    @Override
    public void close() throws Exception {

    }
}
