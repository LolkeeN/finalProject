package daoTests;

import com.epam.rd.fp.dao.*;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingTopicDaoTest extends AbstractIntegrationTest{
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final MeetingDao meetingDao = daoFactory.getMeetingDao();
    private final TopicDao topicDao = daoFactory.getTopicDao();

    public MeetingTopicDaoTest() throws SQLException {
    }

    @Test
    public void testBindTopicIdWithMeetingId() {
        Topic topic = topicDao.getTopicById(1);
        Meeting meeting = meetingDao.getMeetingById(1);
        meetingDao.bindTopicIdWithMeetingId(topic.getId(), meeting.getId());
        List<Topic> meetingTopics = meetingDao.getMeetingTopics(meeting.getId());
        Assertions.assertTrue(meetingTopics.contains(topic));
    }

    @Test
    public void testGetSuggestedTopics() {
        List<Topic> suggestedTopics = topicDao.getSuggestedTopics();
        Assertions.assertEquals(2, suggestedTopics.size());
    }

    @Test
    public void testGetMeetingTopics() {
        Meeting meeting = meetingDao.getMeetingById(1);
        Topic topic = topicDao.getTopicById(1);
        meetingDao.bindTopicIdWithMeetingId(topic.getId(), meeting.getId());
        List<Topic> meetingTopics = meetingDao.getMeetingTopics(meeting.getId());
        Assertions.assertTrue(meetingTopics.contains(topic));
    }

    @Test
    public void testDeleteMeetingAndTopicConnectivityById() {
        Meeting meeting = meetingDao.getMeetingById(1);
        meetingDao.deleteMeetingAndTopicConnectivityById(1, meeting.getId());
        Topic topic = topicDao.getTopicById(1);
        List<Topic> meetingTopics = meetingDao.getMeetingTopics(meeting.getId());
        Assertions.assertFalse(meetingTopics.contains(topic));
    }

    @Test
    public void testUpdateMeetingTopic() {
        //Given
        Topic topic = topicDao.getTopicById(1);
        Topic topic1 = new Topic();
        topic1.setDate("date");
        topic1.setDescription("description");
        topic1.setLanguage(Language.RU);
        topic1.setName("name");
        topic1.setAvailability(true);
        topicDao.insertTopic(topic1);
        Meeting meeting = meetingDao.getMeetingById(1);
        //When
        meetingDao.bindTopicIdWithMeetingId(topic1.getId(), meeting.getId());
        meetingDao.updateMeetingTopic(topic1.getId(), topic.getId(), meeting.getId());
        List<Topic> meetingTopics = meetingDao.getMeetingTopics(meeting.getId());

        //Then
        Assertions.assertTrue(meetingTopics.contains(topic));
    }

    @Test
    public void testExceptionThrownInUpdateMeetingTopic() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.bindTopicIdWithMeetingId(32, 23));


        String expectedMessage = "Cannot bind topic with meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
