//package daoTests;
//
//import com.epam.rd.fp.dao.MeetingDao;
//import com.epam.rd.fp.dao.RegisteredUsersDao;
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
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RegisteredUsersDaoTest {
//
//    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
//
//    @Test
//    public void getAndAddRegisteredUserTest() throws SQLException {
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        UserDao userDao = new UserDao();
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        registeredUsersDao.registerUserForAMeeting(connection, user.getId(), meeting.getId());
//        int providedMeetingParticipants = registeredUsersDao.countMeetingRegisteredUsers(connection, meeting.getId());
//
//        Assertions.assertEquals(1, providedMeetingParticipants);
//    }
//
//    @Test
//    public void isRegisteredTest_True() throws SQLException {
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        UserDao userDao = new UserDao();
//        MeetingDao meetingDao = new MeetingDao();
//
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//
//        boolean providedResult = registeredUsersDao.isRegistered(connection, user.getId(), meeting.getId());
//        assertTrue(providedResult);
//    }
//
//    @Test
//    public void isRegisteredTest_False() throws SQLException {
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        UserDao userDao = new UserDao();
//        MeetingDao meetingDao = new MeetingDao();
//
//        User user = userDao.getUser(connection, 2);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//
//        boolean providedResult = registeredUsersDao.isRegistered(connection, user.getId(), meeting.getId());
//        assertFalse(providedResult);
//    }
//
//
//    @Test
//    public void whenExceptionThrown_thenAssertionSucceeds() throws SQLException {
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        UserDao userDao = new UserDao();
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> registeredUsersDao.registerUserForAMeeting(connection, user.getId(), meeting.getId()));
//
//        String expectedMessage = "User's already registered";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testExceptionThrownInRegisterUserForAMeeting() throws SQLException {
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> registeredUsersDao.registerUserForAMeeting(connection, 123123123, meeting.getId()));
//
//        String expectedMessage = "Cannot register user for a meeting";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testExceptionThrownInIsRegistered() throws SQLException {
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        MeetingDao meetingDao = new MeetingDao();
//
//        UserDao userDao = new UserDao();
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> registeredUsersDao.registerUserForAMeeting(connection, user.getId(), meeting.getId()));
//
//        String expectedMessage = "User's already registered";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    void testExceptionThrownInAddRegisteredUser() throws SQLException {
//        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
//        UserDao userDao = new UserDao();
//        MeetingDao meetingDao = new MeetingDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        User user = userDao.getUser(connection, 1);
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> registeredUsersDao.registerUserForAMeeting(connection, 123123, 123123));
//
//        String expectedMessage = "Cannot register user for a meeting";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//}
