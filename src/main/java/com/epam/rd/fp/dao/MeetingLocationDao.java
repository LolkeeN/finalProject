package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class MeetingLocationDao {
    private static final Logger log = LogManager.getLogger(MeetingLocationDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    public void bindLocationIdWithMeetingId(int locationId, int meetingId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_location (meeting_id, location_id) values (?, ?)");
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, locationId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind location with meeting", e);
            throw new IllegalArgumentException("Cannot bind location with meeting", e);
        }
    }

    public void setMeetingLocation(int meetingId, int locationId){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_location SET location_id = ? WHERE meeting_id = ?");
            prepStat.setInt(1, locationId);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change meeting's location", e);
            throw new IllegalArgumentException("Cannot update meeting's date");
        }
    }
}
