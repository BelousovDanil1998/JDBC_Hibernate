package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
    String createSqlTable = "create table if not exists table_name" +
            "(\n" +
            "    id       int auto_increment," +
            "    name     VARCHAR(255) null," +
            "    lastName varchar(255) null," +
            "    age      int          null," +
            "    constraint table_name_pk" +
            "        primary key (id)" +
            ");";

            try (Statement statement = Util.getConnection().createStatement()) {
       statement.execute(createSqlTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        String dropSqlTable = "drop table if exists table_name";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(dropSqlTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
    String addUser = "insert into <table_name> values (<" + name +"> ,<" + lastName + ">,<" + age + ">)";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(addUser);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
    String removeUser = "delete from table_name where id = " + id;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(removeUser);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> outer = new ArrayList<>();
        String sql = "select from table_name";

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                List<User> inner = new ArrayList<>();
                inner.add(resultSet.getString("name"));
                outer.add(inner);
            }

            for (int i = 0; i < outer.size(); i++) {
                System.out.println(outer.get(i));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void cleanUsersTable() {

    }
}
