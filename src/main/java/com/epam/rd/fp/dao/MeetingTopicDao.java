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

    public void bindTopicIdWithMeetingId(Connection conn, int topicId, int meetingId) {
        try {
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO meeting_topic (meeting_id, topic_id) values (?, ?)");
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, topicId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind topic with meeting", e);
            throw new IllegalArgumentException("Cannot bind topic with meeting");
        }
    }

    public List<Topic> getSuggestedTopics(Connection conn) {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao();
        ResultSet rs;

        try {
            Statement statement = conn.createStatement();
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

    public List<Topic> getMeetingsTopics(Connection conn, int meetingId) {
        List<Topic> topics = new ArrayList<>();
        TopicDao topicDao = new TopicDao();
        ResultSet rs;

        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM meeting_topic WHERE meeting_id = ?");
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

    public void deleteMeetingAndTopicConnectivityById(Connection conn, int topicId, int meetingId) {
        try {
            PreparedStatement prepStat = conn.prepareStatement("DELETE from meeting_topic where topic_id = ? and meeting_id = ?");
            prepStat.setInt(1, topicId);
            prepStat.setInt(2, meetingId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot delete meeting and topic connectivity", e);
            throw new IllegalArgumentException("Cannot delete meeting and topic connectivity");
        }
    }

    public void updateMeetingTopic(Connection conn, int oldTopicId, int newTopicId, int meetingId){
        try {
            PreparedStatement prepStat = conn.prepareStatement("UPDATE meeting_topic SET topic_id = ? WHERE meeting_id = ? AND topic_id = ?");
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
