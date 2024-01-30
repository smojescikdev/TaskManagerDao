package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;
import pl.coderslab.entity.User;
public class UserDao {

    private static final String CREATE_USER_QUERY = " INSERT INTO workshop2.users (email, username,password) VALUES (?, ?, ?);";
    private static final String MODIFY_DATA_QUERY = "UPDATE users " +
            "SET email = ?, " +
            "username = ?, " +
            "password = ? " +
            "WHERE id = ?;";
    private static final String SELECT_ID_QUERY = " SELECT id, email, username, password\n" +
            "FROM users\n" +
            "WHERE id = ?; ";
    private static final String REMOVE_ID_QUERY = " DELETE FROM users WHERE id = ?; ";
    private static final String LIST_ALL_USERS_QUERY = " SELECT * FROM users; ";


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUserName());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(SELECT_ID_QUERY)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(resultSet.getInt("id"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserName(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(MODIFY_DATA_QUERY)) {
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getUserName());
                statement.setString(3, hashPassword(user.getPassword()));
                statement.setInt(4, user.getId());  // Dodajemy ustawienie parametru ID
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(REMOVE_ID_QUERY)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(LIST_ALL_USERS_QUERY)) {

            User[] users = new User[0]; // Początkowo tablica jest pusta

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(id, email, username, password);
                users = addToArray(user, users); // Dodajemy użytkownika do tablicy
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return new User[0]; // Zwracamy pustą tablicę w przypadku błędu
        }
    }
}

