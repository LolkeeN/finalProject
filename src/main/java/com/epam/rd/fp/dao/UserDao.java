package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);
    private static final String SELECT_ALL_FROM_USER_TABLE = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String INSERT_VALUES_INTO_USER_TABLE = "INSERT into users (first_name,last_name, email, role, password) values (?, ?, ?,?, ?)";
    private static final String SELECT_USER_ID_BY_EMAIL = "SELECT  id from users where email = ?";

    /**
     * A method to insert user into "user" table
     *
     * @param conn your database connection
     * @param user a user to be inserted
     * @throws IllegalArgumentException when insertion fails
     */
    public void insertUser(Connection conn, User user) {
        ResultSet rs;
        try (PreparedStatement prepStat = conn.prepareStatement(INSERT_VALUES_INTO_USER_TABLE)) {

            prepStat.setString(1, user.getFirstName());
            prepStat.setString(2, user.getLastName());
            prepStat.setString(3, user.getEmail());
            prepStat.setInt(4, user.getRole().getValue());
            prepStat.setString(5, user.getPassword());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement(SELECT_USER_ID_BY_EMAIL)) {
                prSt.setString(1, user.getEmail());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    user.setId(rs.getInt("id"));
                }
            }

        } catch (SQLException e) {
            log.error("Cannot insert user into user table", e);
            throw new IllegalArgumentException("Cannot insert user", e);
        }
    }

    /**
     * A method to get user from "user" table by it's email and password
     *
     * @param conn     your database connection
     * @param email    user's email
     * @param password user's password
     * @return user with email and password you've entered
     * @throws IllegalArgumentException when cannot get user by email and password
     */
    public User getUser(Connection conn, String email, String password) {
        ResultSet rs;
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try (PreparedStatement prepStat = conn.prepareStatement(SELECT_ALL_FROM_USER_TABLE);
        ) {
            prepStat.setString(1, email);
            prepStat.setString(2, password);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                fillUserFieldsFromDatabase(user, rs);
            }
        } catch (SQLException e) {
            log.error("Cannot get user from user table", e);
            throw new IllegalArgumentException("Cannot get user", e);
        }
        return user;
    }

    /**
     * A method to get user from "user" table by it's id
     *
     * @param conn your database connection
     * @param id   user's id
     * @return user with id you've entered
     * @throws IllegalArgumentException when cannot get user by id
     */
    public User getUser(Connection conn, int id) {
        User user = new User();
        ResultSet rs;
        try (PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
        ) {
            prepStat.setInt(1, id);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                fillUserFieldsFromDatabase(user, rs);
            }
        } catch (SQLException e) {
            log.error("Cannot get user by id from user table", e);
            throw new IllegalArgumentException("Cannot get user");
        }
        return user;
    }

    /**
     * Private method to fill user's fields
     *
     * @param user a user
     * @param rs   result set
     * @throws SQLException when filling fields fails
     */
    private void fillUserFieldsFromDatabase(User user, ResultSet rs) throws SQLException {
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        if (rs.getInt("role") == 1) {
            user.setRole(Role.USER);
        } else if (rs.getInt("role") == 2) {
            user.setRole(Role.MODERATOR);
        } else {
            user.setRole(Role.SPEAKER);
        }
    }

    public boolean isAlreadyRegistered(Connection conn, String email){
        int userCount = 0;
        ResultSet rs;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM users where email = ?");
        ) {
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userCount = rs.getInt("userCount");
            }
            return userCount == 1;
        } catch (SQLException e) {
            log.error("Cannot check user's registration", e);
            throw new IllegalArgumentException("Cannot check user's registration");
        }
    }
}
