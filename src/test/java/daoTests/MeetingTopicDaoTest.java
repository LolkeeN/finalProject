//package daoTests;
//
//import com.epam.rd.fp.dao.*;
//import com.epam.rd.fp.model.Meeting;
//import com.epam.rd.fp.model.Topic;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class MeetingTopicDaoTest {
//    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
//    Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//    MeetingTopicDao meetingTopicDao = new MeetingTopicDao();
//    TopicDao topicDao = new TopicDao();
//    MeetingDao meetingDao = new MeetingDao();
//
//    public MeetingTopicDaoTest() throws SQLException {
//    }
//
//    @Test
//    public void testBindTopicIdWithMeetingId() {
//        Topic topic = topicDao.getTopicById(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        meetingTopicDao.bindTopicIdWithMeetingId(connection, topic.getId(), meeting.getId());
//        List<Topic> meetingTopics = meetingTopicDao.getMeetingsTopics(connection, meeting.getId());
//        Assertions.assertTrue(meetingTopics.contains(topic));
//    }
//
//    @Test
//    public void testGetSuggestedTopics() {
//        List<Topic> suggestedTopics = meetingTopicDao.getSuggestedTopics(connection);
//        Assertions.assertEquals(0, suggestedTopics.size());
//    }
//
//    @Test
//    public void testGetMeetingsTopics() {
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        Topic topic = topicDao.getTopicById(connection, 1);
//        List<Topic> meetingTopics = meetingTopicDao.getMeetingsTopics(connection, meeting.getId());
//        Assertions.assertTrue(meetingTopics.contains(topic));
//    }
//
//    @Test
//    public void testDeleteMeetingAndTopicConnectivityById() {
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        meetingTopicDao.deleteMeetingAndTopicConnectivityById(connection, 1, meeting.getId());
//        Topic topic = topicDao.getTopicById(connection, 1);
//        List<Topic> meetingTopics = meetingTopicDao.getMeetingsTopics(connection, meeting.getId());
//        Assertions.assertFalse(meetingTopics.contains(topic));
//    }
//
//    @Test
//    public void testUpdateMeetingTopic() {
//        Topic topic = topicDao.getTopicById(connection, 2);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        meetingTopicDao.updateMeetingTopic(connection, 1, topic.getId(), meeting.getId());
//        List<Topic> meetingTopics = meetingTopicDao.getMeetingsTopics(connection, meeting.getId());
//        Assertions.assertTrue(meetingTopics.contains(topic));
//    }
//
//    @Test
//    public void testExceptionThrownInUpdateMeetingTopic() {
//            Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingTopicDao.bindTopicIdWithMeetingId(connection, 32, 23));
//
//
//        String expectedMessage = "Cannot bind topic with meeting";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
