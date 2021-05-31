package com.epam.rd.fp.dao;

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

public class TopicDao {
    private static final Logger log = LogManager.getLogger(TopicDao.class);
    private static final String INSERT_VALUES_INTO_TOPIC_TABLE = "INSERT into topic (name, date, description, language) values (?, ?, ?, ?)";

    public Topic getTopicById(String connection, int topic_id) {
        ResultSet rs;
        Topic topic = new Topic();
        topic.setId(topic_id);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(connection);
            PreparedStatement prepStat = conn.prepareStatement("Select * from topic where id = ?");
            prepStat.setInt(1, topic_id);
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
        }catch (SQLException e){
            log.error("Cannot get topic from topic table", e);
            throw new IllegalArgumentException("Cannot get topic");
        }
        return topic;
    }

    public void insertTopic(String connection, Topic topic){
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try (Connection conn = getConnection(connection);
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

    public List<Topic> getFreeTopics(String connection){
        List<Topic> topics = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        ResultSet rs;

        try {
            Connection conn = getConnection(connection);
            Statement statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM topic WHERE available = 'true'");
            while (rs.next()) {
                Topic topic = new Topic();
                topic.setId(rs.getInt("id"));
                topic.setName(rs.getString("name"));
                topic.setDescription(rs.getString("description"));
                topic.setDate(rs.getString("date"));
                topics.add(topic);
            }
        }catch (SQLException e){
            log.error("Cannot get free topics", e);
            throw new IllegalArgumentException("Cannot get free topics");
        }
        return topics;
    }

    public void updateTopicAvailability(String connection, Topic topic, boolean isAvailable){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(connection);
            PreparedStatement prepStat = conn.prepareStatement("UPDATE topic SET available = ? WHERE name = ?");
            prepStat.setString(1, Boolean.toString(isAvailable));
            prepStat.setString(2, topic.getName());
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot change topic's availability", e);
            throw new IllegalArgumentException("Cannot change topic's availability");
        }
    }
}
