package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.MeetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingDaoTest extends AbstractIntegrationTest {
    private MeetingDao meetingDao = new MeetingDao(DBManager.getInstance());

    @Test
    public void testInsertMeeting() {
        //GIVEN
        Meeting meeting = new Meeting();
        meeting.setName("name3");
        meeting.setLocation(new Location());
        meeting.setLanguage(Language.RU);
        meeting.setDate("01.01.32");
        meeting.setParticipantsCount(1);
        meeting.setRegisteredUsers(1);
        meeting.setId(1);
        meeting.setTopics(new ArrayList<>());

        //WHEN
        meetingDao.insertMeeting(meeting);

        //THEN
        String providedResult = meetingDao.getMeetingByName(meeting.getName()).toString();
        String expectedResult = "Meeting{id=2, name='name3', date='01.01.32', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetMeeting() throws SQLException {
        Meeting meeting1 =  new Meeting();
        meeting1.setName("name");
        meeting1.setDate("01.01.32");
        meeting1.setLanguage(Language.RU);
        meeting1.setLocation(new Location());
        meetingDao.insertMeeting(meeting1);
        Meeting meeting = meetingDao.getMeetingByName("name");
        String expectedResult = "Meeting{id=2, name='name', date='01.01.32', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
        String providedResult = meeting.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetAllMeetings() throws SQLException {
        List<Meeting> meetingList = meetingDao.getAllMeetings();
        Assertions.assertNotEquals(1, meetingList.size());
    }

    @Test
    public void testExceptionThrownInInsertMeetingWhenDateHasPassed() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setDate("01.01.01");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(meeting));


        String expectedMessage = "Cannot create meeting with the date that has passed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testExceptionThrownInInsertMeetingWhenDateFormatIsInvalid() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setDate("01/01/01");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(meeting));


        String expectedMessage = "Invalid date format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testExceptionThrownInInsertMeetingWhenFieldsAreNull() throws SQLException {
        Meeting meeting = meetingDao.getMeetingByName("123123123");
        meeting.setDate("01.01.45");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.insertMeeting(meeting));


        String expectedMessage = "Cannot create meeting with the date that has passed";
        String actualMessage = exception.getMessage();

        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testExceptionThrownInSetMeetingDateWhenDateHasPassed() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setDate("12.06.45");
        String newDate = "01.01.01";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.setMeetingDate(1, newDate));


        String expectedMessage = "Cannot update meeting date with the date that has passed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
