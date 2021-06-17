package daoTests;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.factory.impl.ServiceFactoryImpl;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import com.epam.rd.fp.service.LocationService;
import com.epam.rd.fp.service.MeetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static com.epam.rd.fp.util.Constants.CONNECTION_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeetingLocationDaoTest extends AbstractIntegrationTest{
    private final DBManager dbManager = DBManager.getInstance();
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final MeetingDao meetingDao = daoFactory.getMeetingDao();
    private final LocationDao locationDao = daoFactory.getLocationDao();

    @Test
    public void bindLocationIdWithMeetingIdTest() throws SQLException {
        int providedMeetingId = 0;
        Meeting meeting = meetingDao.getMeetingById(1);
        Location location = locationDao.getLocation(1);
        meetingDao.bindLocationIdWithMeetingId(location.getId(), meeting.getId());
        providedMeetingId = getMeetingLocation(providedMeetingId, location);
        Assertions.assertEquals(meeting.getId(), providedMeetingId);
    }

    private int getMeetingLocation(int providedMeetingId, Location location) throws SQLException {
        PreparedStatement prepStat = dbManager.getConnection().prepareStatement("select * from meeting_location where location_id = ?");
        prepStat.setInt(1, location.getId());
        ResultSet rs = prepStat.executeQuery();
        while (rs.next()) {
            providedMeetingId = rs.getInt("meeting_id");
        }
        return providedMeetingId;
    }


    @Test
    public void testExceptionThrownInBindMeetingWithLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingDao.bindLocationIdWithMeetingId(123123, 123123));


        String expectedMessage = "Cannot bind location with meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
