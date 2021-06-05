package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.RegisteredUsersDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegisteredUsersDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void getAndAddRegisteredUserTest() throws SQLException {
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
        UserDao userDao = new UserDao();
        MeetingDao meetingDao = new MeetingDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");

        User user = userDao.getUser(connection, 1);
        Meeting meeting = meetingDao.getMeeting(connection, "name");
        registeredUsersDao.registerUserForAMeeting(connection, user.getId(), meeting.getId());
        int providedMeetingParticipants = registeredUsersDao.countMeetingRegisteredUsers(connection, meeting.getId());

        Assertions.assertEquals(1, providedMeetingParticipants);
    }

    @Test
    public void isRegisteredTest() throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        RegisteredUsersDao registeredUsersDao = new RegisteredUsersDao();
        UserDao userDao = new UserDao();
        MeetingDao meetingDao = new MeetingDao();

        User user = userDao.getUser(connection, 1);
        Meeting meeting = meetingDao.getMeeting(connection, "name");

        boolean providedResult = registeredUsersDao.isRegistered(connection, user.getId(), meeting.getId());
        Assertions.assertTrue(providedResult);
    }

}
