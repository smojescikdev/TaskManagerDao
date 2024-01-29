package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.Scanner;

public class MainDao {
    public static void main(String[] args) {
updateUser();

    }

    public static void createUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To add new user you must to enter a data: ");

        System.out.print("EMAIL:  ");
        String email = scanner.nextLine();


        System.out.print("USERNAME:  ");
        String username = scanner.nextLine();

        System.out.println("PASSWORD:  ");
        String password = scanner.nextLine();

        // Tworzymy obiekt User i ustawiamy pola
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUserName(username);
        newUser.setPassword(password);

        // Tworzymy obiekt UserDao i wywołujemy metodę create, przekazując obiekt User
        UserDao userDao = new UserDao();
        userDao.create(newUser);
    }

    public static void readUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To select a user, please enter an id number ");

        System.out.print("ID:  ");
        int id = scanner.nextInt();

        // Tworzymy obiekt UserDao
        UserDao userDao = new UserDao();

        // Wywołujemy metodę read z UserDao i przekazujemy id
        User user = userDao.read(id);

        // Sprawdzamy, czy użytkownik został znaleziony
        if (user != null) {
            System.out.println("Selected user details:");
            System.out.println("ID: " + user.getId(id));
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Password: " + user.getPassword());
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }


// DO PORAWY!!!
    public static void updateUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To update a user, please enter an id number ");

        System.out.print("ID:  ");
        int id = scanner.nextInt();

        // Tworzymy obiekt UserDao
        UserDao userDao = new UserDao();

        // Wywołujemy metodę read z UserDao i przekazujemy id
        User user = userDao.read(id);

        // Sprawdzamy, czy użytkownik został znaleziony
        if (user != null) {
            System.out.println("Selected user details:");
            System.out.println("ID: " + user.getId(id));
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Password: " + user.getPassword());
        } else {
            System.out.println("User with ID " + id + " not found.");
        }


        System.out.println("to modify user enter data: ");


        System.out.print("EMAIL:  ");
        String email = scanner.nextLine();

        System.out.print("USERNAME:  ");
        String username = scanner.nextLine();


        System.out.print("PASSWORD:  ");
        String password = scanner.nextLine();

        user.setEmail(email);
        user.setUserName(username);
       user.setPassword(password);

        // Tworzymy obiekt UserDao i wywołujemy metodę create, przekazując obiekt User

        userDao.update(user);

    }

}