package com.epam.rd.fp.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class MeetingParticipantsDao {
    private static final Logger log = LogManager.getLogger(RegisteredUsersDao.class);

    /**
     * A method to add user as meeting participant
     *
     * @param conn      your database connection
     * @param userId    id of user to be a participant
     * @param meetingId id of meeting
     * @throws IllegalArgumentException when adding a participant fails
     */
    public void addMeetingParticipant(Connection conn, int userId, int meetingId) {
        int rowcount = 0;
        ResultSet rs;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM meeting_participants where meeting_id = ? AND user_id = ?");
        ) {
            preparedStatement.setInt(1, meetingId);
            preparedStatement.setInt(2, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                rowcount = rs.getInt("rowcount");
            }
            if (rowcount != 0) {
                log.info("User's already taking part");
                throw new IllegalArgumentException("User's already taking part");
            }
            try (PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_participants (meeting_id, user_id) values (?, ?)");
            ) {
                prepStat.setInt(1, meetingId);
                prepStat.setInt(2, userId);
                prepStat.execute();
            }
        } catch (SQLException e) {
            log.error("Cannot add participant for a meeting", e);
            throw new IllegalArgumentException("Cannot add participant for a meeting");
        }
    }

    /**
     * A method to cont how many participants meeting has
     *
     * @param conn      your database connection
     * @param meetingId id of meeting to cont participants
     * @return integer number of meeting's participants
     * @throws IllegalArgumentException when cannot count meeting participants
     */
    public int countMeetingParticipants(Connection conn, int meetingId) {
        int userCount = 0;
        ResultSet rs;
        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM meeting_participants where meeting_id = ?");
        ) {
            preparedStatement.setInt(1, meetingId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userCount = rs.getInt("userCount");
            }
            return userCount;
        } catch (SQLException e) {
            log.error("Cannot register user for a meeting", e);
            throw new IllegalArgumentException("Cannot get participants count");
        }
    }

}
