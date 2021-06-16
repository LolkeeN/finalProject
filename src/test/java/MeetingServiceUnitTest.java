import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.service.impl.MeetingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MeetingServiceUnitTest {
    @Mock
    private TopicDao topicDao;
    @Mock
    private MeetingDao meetingDao;
    @InjectMocks
    private MeetingServiceImpl meetingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldBindSuggestedTopicWithMeeting() {
        //GIVEN
        int topicId = 123;
        int meetingId = 123;
        Topic topic = new Topic();
        topic.setId(topicId);
        Meeting meeting = new Meeting();
        meeting.setId(meetingId);
        meeting.setDate("date");

        Mockito.when(topicDao.getTopicById(topicId)).thenReturn(topic);
        Mockito.when(meetingDao.getMeetingById(meetingId)).thenReturn(meeting);

        //WHEN
        meetingService.bindSuggestedTopicWithMeeting(topicId, meetingId);

        //THEN
        Mockito.verify(topicDao).getTopicById(topicId);
        Mockito.verify(meetingDao).getMeetingById(meetingId);
        Mockito.verify(topicDao).updateTopicDate(topic, meeting.getDate());
        Mockito.verify(meetingDao).bindTopicIdWithMeetingId(topicId, meetingId);
        Mockito.verify(meetingDao).deleteMeetingAndTopicConnectivityById(topicId, 1);
    }

    @Test
    void shouldChangeMeetingTimeAndPlace() {
        //GIVEN
        Meeting meeting = new Meeting();
        Location location = new Location();
        int meetingId = 4124;
        int locationId = 4124;
        String date = "123123";
        meeting.setId(meetingId);
        meeting.setDate(date);
        location.setId(locationId);
        Mockito.when(meetingDao.getMeetingById(meetingId)).thenReturn(meeting);

        //WHEN
        meetingService.changeMeetingTimeAndPlace(meetingId, date, locationId);

        //THEN
        Mockito.verify(meetingDao).setMeetingDate(meetingId, date);
        Mockito.verify(meetingDao).setMeetingLocation(meetingId, locationId);
    }
}
