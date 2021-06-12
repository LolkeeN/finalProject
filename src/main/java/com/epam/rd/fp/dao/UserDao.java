package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.DBManager;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.fp.util.Constants.CONNECTION_URL;
import static java.sql.DriverManager.getConnection;

public class UserDao {
    private final DBManager dbManager;
    private static final Logger log = LogManager.getLogger(UserDao.class);
    private static final String SELECT_ALL_FROM_USER_TABLE = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String INSERT_VALUES_INTO_USER_TABLE = "INSERT into users (first_name,last_name, email, role, password) values (?, ?, ?,?, ?)";
    private static final String SELECT_USER_ID_BY_EMAIL = "SELECT  id from users where email = ?";

    public UserDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * A method to insert user into "user" table
     *
     * @param conn your database connection
     * @param user a user to be inserted
     * @throws IllegalArgumentException when insertion fails
     */
    public void insertUser(User user) {
        ResultSet rs;
        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
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
    public User getUser(String email, String password) {
        ResultSet rs;
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
                PreparedStatement prepStat = conn.prepareStatement(SELECT_ALL_FROM_USER_TABLE);
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
    public User getUser(int id) {
        User user = new User();
        ResultSet rs;
        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
                PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
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

    public boolean isAlreadyRegistered(String email) {
        int userCount = 0;
        ResultSet rs;
        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM users where email = ?");
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


    /**
     * A method to get speaker id by id of topic connected with him
     *
     * @param conn    your database connection
     * @param topicId id of the topic whose speaker you want to get
     * @return speaker's id
     * @throws IllegalArgumentException when cannot get speaker id by topic id
     */
    public int getSpeakerIdByTopicId(int topicId) {
        int speakerId = 0;
        ResultSet rs;

        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM topic_speaker WHERE topic_id = ?");
        ) {
            prepStat.setInt(1, topicId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                speakerId = rs.getInt("speaker_id");
            }
        } catch (SQLException e) {
            log.error("Cannot get speaker's id", e);
            throw new IllegalArgumentException("Cannot get speaker's id");
        }
        return speakerId;
    }

    /**
     * A method to register user for a meeting
     *
     * @param conn      your database connection
     * @param userId    id of user to be registered
     * @param meetingId the id of the meeting the user is registering for
     * @throws IllegalArgumentException when user registration for a meeting fails
     */
    public void registerUserForAMeeting(int userId, int meetingId) {
        int rowcount = 0;
        ResultSet rs;
        try (Connection conn = dbManager.getConnection(CONNECTION_URL);
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM registered_users where meeting_id = ? AND user_id = ?");
        ) {
            preparedStatement.setInt(1, meetingId);
            preparedStatement.setInt(2, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rowcount = rs.getInt("rowcount");
            }
            if (rowcount != 0) {
                log.info("User's already registered");
                throw new IllegalArgumentException("User's already registered");
            }
            try (PreparedStatement prepStat = conn.prepareStatement("INSERT INTO registered_users (meeting_id, user_id) values (?, ?)");
            ) {
                prepStat.setInt(1, meetingId);
                prepStat.setInt(2, userId);
                prepStat.execute();
            }
        } catch (SQLException e) {
            log.error("Cannot register user for a meeting", e);
            throw new IllegalArgumentException("Cannot register user for a meeting");
        }
    }

    /**
     * A method to check if user is registered for a meeting
     *
     * @param conn      your database connection
     * @param userId    id of user to check registration
     * @param meetingId id of meeting to check if user is registered
     * @return true if user registered, false if user is not registered
     * @throws IllegalArgumentException when cannot check topic for availability
     */
    public boolean isRegistered(int userId, int meetingId) {
        int rowcount = 0;
        ResultSet rs;
        try {
            try (Connection conn = dbManager.getConnection(CONNECTION_URL);
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM registered_users where meeting_id = ? AND user_id = ?")) {
                preparedStatement.setInt(1, meetingId);
                preparedStatement.setInt(2, userId);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    rowcount = rs.getInt("rowcount");
                }
                if (rowcount != 0) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            log.error("Cannot check user registration for a meeting", e);
            throw new IllegalArgumentException("Cannot check user registration for a meeting");
        }
    }

}
