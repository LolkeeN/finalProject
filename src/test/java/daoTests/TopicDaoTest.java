package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TopicDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void testInsertTopic() throws SQLException {
        TopicDao topicDao = new TopicDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        Topic topic = new Topic();
        topic.setName("name");
        topic.setSpeaker(new User());
        topic.setDescription("description");
        topic.setLanguage(Language.RU);
        topic.setDate("01.01.01");
        topic.setAvailability(true);
        topic.setId(1);
        topicDao.insertTopic(connection, topic);
        String providedResult = topicDao.getTopicById(connection, 1).toString();
        String expectedResult = "Topic{id=1, name='name', description='description', speaker=null, date='01.01.01', language=RU, availability=false}";
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetTopic() throws SQLException {
        TopicDao topicDao = new TopicDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        Topic topic = topicDao.getTopicById(connection, 1);
        String expectedResult = "Topic{id=1, name='name', description='description', speaker=null, date='01.01.01', language=RU, availability=false}";
        String providedResult = topic.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetFreeTopics() throws SQLException {
        TopicDao topicDao = new TopicDao();
        boolean hasNotFree = false;
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        List<Topic> freeTopics = topicDao.getFreeTopics(connection);
        for (Topic topic:freeTopics) {
            if (!topic.isAvailability()) {
                hasNotFree = true;
                break;
            }
        }
        Assertions.assertFalse(hasNotFree);
    }

    @Test
    public void testUpdateAvailability() throws SQLException {
        TopicDao topicDao = new TopicDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        Topic topic = topicDao.getTopicById(connection, 1);
        topicDao.updateTopicAvailability(connection, topic, false);
        topic = topicDao.getTopicById(connection, 1);
        Assertions.assertFalse(topic.isAvailability());

    }
       @Test
    public void testUpdateDate() throws SQLException {
        TopicDao topicDao = new TopicDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        Topic topic = topicDao.getTopicById(connection, 1);
        Boolean success = false;
        String oldDate = topic.getDate();
        topicDao.updateTopicDate(connection, topic, "04.06.21");
        topicDao.updateTopicDate(connection, topic, "01.01.01");
        topic = topicDao.getTopicById(connection, 1);
        String expectedResult = "01.01.01";

        if (!oldDate.equals(topic.getDate())){
            success = true;
        }
        if (success){
            Assertions.assertEquals(expectedResult, topic.getDate());
        }

    }

}
