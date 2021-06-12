//package daoTests;
//
//import com.epam.rd.fp.dao.LocationDao;
//import com.epam.rd.fp.dao.MeetingDao;
//import com.epam.rd.fp.dao.MeetingLocationDao;
//import com.epam.rd.fp.model.Location;
//import com.epam.rd.fp.model.Meeting;
//import com.epam.rd.fp.model.enums.Language;
//import com.epam.rd.fp.service.DBManager;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class MeetingLocationDaoTest {
//    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
//
//    @Test
//    public void bindLocationIdWithMeetingIdTest() throws SQLException {
//        MeetingLocationDao meetingLocationDao = new MeetingLocationDao();
//        MeetingDao meetingDao = new MeetingDao();
//        LocationDao locationDao = new LocationDao(DBManager.getInstance());
//        int providedMeetingId = 0;
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        Location location = locationDao.getLocation(connection, 1);
//        meetingLocationDao.bindLocationIdWithMeetingId(connection, location.getId(), meeting.getId());
//        providedMeetingId = getMeetingLocation(providedMeetingId, connection, location);
//        Assertions.assertEquals(meeting.getId(), providedMeetingId);
//    }
//
//    private int getMeetingLocation(int providedMeetingId, Connection connection, Location location) throws SQLException {
//        PreparedStatement prepStat = connection.prepareStatement("select * from meeting_location where location_id = ?");
//        prepStat.setInt(1, location.getId());
//        ResultSet rs = prepStat.executeQuery();
//        while (rs.next()) {
//            providedMeetingId = rs.getInt("meeting_id");
//        }
//        return providedMeetingId;
//    }
//
//    @Test
//    public void setMeetingLocationTest() throws SQLException {
//        MeetingLocationDao meetingLocationDao = new MeetingLocationDao();
//        MeetingDao meetingDao = new MeetingDao();
//        LocationDao locationDao = new LocationDao(DBManager.getInstance());
//        int providedMeetingId = 0;
//        int expectedMeetingId = 0;
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        Meeting meeting = meetingDao.getMeeting(connection, "name");
//        Location location = locationDao.getLocation(connection, 1);
//
//        providedMeetingId = getMeetingLocation(providedMeetingId, connection, location);
//
//        Location location1 = new Location();
//        location1.setCountry("country1");
//        location1.setStreet("street1");
//        location1.setRoom("1");
//        location1.setHouse("2");
//        location1.setLanguage(Language.RU);
//        location1.setCity("city1");
//        locationDao.insertLocation(location1);
//        meetingLocationDao.setMeetingLocation(connection, meeting.getId(), location1.getId());
//        expectedMeetingId = getMeetingLocation(expectedMeetingId, connection, location1);
//        Assertions.assertEquals(expectedMeetingId, providedMeetingId);
//    }
//
//    @Test
//    public void testExceptionThrownInBindMeetingWithLocation() throws SQLException {
//        MeetingLocationDao meetingLocationDao = new MeetingLocationDao();
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingLocationDao.bindLocationIdWithMeetingId(connection, 123123, 123123));
//
//
//        String expectedMessage = "Cannot bind location with meeting";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//}
