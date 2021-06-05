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

    public void bindTopicWithSpeakerId(Connection conn, int topic_id, int speaker_id) {
        try {
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO topic_speaker (topic_id, speaker_id) values (?, ?)");
            prepStat.setInt(1, topic_id);
            prepStat.setInt(2, speaker_id);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot bind topic with speaker", e);
            throw new IllegalArgumentException("Cannot bind topic with speaker");
        }
    }

    public List<Topic> getTopicIdBySpeakerId(Connection conn, int speakerId){
        TopicDao topicDao = new TopicDao();
        List<Topic> topicsList = new ArrayList<>();
        ResultSet rs;

        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM topic_speaker WHERE speaker_id = ?");
            prepStat.setInt(1, speakerId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                topicsList.add(topicDao.getTopicById(conn, rs.getInt("topic_id")));
            }
        }catch (SQLException e){
            log.error("Cannot get speaker's topics", e);
            throw new IllegalArgumentException("Cannot get speaker's topics");
        }
        return topicsList;
    }

    public int getSpeakerIdByTopicId(Connection conn, int topicId){
        int speakerId = 0;
        ResultSet rs;

        try {
            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM topic_speaker WHERE topic_id = ?");
            prepStat.setInt(1, topicId);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                speakerId = rs.getInt("speaker_id");
            }
        }catch (SQLException e){
            log.error("Cannot get speaker's id", e);
            throw new IllegalArgumentException("Cannot get speaker's id");
        }
        return speakerId;
    }
}
