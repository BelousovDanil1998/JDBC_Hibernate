package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
   private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createSqlTable = "CREATE TABLE IF NOT EXISTS table_name (\n id int auto_increment, name VARCHAR(255) null, lastName VARCHAR(255) null, age int null, CONSTRAINT table_name_pk PRIMARY KEY (id));";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createSqlTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String dropSqlTable = "DROP TABLE IF EXISTS table_name";
        try (Statement statement = connection.createStatement()) {
            statement.execute(dropSqlTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String addUser = "INSERT INTO table_name  (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(addUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUser = "DELETE FROM table_name WHERE id = " + id;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(removeUser);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> outer = new ArrayList<>();
        String sql = "SELECT * FROM table_name";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");
                outer.add(new User(name, lastName, age));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return outer;
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE table_name";
        try (Statement statement = connection.createStatement()) {
            statement.execute(cleanTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
