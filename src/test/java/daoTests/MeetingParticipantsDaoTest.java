//package daoTests;
//
//import com.epam.rd.fp.dao.MeetingDao;
//import com.epam.rd.fp.dao.MeetingParticipantsDao;
//import com.epam.rd.fp.dao.UserDao;
//import com.epam.rd.fp.model.Meeting;
//import com.epam.rd.fp.model.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class MeetingParticipantsDaoTest {
//    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
//    UserDao userDao = new UserDao();
//    MeetingDao meetingDao = new MeetingDao();
//    Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//    MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
//
//
//    public MeetingParticipantsDaoTest() throws SQLException {
//    }
//
//
//    @Test
//    public void getAndAddMeetingParticipantsCountTest() throws SQLException {
//
//
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//
//        meetingParticipantsDao.addMeetingParticipant(connection, user.getId(), meeting.getId());
//        int meetingParticipants = meetingParticipantsDao.countMeetingParticipants(connection, meeting.getId());
//        Assertions.assertEquals(1, meetingParticipants);
//
//
//    }
//
//    @Test
//    public void testCountMeetingParticipants(){
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        int providedResult = meetingParticipantsDao.countMeetingParticipants(connection, meeting.getId());
//        Assertions.assertEquals(1, providedResult);
//    }
//
//    @Test
//    public void testExceptionThrownInAddParticipantToMeeting() {
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingParticipantsDao.addMeetingParticipant(connection, 1231231, 123123123));
//
//
//        String expectedMessage = "Cannot add participant for a meeting";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
