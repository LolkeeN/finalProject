package entityTests;

import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.enums.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MeetingTest {

    @Test
    public void testMeetingSetters(){
        Location location = new Location();
        Meeting meeting = new Meeting();
        location.setId(1);
        location.setCity("Dnipro");
        location.setLanguage(Language.RU);
        location.setHouse("23");
        location.setRoom("12");
        location.setStreet("Pushkina");
        location.setCountry("Ukraine");

        meeting.setRegisteredUsers(2);
        meeting.setParticipantsCount(3);
        meeting.setLocation(location);
        meeting.setDate("03.06.21");
        meeting.setId(12);
        meeting.setTopics(new ArrayList<>());
        meeting.setLanguage(Language.EN);
        meeting.setName("name");
        String expectedResult = "Meeting{id=12, name='name', date='03.06.21', topics=[], registeredUsers=2, participantsCount=3, location=Location{country='Ukraine', city='Dnipro', street='Pushkina', house='23', room='12', language='RU'}, language=EN}";
        String providedResult = meeting.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testLocationGetters(){
        Location location = new Location();
        Meeting meeting = new Meeting();
        location.setId(1);
        location.setCity("Dnipro");
        location.setLanguage(Language.RU);
        location.setHouse("23");
        location.setRoom("12");
        location.setStreet("Pushkina");
        location.setCountry("Ukraine");

        meeting.setRegisteredUsers(2);
        meeting.setParticipantsCount(3);
        meeting.setLocation(location);
        meeting.setDate("03.06.21");
        meeting.setId(12);
        List<Topic> topics = new ArrayList<>();
        meeting.setTopics(topics);
        meeting.setLanguage(Language.EN);
        meeting.setName("name");

        Assertions.assertEquals(12, meeting.getId());
        Assertions.assertEquals(2, meeting.getRegisteredUsers());
        Assertions.assertEquals(3, meeting.getParticipantsCount());
        Assertions.assertEquals("03.06.21", meeting.getDate());
        Assertions.assertEquals(location, meeting.getLocation());
        Assertions.assertEquals(topics, meeting.getTopics());
        Assertions.assertEquals("en", meeting.getLanguage().getValue());
    }

    @Test
    public void testMeetingConstructor(){
        Meeting meeting = new Meeting("name", "date", new ArrayList<>(), new Location(), Language.EN);
        Meeting meeting2 = new Meeting();
        Assertions.assertNotEquals(meeting, meeting2);
    }
}
