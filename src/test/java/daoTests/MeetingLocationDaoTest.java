package daoTests;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.factory.ServiceFactory;
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

public class MeetingLocationDaoTest {
    private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
    private final MeetingService meetingService = serviceFactory.getMeetingService();
    private final LocationService locationService = serviceFactory.getLocationService();

    @Test
    public void bindLocationIdWithMeetingIdTest() throws SQLException {
        int providedMeetingId = 0;
        Meeting meeting = meetingService.getMeetingByName("name");
        Location location = locationService.getLocation(1);
        meetingService.bindLocationIdWithMeetingId(location.getId(), meeting.getId());
        providedMeetingId = getMeetingLocation(providedMeetingId, location);
        Assertions.assertEquals(meeting.getId(), providedMeetingId);
    }

    private int getMeetingLocation(int providedMeetingId, Location location) throws SQLException {
        DBManager dbManager = DBManager.getInstance();
        PreparedStatement prepStat = dbManager.getConnection(CONNECTION_URL).prepareStatement("select * from meeting_location where location_id = ?");
        prepStat.setInt(1, location.getId());
        ResultSet rs = prepStat.executeQuery();
        while (rs.next()) {
            providedMeetingId = rs.getInt("meeting_id");
        }
        return providedMeetingId;
    }


    @Test
    public void testExceptionThrownInBindMeetingWithLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> meetingService.bindLocationIdWithMeetingId(123123, 123123));


        String expectedMessage = "Cannot bind location with meeting";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
