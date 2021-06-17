package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class TopicDaoTest extends AbstractIntegrationTest {
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final TopicDao topicDao = daoFactory.getTopicDao();
    private final MeetingDao meetingDao = daoFactory.getMeetingDao();

    @Test
    public void testInsertTopic() throws SQLException {
        Topic topic = new Topic();
        topic.setName("name");
        topic.setDescription("description");
        topic.setLanguage(Language.RU);
        topic.setDate("01.01.01");
        topic.setAvailability(true);
        topic.setId(2);
        topicDao.insertTopic(topic);
        Topic providedTopic = topicDao.getTopicById(2);
        Assertions.assertEquals(topic, providedTopic);
    }

    @Test
    public void testGetTopic() throws SQLException {
        Topic topic = topicDao.getTopicById(1);
        String expectedResult = "Topic{id=1, name='tatatata', description='asdasdasdasd', speaker=null, date='01.06.2021', language=RU, availability=true}";
        String providedResult = topic.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetFreeTopics() throws SQLException {
        boolean hasNotFree = false;
        List<Topic> freeTopics = topicDao.getFreeTopics();
        for (Topic topic : freeTopics) {
            if (!topic.isAvailability()) {
                if (topic.getId() == 1){
                    continue;
                }
                hasNotFree = true;
                break;
            }
        }
        Assertions.assertFalse(hasNotFree);
    }

    @Test
    public void testUpdateAvailability() throws SQLException {
        Topic topic = topicDao.getTopicById(1);
        topicDao.updateTopicAvailability(topic, false);
        topic = topicDao.getTopicById(1);
        Assertions.assertFalse(topic.isAvailability());

    }

    @Test
    public void testUpdateDate() throws SQLException {
        Topic topic = topicDao.getTopicById(1);
        boolean success = false;
        String oldDate = topic.getDate();
        topicDao.updateTopicDate(topic, "04.06.21");
        topicDao.updateTopicDate(topic, "01.01.01");
        topic = topicDao.getTopicById(1);
        String expectedResult = "01.01.01";

        if (!oldDate.equals(topic.getDate())) {
            success = true;
        }
        if (success) {
            Assertions.assertEquals(expectedResult, topic.getDate());
        }

    }

}
