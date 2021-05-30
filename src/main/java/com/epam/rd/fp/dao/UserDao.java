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
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final String SELECT_ALL_FROM_USER_TABLE = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String INSERT_VALUES_INTO_USER_TABLE = "INSERT into users (first_name,last_name, email, role, password) values (?, ?, ?,?, ?)";
    private static final String SELECT_USER_ID_BY_EMAIL = "SELECT  id from users where email = ?";

    public void insertUser(User user) {
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement(INSERT_VALUES_INTO_USER_TABLE)) {

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
        }
    }

    public User getUser(String email, String password) {
        ResultSet rs;
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement prepStat = conn.prepareStatement(SELECT_ALL_FROM_USER_TABLE);
            prepStat.setString(1, email);
            prepStat.setString(2, password);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                fillUserFieldsFromDatabase(user, rs);
            }
        } catch (SQLException e) {
            log.error("Cannot get user from user table", e);
        }
        return user;
    }

    public User getUser(int id) {
        User user = new User();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                log.error("No suitable driver found", e);
            }
            ResultSet rs;

            try {
                Connection conn = getConnection(CONNECTION_URL);
                PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
                prepStat.setInt(1, id);
                rs = prepStat.executeQuery();
                while (rs.next()) {
                    fillUserFieldsFromDatabase(user, rs);
                }
            }catch (SQLException e){
                log.error("Cannot get user by id from user table", e);
            }
            return user;
    }

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
}
