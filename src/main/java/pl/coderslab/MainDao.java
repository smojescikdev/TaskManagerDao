package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.util.List;
import java.util.Scanner;

public class MainDao {
    public static void main(String[] args) {
menuOption();

    }

    public static void menuOption() {
        Scanner scanner = new Scanner(System.in);

        int inputMenu = 0;

        while (inputMenu != 6) {
            System.out.println("---- MENU ----");
            System.out.println("1: ADD USER");
            System.out.println("2: MODIFY USER");
            System.out.println("3: READ USER");
            System.out.println("4: DELETE USER");
            System.out.println("5: LIST USERS");
            System.out.println("6: --EXIT--");

            inputMenu = scanner.nextInt();

            switch (inputMenu) {
                case 1:
                    System.out.println("-- ADD USER --");
                    createUser();
                    break;

                case 2:
                    System.out.println("-- MODIFY USER --");
                   updateUser();
                    break;

                case 3:
                    System.out.println("-- READ USER --");
                    readUser();
                    break;

                case 4:
                    System.out.println("-- DELETE USER --");
                    deleteUser();
                    break;

                case 5:
                    System.out.println("-- LIST USERS --");
                    findAll();
                    break;

                case 6:
                    System.out.println("-- EXIT --");
                    break;

                default:
                    System.out.println("You entered an invalid option!");
            }
        }

        scanner.close();
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

        System.out.println("USER " + username + "CREATED");
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
            System.out.println("ID: " + user.getId());
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
        scanner.nextLine(); // Dodajemy dodatkowe nextLine(), aby przechwycić znak nowej linii

        // Tworzymy obiekt UserDao
        UserDao userDao = new UserDao();

        // Wywołujemy metodę read z UserDao i przekazujemy id
        User user = userDao.read(id);

        // Sprawdzamy, czy użytkownik został znaleziony
        if (user != null) {
            System.out.println("Selected user details:");
            System.out.println("ID: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Password: " + user.getPassword());

            System.out.println("To modify user, enter new data: ");
            System.out.print("EMAIL:  ");
            String email = scanner.nextLine();

            System.out.print("USERNAME:  ");
            String username = scanner.nextLine();

            System.out.print("PASSWORD:  ");
            String password = scanner.nextLine();

            user.setEmail(email);
            user.setUserName(username);
            user.setPassword(password);

            // Wywołujemy metodę update, aby zaktualizować dane użytkownika
            userDao.update(user);
            System.out.println("User updated successfully.");
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }


    public static void deleteUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To delete a user, please enter an id number ");

        System.out.print("ID:  ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Dodajemy dodatkowe nextLine(), aby przechwycić znak nowej linii

        // Tworzymy obiekt UserDao
        UserDao userDao = new UserDao();

        // Wywołujemy metodę read z UserDao i przekazujemy id
        User user = userDao.read(id);

        // Sprawdzamy, czy użytkownik został znaleziony
        if (user != null) {
            System.out.println("Selected user details:");
            System.out.println("ID: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Password: " + user.getPassword());

            System.out.println("To delete user, please enter Y or N ");
            String input = scanner.next();
            char delete = input.charAt(0);

            while (delete == 'y' || delete == 'Y') {
                // Wywołujemy metodę delete na UserDao
                userDao.delete(id);

                System.out.print("Do you want to delete another user? (Y/N): ");
                delete = scanner.next().charAt(0);

                // Odczytujemy dodatkowy znak nowej linii
                scanner.nextLine();
            }

            System.out.println("Exiting the program.");
        }
    }


    public static void findAll() {
        UserDao userDao = new UserDao();

        // Wywołujemy metodę findAll z UserDao
        User[] users = userDao.findAll();

        // Wyświetlamy dane każdego użytkownika z tablicy
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Username: " + user.getUserName());
            System.out.println("Password: " + user.getPassword());
            System.out.println("--------------");
        }
    }
}