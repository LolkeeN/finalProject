package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class MeetingTopicDao {
    private static final Logger log = LogManager.getLogger(MeetingTopicDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    /**
     * A method to bind topic with meeting
     *
     * @param conn      your database connection
     * @param topicId   id of topic to bind with meeting
     * @param meetingId id of meeting to bind with topic
     * @throws IllegalArgumentException when cannot bind topic with meeting
     */
    public void bindTopicIdWithMeetingId(Connection conn, int topicId, int meetingId) {
        try (PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_topic (meeting_id, topic_id) values (?, ?)");
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
     * A method to get topics that speaker suggested
     *
     * @param conn your database connection
     * @return a list of topics
     * @throws IllegalArgumentException when cannot get suggested topics
     */
    public List<Topic> getSuggestedTopics(Connection conn) {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao();
        ResultSet rs;

        try (Statement statement = conn.createStatement();
        ) {
            rs = statement.executeQuery("SELECT * FROM meeting_topic WHERE meeting_id = 1");
            while (rs.next()) {
                Topic topic = topicDao.getTopicById(conn, rs.getInt("topic_id"));
                topics.add(topic);
            }
        } catch (SQLException e) {
            log.error("Cannot get suggested topics", e);
            throw new IllegalArgumentException("Cannot get suggested topics");
        }
        return topics;
    }

    /**
     * A method to topics connected to meeting
     *
     * @param conn      your database connection
     * @param meetingId id of meeting to get connected topics
     * @return a list of topics connected with meeting
     * @throws IllegalArgumentException when cannot get meeting's topics
     */
    public List<Topic> getMeetingsTopics(Connection conn, int meetingId) {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao();
        ResultSet rs;

        try (PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM meeting_topic WHERE meeting_id = ?");
        ) {
            prepStat.setInt(1, meetingId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                Topic topic = topicDao.getTopicById(conn, rs.getInt("topic_id"));
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
     * @param conn      your database connection
     * @param topicId   id of topic to delete connection with meeting
     * @param meetingId id of meeting to delete connection with topic
     * @throws IllegalArgumentException when meeting and topic connectivity deletion fails
     */
    public void deleteMeetingAndTopicConnectivityById(Connection conn, int topicId, int meetingId) {
        try (PreparedStatement prepStat = conn.prepareStatement("DELETE from meeting_topic where topic_id = ? and meeting_id = ?");
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
    public void updateMeetingTopic(Connection conn, int oldTopicId, int newTopicId, int meetingId) {
        try (PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_topic SET topic_id = ? WHERE meeting_id = ? AND topic_id = ?");
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
}
