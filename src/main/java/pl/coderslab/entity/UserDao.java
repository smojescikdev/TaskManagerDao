package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = " INSERT INTO workshop2.users (email, username,password) VALUES (?, ?, ?);";
    private static final String MODIFY_DATA_QUERY = " UPDATE users\n" +
            "SET email = '?',\n" +
            "    username = '?',\n" +
            "    password = '?o'\n" +
            "WHERE id = ?; ";
    private static final String SELECT_ID_QUERY = " SELECT id, email, username, password\n" +
            "FROM users\n" +
            "WHERE id = ?; ";
    private static final String REMOVE_ID_QUERY = " DELETE FROM users\\n\" +\n" +
            "            \"WHERE id = ?; ";
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
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
