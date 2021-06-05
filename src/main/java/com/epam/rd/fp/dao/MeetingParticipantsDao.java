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

    public void addMeetingParticipant(Connection conn, int userId, int meetingId){
        int rowcount = 0;
        ResultSet rs;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM meeting_participants where meeting_id = ? AND user_id = ?");
            preparedStatement.setInt(1, meetingId);
            preparedStatement.setInt(2, userId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                rowcount = rs.getInt("rowcount");
            }
            if (rowcount != 0){
                log.info("User's already taking part");
                throw new IllegalArgumentException("User's already taking part");
            }
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_participants (meeting_id, user_id) values (?, ?)");
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, userId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot add participant for a meeting", e);
            throw new IllegalArgumentException("Cannot add participant for a meeting");
        }
    }

    public int countMeetingParticipants(Connection conn, int meetingId){
        int userCount = 0;
        ResultSet rs;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM meeting_participants where meeting_id = ?");
            preparedStatement.setInt(1, meetingId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                userCount = rs.getInt("userCount");
            }
            return userCount;
        } catch (SQLException e) {
            log.error("Cannot register user for a meeting", e);
            throw new IllegalArgumentException("Cannot get participants count");
        }
    }

}
