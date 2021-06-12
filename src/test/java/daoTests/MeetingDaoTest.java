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

public class MeetingDaoTest {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();

    @Test
    public void testInsertMeeting() {
        Meeting meeting = new Meeting();
        meeting.setName("name3");
        meeting.setLocation(new Location());
        meeting.setLanguage(Language.RU);
        meeting.setDate("01.01.01");
        meeting.setParticipantsCount(1);
        meeting.setRegisteredUsers(1);
        meeting.setId(1);
        meeting.setTopics(new ArrayList<>());
        meetingService.createMeeting(meeting);
        String providedResult = meetingService.getMeetingByName(meeting.getName()).toString();
        String expectedResult = "Meeting{id=3, name='name3', date='01.01.01', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetMeeting() throws SQLException {
        Meeting meeting = meetingService.getMeetingByName( "name");
        String expectedResult = "Meeting{id=1, name='name', date='01.01.01', topics=[], registeredUsers=0, participantsCount=0, location=null, language=RU}";
        String providedResult = meeting.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetAllMeetings() throws SQLException {
        List<Meeting> meetingList = meetingService.getAllMeetings();
        Assertions.assertNotEquals(1, meetingList.size());
    }

    @Test
    public void testExceptionThrownInInsertMeetingWhenDateHasPassed() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setDate("01.01.01");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingService.createMeeting(meeting));


        String expectedMessage = "Cannot create meeting with the date that has passed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testExceptionThrownInInsertMeetingWhenDateFormatIsInvalid() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setDate("01/01/01");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingService.createMeeting(meeting));


        String expectedMessage = "Invalid date format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void testExceptionThrownInInsertMeetingWhenFieldsAreNull() throws SQLException {
        Meeting meeting = meetingService.getMeetingByName("123123123");
        meeting.setDate("01.01.45");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingService.createMeeting(meeting));


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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingService.setMeetingDate(1, newDate));


        String expectedMessage = "Cannot update meeting date with the date that has passed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
