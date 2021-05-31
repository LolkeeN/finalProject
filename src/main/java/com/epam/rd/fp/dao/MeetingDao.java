package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class MeetingDao {
    private static final Logger log = LogManager.getLogger(MeetingDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final String INSERT_MEETING_INTO_MEETING_TABLE = "INSERT into meeting (name, date, language) values (?, ?, ?)";
    private static final String SELECT_MEETING_ID_BY_NAME = "SELECT  id from meeting where name = ?";
    private static final String SELECT_MEETING_DATA_BY_NAME = "SELECT * FROM meeting WHERE name = ? ";
    private static final String UPDATE_MEETING_DATE_BY_NAME = "UPDATE meeting SET date = ? WHERE name = ?";

    public void insertMeeting(String connection, Meeting meeting) {
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try (Connection conn = getConnection(connection);
             PreparedStatement prepStat = conn.prepareStatement(INSERT_MEETING_INTO_MEETING_TABLE)) {

            prepStat.setString(1, meeting.getName());
            prepStat.setString(2, meeting.getDate());
            prepStat.setString(3, meeting.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement(SELECT_MEETING_ID_BY_NAME)) {
                prSt.setString(1, meeting.getName());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    meeting.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot insert meeting into meeting table", e);
            throw new IllegalArgumentException("Cannot insert meeting into meeting table", e);
        }
    }

    public Meeting getMeeting(String connection, String name) {
        ResultSet rs;
        Meeting meeting = new Meeting();
        meeting.setName(name);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = getConnection(connection);
            PreparedStatement prepStat = conn.prepareStatement(SELECT_MEETING_DATA_BY_NAME);
            prepStat.setString(1, name);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                meeting.setId(rs.getInt("id"));
                meeting.setDate(rs.getString("date"));
                if (rs.getString("language").equals("EN")) {
                    meeting.setLanguage(Language.EN);
                } else {
                    meeting.setLanguage(Language.RU);
                }
            }
        }catch (SQLException e){
            log.error("Cannot get meeting from meeting table", e);
            throw new IllegalArgumentException("Cannot get meeting from meeting table");
        }
        return meeting;
    }

    public List<Meeting> getAllMeetings(String connection){
        LocationDao locationDao = new LocationDao();
        List<Meeting> meetings = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        ResultSet rs;
        ResultSet resultSet;

        try {
            Connection conn = getConnection(connection);
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM meeting where id != 1");
            while (rs.next()) {
                Meeting meeting = new Meeting();
                meeting.setId(rs.getInt("id"));
                meeting.setName(rs.getString("name"));
                meeting.setDate(rs.getString("date"));
                if ("EN".equals(rs.getString("language"))){
                    meeting.setLanguage(Language.EN);
                }else{
                    meeting.setLanguage(Language.RU);
                }
                meetings.add(meeting);
            }
            for (Meeting meeting:meetings) {
                PreparedStatement prepStat = conn.prepareStatement("select location_id from meeting_location where meeting_id = ?");
                prepStat.setInt(1, meeting.getId());
                resultSet = prepStat.executeQuery();
            }
        }catch (SQLException e){
            log.error("Cannot get all meetings", e);
            throw new IllegalArgumentException("Cannot get all meetings");
        }
        return meetings;
    }

    public void setMeetingDate(String connection, String name, String date){
        Meeting meeting = new Meeting();
        meeting.setName(name);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = getConnection(connection);
            PreparedStatement prepStat = conn.prepareStatement(UPDATE_MEETING_DATE_BY_NAME);
            prepStat.setString(1, date);
            prepStat.setString(2, name);
            prepStat.execute();
        }catch (SQLException e){
            log.error("Cannot update meeting's date", e);
            throw new IllegalArgumentException("Cannot update meeting's date");
        }
    }
}
