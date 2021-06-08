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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class MeetingDao {
    private static final Logger log = LogManager.getLogger(MeetingDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final String INSERT_MEETING_INTO_MEETING_TABLE = "INSERT into meeting (name, date, language) values (?, ?, ?)";
    private static final String SELECT_MEETING_ID_BY_NAME = "SELECT  id from meeting where name = ?";
    private static final String SELECT_MEETING_DATA_BY_NAME = "SELECT * FROM meeting WHERE name = ? ";
    private static final String UPDATE_MEETING_DATE_BY_NAME = "UPDATE meeting SET date = ? WHERE name = ?";

    /**
     * A method to insert a meeting to "meeting" table
     * @param conn your database connection
     * @param meeting a meeting to insert
     * @throws IllegalArgumentException when insertion fails
     */
    public void insertMeeting(Connection conn, Meeting meeting) {
        ResultSet rs;
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        Date meetingDate;
        try {
            meetingDate = df.parse(meeting.getDate());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        Date date = new Date();
        if (date.getTime() != (meetingDate.getTime())) {
            throw new IllegalArgumentException("Cannot create meeting with the date that has passed");
        }

        try (PreparedStatement prepStat = conn.prepareStatement(INSERT_MEETING_INTO_MEETING_TABLE)) {

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
        } catch (SQLException | NullPointerException e) {
            log.error("Cannot insert meeting into meeting table", e);
            throw new IllegalArgumentException("Cannot insert meeting into meeting table", e);
        }
    }

    /**
     * A method to get meeting from "meeting" table by it's name
     * @param conn your database connection
     * @param name a name of meeting yo want to get
     * @return a meeting with name you've entered
     * @throws IllegalArgumentException when cannot get meeting
     */
    public Meeting getMeeting(Connection conn, String name) {
        ResultSet rs;
        Meeting meeting = new Meeting();
        meeting.setName(name);
        try {
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

    /**
     * A method to get all meetings from "meeting" table
     * @param conn your database connection
     * @return a list filled with meetings from table "meeting"
     * @throws IllegalArgumentException when cannot get meetings
     */
    public List<Meeting> getAllMeetings(Connection conn){
        List<Meeting> meetings = new ArrayList<>();
        ResultSet rs;
        try {
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
        }catch (SQLException e){
            log.error("Cannot get all meetings", e);
            throw new IllegalArgumentException("Cannot get all meetings");
        }
        return meetings;
    }

    /**
     * A method to change meeting's date
     * @param conn your database connection
     * @param name name of meeting which date you want to change
     * @param date new meeting's date
     * @throws IllegalArgumentException when cannot set meeting's date
     */
    public void setMeetingDate(Connection conn, String name, String date){
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        Date meetingDate;
        try {
            meetingDate = df.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        Date date1 = new Date();
        if (date1.getTime() != (meetingDate.getTime())) {
            throw new IllegalArgumentException("Cannot update meeting date with the date that has passed");
        }
        Meeting meeting = new Meeting();
        meeting.setName(name);
        try {
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
