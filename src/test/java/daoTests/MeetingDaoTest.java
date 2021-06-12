//package daoTests;
//
//import com.epam.rd.fp.dao.MeetingDao;
//import com.epam.rd.fp.dao.MeetingLocationDao;
//import com.epam.rd.fp.dao.TopicDao;
//import com.epam.rd.fp.model.Location;
//import com.epam.rd.fp.model.Meeting;
//import com.epam.rd.fp.model.Topic;
//import com.epam.rd.fp.model.User;
//import com.epam.rd.fp.model.enums.Language;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class MeetingDaoTest {
//    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
//
//    @Test
//    public void testInsertMeeting() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        Meeting meeting = new Meeting();
//        meeting.setName("name3");
//        meeting.setLocation(new Location());
//        meeting.setLanguage(Language.RU);
//        meeting.setDate("01.01.01");
//        meeting.setParticipantsCount(1);
//        meeting.setRegisteredUsers(1);
//        meeting.setId(1);
//        meeting.setTopics(new ArrayList<>());
//        meetingDao.insertMeeting(connection, meeting);
//        String providedResult = meetingDao.getMeeting(connection, meeting.getName()).toString();
//        String expectedResult = "Meeting{id=3, name='name3', date='01.01.01', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
//        Assertions.assertEquals(expectedResult, providedResult);
//    }
//
//    @Test
//    public void testGetMeeting() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        String expectedResult = "Meeting{id=1, name='name', date='01.01.01', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
//        String providedResult = meeting.toString();
//        Assertions.assertEquals(expectedResult, providedResult);
//    }
//
//    @Test
//    public void testGetAllMeetings() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        List<Meeting> meetingList = meetingDao.getAllMeetings(connection);
//        Assertions.assertNotEquals(1, meetingList.size());
//    }
//
//    @Test
//    public void testExceptionThrownInInsertMeetingWhenDateHasPassed() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Meeting meeting = new Meeting();
//        meeting.setDate("01.01.01");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(connection, meeting));
//
//
//        String expectedMessage = "Cannot create meeting with the date that has passed";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testExceptionThrownInInsertMeetingWhenDateFormatIsInvalid() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Meeting meeting = new Meeting();
//        meeting.setDate("01/01/01");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(connection, meeting));
//
//
//        String expectedMessage = "Invalid date format";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//    @Test
//    public void testExceptionThrownInInsertMeetingWhenFieldsAreNull() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Meeting meeting = meetingDao.getMeeting(connection, "123123123");
//        meeting.setDate("01.01.45");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(connection, meeting));
//
//
//        String expectedMessage = "Cannot create meeting with the date that has passed";
//        String actualMessage = exception.getMessage();
//
//        System.out.println(actualMessage);
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testExceptionThrownInSetMeetingDateWhenDateHasPassed() throws SQLException {
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Meeting meeting = new Meeting();
//        meeting.setDate("12.06.45");
//        String newDate = "01.01.01";
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.setMeetingDate(connection, "name", newDate));
//
//
//        String expectedMessage = "Cannot update meeting date with the date that has passed";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//}
