package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.epam.rd.fp.util.Constants.CONNECTION_URL;
import static java.sql.DriverManager.getConnection;

public class MeetingDao {
    private final DBManager dbManager;
    private static final Logger log = LogManager.getLogger(MeetingDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";
    private static final String INSERT_MEETING_INTO_MEETING_TABLE = "INSERT into meeting (name, date, language) values (?, ?, ?)";
    private static final String SELECT_MEETING_ID_BY_NAME = "SELECT  id from meeting where name = ?";
    private static final String SELECT_MEETING_DATA_BY_NAME = "SELECT * FROM meeting WHERE name = ? ";
    private static final String UPDATE_MEETING_DATE_BY_ID = "UPDATE meeting SET date = ? WHERE id = ?";

    public MeetingDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * A method to insert a meeting to "meeting" table
     *
     * @param conn    your database connection
     * @param meeting a meeting to insert
     * @throws IllegalArgumentException when insertion fails
     */
    public void insertMeeting(Meeting meeting) {
        ResultSet rs;
        DateFormat df = new SimpleDateFormat("dd.MM.yy");
        Date meetingDate;
        try {
            meetingDate = df.parse(meeting.getDate());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
        Date date = new Date();
        if (date.getTime() > (meetingDate.getTime())) {
            throw new IllegalArgumentException("Cannot create meeting with the date that has passed");
        }

        try (Connection conn = dbManager.getConnection();
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
        } catch (SQLException | NullPointerException e) {
            log.error("Cannot insert meeting into meeting table", e);
            throw new IllegalArgumentException("Cannot insert meeting into meeting table", e);
        }
    }

    /**
     * A method to get meeting from "meeting" table by it's name
     *
     * @param conn your database connection
     * @param name a name of meeting yo want to get
     * @return a meeting with name you've entered
     * @throws IllegalArgumentException when cannot get meeting
     */
    public Meeting getMeetingByName(String name) {
        ResultSet rs;
        Meeting meeting = new Meeting();
        meeting.setName(name);
        try (Connection conn = dbManager.getConnection();
                PreparedStatement prepStat = conn.prepareStatement(SELECT_MEETING_DATA_BY_NAME)) {
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
        } catch (SQLException e) {
            log.error("Cannot get meeting from meeting table", e);
            throw new IllegalArgumentException("Cannot get meeting from meeting table");
        }
        return meeting;
    }

    /**
     * A method to get all meetings from "meeting" table
     *
     * @param conn your database connection
     * @return a list filled with meetings from table "meeting"
     * @throws IllegalArgumentException when cannot get meetings
     */
    public List<Meeting> getAllMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        ResultSet rs;
        try (Connection conn = dbManager.getConnection();
             Statement statement = conn.createStatement()) {
            rs = statement.executeQuery("SELECT * FROM meeting where id != 1");
            while (rs.next()) {
                Meeting meeting = new Meeting();
                meeting.setId(rs.getInt("id"));
                meeting.setName(rs.getString("name"));
                meeting.setDate(rs.getString("date"));
                if ("EN".equals(rs.getString("language"))) {
                    meeting.setLanguage(Language.EN);
                } else {
                    meeting.setLanguage(Language.RU);
                }
                meetings.add(meeting);
            }
        } catch (SQLException e) {
            log.error("Cannot get all meetings", e);
            throw new IllegalArgumentException("Cannot get all meetings");
        }
        return meetings;
    }

    /**
     * A method to change meeting's date
     *
     * @param conn your database connection
     * @param name name of meeting which date you want to change
     * @param date new meeting's date
     * @throws IllegalArgumentException when cannot set meeting's date
     */
    public void setMeetingDate(int meetingId, String date) {
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
        meeting.setId(meetingId);
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement(UPDATE_MEETING_DATE_BY_ID);
        ) {
            prepStat.setString(1, date);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot update meeting's date", e);
            throw new IllegalArgumentException("Cannot update meeting's date");
        }
    }

    public Meeting getMeetingById(int meetingId){
        ResultSet rs;
        Meeting meeting = new Meeting();
        meeting.setId(meetingId);
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM meeting WHERE id = ?")) {
            prepStat.setInt(1, meetingId);
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
        } catch (SQLException e) {
            log.error("Cannot get meeting from meeting table", e);
            throw new IllegalArgumentException("Cannot get meeting from meeting table");
        }
        return meeting;
    }
    /**
     * A method to bind topic with meeting
     *

     * @param topicId   id of topic to bind with meeting
     * @param meetingId id of meeting to bind with topic
     * @throws IllegalArgumentException when cannot bind topic with meeting
     */

    public void bindTopicIdWithMeetingId(int topicId, int meetingId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_topic (meeting_id, topic_id) values (?, ?)");
        ) {
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, topicId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind topic with meeting", e);
            throw new IllegalArgumentException("Cannot bind topic with meeting");
        }
    }

    /**
     * A method to topics connected to meeting
     *

     * @param meetingId id of meeting to get connected topics
     * @return a list of topics connected with meeting
     * @throws IllegalArgumentException when cannot get meeting's topics
     */
    public List<Topic> getMeetingTopics(int meetingId) {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao(dbManager);
        ResultSet rs;

        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM meeting_topic WHERE meeting_id = ?");
        ) {
            prepStat.setInt(1, meetingId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                Topic topic = topicDao.getTopicById(rs.getInt("topic_id"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            log.error("Cannot get meeting's topics", e);
            throw new IllegalArgumentException("Cannot get meeting's topics");
        }
        return topics;
    }

    /**
     * A method to delete meeting and topic connectivity
     *
     * @param topicId   id of topic to delete connection with meeting
     * @param meetingId id of meeting to delete connection with topic
     * @throws IllegalArgumentException when meeting and topic connectivity deletion fails
     */
    public void deleteMeetingAndTopicConnectivityById(int topicId, int meetingId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("DELETE from meeting_topic where topic_id = ? and meeting_id = ?");
        ) {
            prepStat.setInt(1, topicId);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot delete meeting and topic connectivity", e);
            throw new IllegalArgumentException("Cannot delete meeting and topic connectivity");
        }
    }

    /**
     * A method to replace one meeting's topic with another
     *
     * @param conn       your database connection
     * @param oldTopicId id of topic to be replaced
     * @param newTopicId id of new topic
     * @param meetingId  id of meeting which topic need to be updated
     * @throws IllegalArgumentException when updating meeting topics fails
     */
    public void updateMeetingTopic(int oldTopicId, int newTopicId, int meetingId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_topic SET topic_id = ? WHERE meeting_id = ? AND topic_id = ?");
        ) {
            prepStat.setInt(1, newTopicId);
            prepStat.setInt(2, meetingId);
            prepStat.setInt(3, oldTopicId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot update meeting's topic", e);
            throw new IllegalArgumentException("Cannot update meeting's topic");
        }
    }

    /**
     * A method to bind meeting with location
     *
     * @param conn       your database connection
     * @param locationId id of location to bind
     * @param meetingId  id of meeting to bind
     * @throws IllegalArgumentException when cannot bind location with meeting
     */
    public void bindLocationIdWithMeetingId(int locationId, int meetingId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_location (meeting_id, location_id) values (?, ?)");
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
    public void setMeetingLocation(int meetingId, int locationId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_location SET location_id = ? WHERE meeting_id = ?");
        ) {
            prepStat.setInt(1, locationId);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change meeting's location", e);
            throw new IllegalArgumentException("Cannot update meeting's date");
        }
    }

    /**
     * A method to add user as meeting participant
     *

     * @param userId    id of user to be a participant
     * @param meetingId id of meeting
     * @throws IllegalArgumentException when adding a participant fails
     */
    public void addMeetingParticipant(int userId, int meetingId) {
        int rowcount = 0;
        ResultSet rs;
        try (Connection conn = dbManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM meeting_participants where meeting_id = ? AND user_id = ?");
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

     * @param meetingId id of meeting to cont participants
     * @return integer number of meeting's participants
     * @throws IllegalArgumentException when cannot count meeting participants
     */
    public int countMeetingParticipants(int meetingId) {
        int userCount = 0;
        ResultSet rs;
        try (Connection conn = dbManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM meeting_participants where meeting_id = ?");
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

    /**
     * A method to count how many users is registered for a meeting
     *

     * @param meetingId id of meeting to count registered users
     * @return integer number of registered users
     * @throws IllegalArgumentException when cannot count meeting's registered users
     */
    public int countMeetingRegisteredUsers(int meetingId) {
        int userCount = 0;
        ResultSet rs;
        try (Connection conn = dbManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS userCount FROM registered_users where meeting_id = ?");
        ) {
            preparedStatement.setInt(1, meetingId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userCount = rs.getInt("userCount");
            }
            return userCount;
        } catch (SQLException e) {
            log.error("Cannot count registered count", e);
            throw new IllegalArgumentException("Cannot get registered count");
        }
    }
}