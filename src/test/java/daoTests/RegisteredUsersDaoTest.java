package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RegisteredUsersDaoTest extends AbstractIntegrationTest{
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final UserDao userDao = daoFactory.getUserDao();
    private final MeetingDao meetingDao = daoFactory.getMeetingDao();


    @Test
    public void getAndAddRegisteredUserTest() throws SQLException {
        User user = userDao.getUser(1);
        Meeting meeting = meetingDao.getMeetingById(1);
        userDao.registerUserForAMeeting(user.getId(), meeting.getId());
        int providedMeetingParticipants = meetingDao.countMeetingRegisteredUsers(meeting.getId());

        Assertions.assertEquals(1, providedMeetingParticipants);
    }

    @Test
    public void isRegisteredTest_True() throws SQLException {
        User user = userDao.getUser(1);
        Meeting meeting = meetingDao.getMeetingById(1);
        userDao.registerUserForAMeeting(user.getId(), meeting.getId());

        boolean providedResult = userDao.isRegistered(user.getId(), meeting.getId());
        assertTrue(providedResult);
    }

    @Test
    public void isRegisteredTest_False() throws SQLException {
        User user = userDao.getUser(2);
        Meeting meeting = meetingDao.getMeetingById(1);

        boolean providedResult = userDao.isRegistered(user.getId(), meeting.getId());
        assertFalse(providedResult);
    }

    @Test
    public void testExceptionThrownInRegisterUserForAMeeting() throws SQLException {
        Meeting meeting = meetingDao.getMeetingById(1);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDao.registerUserForAMeeting(123123123, meeting.getId()));

        String expectedMessage = "Cannot register user for a meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testExceptionThrownInAddRegisteredUser() throws SQLException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDao.registerUserForAMeeting(123123, 123123));

        String expectedMessage = "Cannot register user for a meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
