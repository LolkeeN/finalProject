package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.DBManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.fp.util.Constants.CONNECTION_URL;
import static java.sql.DriverManager.getConnection;

public class TopicDao {
    private DBManager dbManager;
    private static final Logger log = LogManager.getLogger(TopicDao.class);
    private static final String INSERT_VALUES_INTO_TOPIC_TABLE = "INSERT into topic (name, date, description, language) values (?, ?, ?, ?)";

    public TopicDao(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    /**
     * A method to get topic from "topic" table by it's id
     *
     * @param conn     your database connection
     * @param topicId id of topic to get
     * @return topic with id you've entered
     * @throws IllegalArgumentException when cannot get meeting by id
     */
    public Topic getTopicById(int topicId) {
        ResultSet rs;
        Topic topic = new Topic();
        topic.setId(topicId);
        try (Connection conn = dbManager.getConnection();
                PreparedStatement prepStat = conn.prepareStatement("Select * from topic where id = ?");
        ) {
            prepStat.setInt(1, topicId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                topic.setName(rs.getString("name"));
                topic.setDate(rs.getString("date"));
                topic.setDescription(rs.getString("description"));
                topic.setAvailability(rs.getBoolean("available"));
                if (rs.getString("language").equals("EN")) {
                    topic.setLanguage(Language.EN);
                } else {
                    topic.setLanguage(Language.RU);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot get topic from topic table", e);
            throw new IllegalArgumentException("Cannot get topic");
        }
        return topic;
    }

    /**
     * A method to insert topic into "topic" table
     *
     * @param conn  your database connection
     * @param topic a topic to be inserted
     * @throws IllegalArgumentException when insertion fails
     */
    public void insertTopic(Topic topic) {
        ResultSet rs;
        try (Connection conn = dbManager.getConnection();
                PreparedStatement prepStat = conn.prepareStatement(INSERT_VALUES_INTO_TOPIC_TABLE)) {

            prepStat.setString(1, topic.getName());
            prepStat.setString(2, topic.getDate());
            prepStat.setString(3, topic.getDescription());
            prepStat.setString(4, topic.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement("SELECT  id from topic where name = ?")) {
                prSt.setString(1, topic.getName());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    topic.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            log.error("Cannot insert topic into topic table", e);
            throw new IllegalArgumentException("Cannot insert topic into topic table");
        }
    }

    /**
     * A method to get all topics which availability is "true"
     *
     * @param conn your database connection
     * @return a list of topics which availability is "true"
     * @throws IllegalArgumentException when cannot get all free topics
     */
    public List<Topic> getFreeTopics() {
        List<Topic> topics = new ArrayList<>();
        ResultSet rs;

        try (Connection conn = dbManager.getConnection();
                Statement statement = conn.createStatement();
        ) {
            rs = statement.executeQuery("SELECT * FROM topic WHERE available = 'true'");
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id"));
                topic.setName(rs.getString("name"));
                topic.setDescription(rs.getString("description"));
                topic.setDate(rs.getString("date"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            log.error("Cannot get free topics", e);
            throw new IllegalArgumentException("Cannot get free topics");
        }
        return topics;
    }

    /**
     * A method to update topics availability
     *
     * @param conn        your database connection
     * @param topic       a topic which availability needs to be changed
     * @param isAvailable new availability
     * @throws IllegalArgumentException when cannot update meeting's availability
     */
    public void updateTopicAvailability(Topic topic, boolean isAvailable) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement prepStat = conn.prepareStatement("UPDATE topic SET available = ? WHERE name = ?");
        ) {
            prepStat.setString(1, Boolean.toString(isAvailable));
            prepStat.setString(2, topic.getName());
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change topic's availability", e);
            throw new IllegalArgumentException("Cannot change topic's availability");
        }
    }

    /**
     * A method to update topics date
     *
     * @param conn  your database connection
     * @param topic a topic which date needs to be updated
     * @param date  a new topic's date
     * @throws IllegalArgumentException when cannot update topic's date
     */
    public void updateTopicDate(Topic topic, String date) {
        try (Connection conn = dbManager.getConnection();
                PreparedStatement prepStat = conn.prepareStatement("UPDATE topic SET date = ? WHERE name = ?");
        ) {
            prepStat.setString(1, date);
            prepStat.setString(2, topic.getName());
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change topic's date", e);
            throw new IllegalArgumentException("Cannot change topic's availability");
        }
    }

    /**
     * A method to get topics that speaker suggested
     *
     * @param conn your database connection
     * @return a list of topics
     * @throws IllegalArgumentException when cannot get suggested topics
     */
    public List<Topic> getSuggestedTopics() {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao(dbManager);
        ResultSet rs;

        try (Connection conn = dbManager.getConnection();
             Statement statement = conn.createStatement();
        ) {
            rs = statement.executeQuery("SELECT * FROM meeting_topic WHERE meeting_id = 1");
            while (rs.next()) {
                Topic topic = topicDao.getTopicById(rs.getInt("topic_id"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            log.error("Cannot get suggested topics", e);
            throw new IllegalArgumentException("Cannot get suggested topics");
        }
        return topics;
    }

    /**
     * A method to connect topic with speaker
     *
     * @param conn       your database connection
     * @param topicId   id of topic to be connected with speaker
     * @param speakerId id of speaker to be connected with topic
     * @throws IllegalArgumentException when cannot bind topic with speaker
     */
    public void bindTopicWithSpeakerId(int topicId, int speakerId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("INSERT INTO topic_speaker (topic_id, speaker_id) values (?, ?)");
        ) {
            prepStat.setInt(1, topicId);
            prepStat.setInt(2, speakerId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind topic with speaker", e);
            throw new IllegalArgumentException("Cannot bind topic with speaker");
        }
    }

    /**
     * A method to get all topics connected to speaker
     *

     * @param speakerId id of the speaker whose topics need to be retrieved
     * @return list of topics connected to speaker
     * @throws IllegalArgumentException when cannot get topic id by speaker id
     */
    public List<Topic> getTopicIdBySpeakerId(int speakerId) {
        TopicDao topicDao = new TopicDao(dbManager);
        List<Topic> topicsList = new ArrayList<>();
        ResultSet rs;

        try (Connection conn = dbManager.getConnection();
             PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM topic_speaker WHERE speaker_id = ?")){

            prepStat.setInt(1, speakerId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                topicsList.add(topicDao.getTopicById(rs.getInt("topic_id")));
            }
        } catch (SQLException e) {
            log.error("Cannot get speaker's topics", e);
            throw new IllegalArgumentException("Cannot get speaker's topics");
        }
        return topicsList;
    }
}
