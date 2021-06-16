package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.service.MeetingService;
import liquibase.statement.AbstractSqlStatement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingParticipantsDaoTest extends AbstractIntegrationTest {
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final MeetingDao meetingDao = daoFactory.getMeetingDao();
    private final UserDao userDao = daoFactory.getUserDao();


    public MeetingParticipantsDaoTest() throws SQLException {
    }


    @Test
    public void getAndAddMeetingParticipantsCountTest() throws SQLException {


        User user = userDao.getUser(1);
        Meeting meeting = meetingDao.getMeetingById(1);

        meetingDao.addMeetingParticipant(user.getId(), meeting.getId());
        int meetingParticipants = meetingDao.countMeetingParticipants(meeting.getId());
        Assertions.assertEquals(1, meetingParticipants);


    }

    @Test
    public void testCountMeetingParticipants(){
        Meeting meeting = meetingDao.getMeetingById(1);
        int providedResult = meetingDao.countMeetingParticipants(meeting.getId());
        Assertions.assertEquals(0, providedResult);
    }

    @Test
    public void testExceptionThrownInAddParticipantToMeeting() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.addMeetingParticipant(1231231, 123123123));


        String expectedMessage = "Cannot add participant for a meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
