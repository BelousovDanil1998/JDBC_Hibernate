package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Oleg", "Ivanov", (byte) 20);
        userService.saveUser("Ivan", "Olegov", (byte) 21);
        userService.saveUser("Petr", "Petrov", (byte) 22);
        userService.saveUser("Vasya", "Korolov", (byte) 23);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();


    }
}
