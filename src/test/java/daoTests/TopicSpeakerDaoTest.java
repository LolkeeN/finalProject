package daoTests;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.TopicSpeakerDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicSpeakerDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void bindSpeakerWithTopic() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        TopicDao topicDao = new TopicDao();
        UserDao userDao = new UserDao();
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        Topic topic = topicDao.getTopicById(connection, 1);
        userDao.insertUser(connection, user);
        int providedTopicId = 0;

        topicSpeakerDao.bindTopicWithSpeakerId(connection, topic.getId(), user.getId());
        PreparedStatement prepStat = connection.prepareStatement("select * from topic_speaker where speaker_id = ?");
        prepStat.setInt(1, user.getId());
        ResultSet rs = prepStat.executeQuery();
        while(rs.next()){
            providedTopicId = rs.getInt("topic_id");
        }

        Assertions.assertEquals(topic.getId(), providedTopicId);
    }

    @Test
    public void getSpeakerIdByTopicIdTest() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        UserDao userDao = new UserDao();
        TopicDao topicDao = new TopicDao();
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        userDao.insertUser(connection, user);

        Topic topic = topicDao.getTopicById(connection, 2);
        topicSpeakerDao.bindTopicWithSpeakerId(connection, topic.getId(), user.getId());

        int providedSpeakerId = topicSpeakerDao.getSpeakerIdByTopicId(connection, topic.getId());
        Assertions.assertEquals(user.getId(), providedSpeakerId);
    }

    @Test
    public void getTopicIdBySpeakerIdTest() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        TopicSpeakerDao topicSpeakerDao = new TopicSpeakerDao();
        UserDao userDao = new UserDao();
        TopicDao topicDao = new TopicDao();
        List<Integer> topicIds = new ArrayList<>();
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        userDao.insertUser(connection, user);

        Topic topic = topicDao.getTopicById(connection, 2);
        topicSpeakerDao.bindTopicWithSpeakerId(connection, topic.getId(), user.getId());
        List<Topic> speakersTopics = topicSpeakerDao.getTopicIdBySpeakerId(connection, user.getId());
        for (Topic topic1 :speakersTopics) {
            topicIds.add(topic1.getId());
        }
        Assertions.assertTrue(topicIds.contains(topic.getId()));
    }
}
