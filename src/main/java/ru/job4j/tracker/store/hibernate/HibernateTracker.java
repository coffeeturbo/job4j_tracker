package ru.job4j.tracker.store.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

public class HibernateTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();


    @Override
    public void init() {

    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();

        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        item.setId(id);
        session.update(item);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item("");
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from ru.job4j.tracker.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String name) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ru.job4j.tracker.model.Item where name=:name");
        query.setParameter("name", name);
        List<Item> result = query.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        if (sf.isOpen()) {
            sf.close();
        }
    }
}
