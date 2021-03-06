package ru.job4j.tracker.store.sql;

import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.Store;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection con;

    public SqlTracker() {
        this.init();
    }

    public SqlTracker(Connection con) {
        this.con = con;
    }

    @Override
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            con = DriverManager.getConnection(
                config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement = con.prepareStatement("INSERT INTO item(name) VALUES(?)",
            Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.executeUpdate();

            ResultSet rsl = statement.getGeneratedKeys();
            rsl.next();
            item.setId(rsl.getInt(1));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        try (PreparedStatement statement = con.prepareStatement("UPDATE item SET name = ? WHERE id = ?")) {
            statement.setString(1, item.getName());
            statement.setInt(2, id);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement statement = con.prepareStatement("DELETE FROM item WHERE id = ?")) {
            statement.setInt(1, id);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT id, name FROM item")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                list.add(new Item(result.getInt(1), result.getString(2)));
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT id, name FROM item WHERE name = ?")) {
            statement.setString(1, key);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                list.add(new Item(result.getInt(1), result.getString(2)));
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    @Override
    public Item findById(Integer id) {
        Item item = null;
        try (PreparedStatement statement = con.prepareStatement("SELECT id, name FROM item WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                item = new Item(result.getInt(1), result.getString(2));
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return item;
    }

}
