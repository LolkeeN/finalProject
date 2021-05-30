package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class TopicSpeakerDao {
    private static final Logger log = LogManager.getLogger(TopicSpeakerDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    public void bindTopicWithSpeakerId(int topic_id, int speaker_id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO topic_speaker (topic_id, speaker_id) values (?, ?)");
            prepStat.setInt(1, topic_id);
            prepStat.setInt(2, speaker_id);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind topic with speaker", e);
        }
    }

    public List<Topic> getTopicIdBySpeakerId(int speakerId){
        TopicDao topicDao = new TopicDao();
        List<Topic> topicsList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        ResultSet rs;

        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM topic_speaker WHERE speaker_id = ?");
            prepStat.setInt(1, speakerId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                topicsList.add(topicDao.getTopicById(rs.getInt("topic_id")));
            }
        }catch (SQLException e){
            log.error("Cannot get speaker's topics", e);
        }
        return topicsList;
    }
}
