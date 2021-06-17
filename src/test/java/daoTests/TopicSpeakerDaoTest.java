package daoTests;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicSpeakerDaoTest extends AbstractIntegrationTest{
    private final DBManager dbManager = DBManager.getInstance();
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final TopicDao topicDao = daoFactory.getTopicDao();
    private final UserDao userDao = daoFactory.getUserDao();

    @Test
    public void bindSpeakerWithTopic() throws SQLException {
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        Topic topic = topicDao.getTopicById(1);
        userDao.insertUser(user);
        int providedTopicId = 0;

        topicDao.bindTopicWithSpeakerId(topic.getId(), user.getId());
        PreparedStatement prepStat = dbManager.getConnection().prepareStatement("select * from topic_speaker where speaker_id = ?");
        prepStat.setInt(1, user.getId());
        ResultSet rs = prepStat.executeQuery();
        while (rs.next()) {
            providedTopicId = rs.getInt("topic_id");
        }

        Assertions.assertEquals(topic.getId(), providedTopicId);
    }

    @Test
    public void getSpeakerIdByTopicIdTest() throws SQLException {
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        userDao.insertUser(user);

        Topic topic = topicDao.getTopicById(1);
        topicDao.bindTopicWithSpeakerId(topic.getId(), user.getId());

        int providedSpeakerId = userDao.getSpeakerIdByTopicId(topic.getId());
        Assertions.assertEquals(user.getId(), providedSpeakerId);
    }

    @Test
    public void getTopicIdBySpeakerIdTest() throws SQLException {
        List<Integer> topicIds = new ArrayList<>();
        User user = new User();
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.SPEAKER);
        userDao.insertUser(user);

        Topic topic = topicDao.getTopicById(1);
        topicDao.bindTopicWithSpeakerId(topic.getId(), user.getId());
        List<Topic> speakersTopics = topicDao.getTopicIdBySpeakerId(user.getId());
        for (Topic topic1 : speakersTopics) {
            topicIds.add(topic1.getId());
        }
        Assertions.assertTrue(topicIds.contains(topic.getId()));
    }
}
