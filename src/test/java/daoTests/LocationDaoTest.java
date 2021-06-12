package daoTests;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.service.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void testInsertAndGetLocationWithRuLanguage() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        LocationDao locationDao = new LocationDao(DBManager.getInstance());
        Location location = new Location();
        location.setCountry("country");
        location.setStreet("street");
        location.setRoom("1");
        location.setHouse("2");
        location.setLanguage(Language.RU);
        location.setId(1);
        location.setCity("city");
        locationDao.insertLocation(location);
        String expectedResult = location.toString();
        String providedResult = locationDao.getLocation(connection, location.getId()).toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }
    @Test
    public void testInsertAndGetLocationWithEnLanguage() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        LocationDao locationDao = new LocationDao(DBManager.getInstance());
        Location location = new Location();
        location.setCountry("country");
        location.setStreet("street");
        location.setRoom("1");
        location.setHouse("2");
        location.setLanguage(Language.EN);
        location.setId(1);
        location.setCity("city");
        locationDao.insertLocation(location);
        String expectedResult = location.toString();
        String providedResult = locationDao.getLocation(connection, location.getId()).toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testExceptionThrownInGetLocation() throws SQLException {
        LocationDao locationDao = new LocationDao(DBManager.getInstance());
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");

        Location location = locationDao.getLocation(connection, 1231231);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> locationDao.insertLocation(location));


        String expectedMessage = "Cannot insert location";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
