package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class RegisteredUsersDao {
    private static final Logger log = LogManager.getLogger(RegisteredUsersDao.class);

    /**
     * A method to register user for a meeting
     * @param conn your database connection
     * @param userId id of user to be registered
     * @param meetingId the id of the meeting the user is registering for
     * @throws IllegalArgumentException when user registration for a meeting fails
     */
    public void registerUserForAMeeting(Connection conn, int userId, int meetingId){
        int rowcount = 0;
        ResultSet rs;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM registered_users where meeting_id = ? AND user_id = ?");
            preparedStatement.setInt(1, meetingId);
            preparedStatement.setInt(2, userId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                rowcount = rs.getInt("rowcount");
            }
            if (rowcount != 0){
                log.info("User's already registered");
                throw new IllegalArgumentException("User's already registered");
            }
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO registered_users (meeting_id, user_id) values (?, ?)");
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, userId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot register user for a meeting", e);
            throw new IllegalArgumentException("Cannot register user for a meeting");
        }
    }

    /**
     * A method to count how many users is registered for a meeting
     * @param conn your database connection
     * @param meetingId id of meeting to count registered users
     * @return integer number of registered users
     * @throws IllegalArgumentException when cannot count meeting's registered users
     */
    public int countMeetingRegisteredUsers(Connection conn, int meetingId){
        int userCount = 0;
        ResultSet rs;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM registered_users where meeting_id = ?");
            preparedStatement.setInt(1, meetingId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                userCount = rs.getInt("userCount");
            }
            return userCount;
        } catch (SQLException e) {
            log.error("Cannot count registered count", e);
            throw new IllegalArgumentException("Cannot get registered count");
        }
    }

    /**
     * A method to check if user is registered for a meeting
     * @param conn your database connection
     * @param userId id of user to check registration
     * @param meetingId id of meeting to check if user is registered
     * @return true if user registered, false if user is not registered
     * @throws IllegalArgumentException when cannot check topic for availability
     */
    public boolean isRegistered(Connection conn, int userId, int meetingId){
        int rowcount = 0;
        ResultSet rs;
        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM registered_users where meeting_id = ? AND user_id = ?")) {
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
