package daoTests;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LocationDaoTest extends AbstractIntegrationTest {

    private LocationDao locationDao = new LocationDao(DBManager.getInstance());

    @Test
    public void testInsertAndGetLocationWithRuLanguage() {
        //GIVEN
        Location location = new Location();
        location.setCountry("country");
        location.setStreet("street");
        location.setRoom("1");
        location.setHouse("2");
        location.setLanguage(Language.RU);
        location.setId(1);
        location.setCity("city");

        //WHEN
        locationDao.insertLocation(location);

        //THEN
        assertEquals(location, locationDao.getLocation(location.getId()));
    }
    @Test
    public void testInsertAndGetLocationWithEnLanguage() throws SQLException, ClassNotFoundException {
        //GIVEN
        Location location = new Location();
        location.setCountry("country");
        location.setStreet("street");
        location.setRoom("1");
        location.setHouse("2");
        location.setLanguage(Language.RU);
        location.setId(1);
        location.setCity("city");

        //WHEN
        locationDao.insertLocation(location);

        //THEN
        assertEquals(location, locationDao.getLocation(location.getId()));
    }

    @Test
    public void testExceptionThrownInGetLocation() {
        //GIVEN
        Location location = locationDao.getLocation(1231231);
        String expectedMessage = "Cannot insert location";

        //WHEN
        Exception exception = assertThrows(IllegalArgumentException.class, () -> locationDao.insertLocation(location));
        String actualMessage = exception.getMessage();


        //THEN
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
