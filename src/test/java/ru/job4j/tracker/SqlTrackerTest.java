package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.sql.ConnectionRollback;
import ru.job4j.tracker.sql.SqlTracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {
    private Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenAddItem() throws Exception {

        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("1", "desc"));
            assertThat(tracker.findByName("desc").size(), is(1));
        }

    }

    @Test
    public void whenReplaceItem() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.replace("1", new Item("1", "replaced"));
            assertThat(tracker.findByName("replaced").size(), is(1));
        }
    }

    @Test
    public void whenFindById() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            assertThat(tracker.findById("1").getId(), is("1"));
        }
    }

    @Test
    public void whenFindByIdFalse() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            assertNull(tracker.findById("1223213"));
        }
    }

    @Test
    public void whenFindByName() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            assertThat(tracker.findByName("item3").size(), is(1));
        }
    }

    @Test
    public void whenFindByNameFalse() throws Exception {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            assertThat(tracker.findByName("notExistsItem").size(), is(0));
        }
    }

}