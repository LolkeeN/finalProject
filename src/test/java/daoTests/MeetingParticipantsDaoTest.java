package daoTests;

import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.MeetingParticipantsDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MeetingParticipantsDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void getAndAddMeetingParticipantsCountTest() throws SQLException {
        MeetingParticipantsDao meetingParticipantsDao = new MeetingParticipantsDao();
        UserDao userDao = new UserDao();
        MeetingDao meetingDao = new MeetingDao();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");

        User user = userDao.getUser(connection, 1);
        Meeting meeting = meetingDao.getMeeting(connection, "name");

        meetingParticipantsDao.addMeetingParticipant(connection, user.getId(), meeting.getId());
        int meetingParticipants = meetingParticipantsDao.countMeetingParticipants(connection, meeting.getId());
        Assertions.assertEquals(1, meetingParticipants);


    }
}
