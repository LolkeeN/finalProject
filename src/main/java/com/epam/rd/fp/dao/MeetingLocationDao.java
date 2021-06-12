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

    /**
     * A method to bind meeting with location
     *
     * @param conn       your database connection
     * @param locationId id of location to bind
     * @param meetingId  id of meeting to bind
     * @throws IllegalArgumentException when cannot bind location with meeting
     */
    public void bindLocationIdWithMeetingId(Connection conn, int locationId, int meetingId) {
        try (PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_location (meeting_id, location_id) values (?, ?)");
        ) {
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, locationId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind location with meeting", e);
            throw new IllegalArgumentException("Cannot bind location with meeting", e);
        }
    }

    /**
     * A method to change meeting's location
     *
     * @param conn       your database connection
     * @param meetingId  id of meeting which location needs to be changed
     * @param locationId id of new location
     * @throws IllegalArgumentException when cannot set meeting's location
     */
    public void setMeetingLocation(Connection conn, int meetingId, int locationId) {
        try (PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_location SET location_id = ? WHERE meeting_id = ?");
        ) {
            prepStat.setInt(1, locationId);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change meeting's location", e);
            throw new IllegalArgumentException("Cannot update meeting's date");
        }
    }
}
