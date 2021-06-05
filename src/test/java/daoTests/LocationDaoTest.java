package daoTests;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.enums.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocationDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";

    @Test
    public void testInsertAndGetLocation() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        LocationDao locationDao = new LocationDao();
        Location location = new Location();
        location.setCountry("country");
        location.setStreet("street");
        location.setRoom("1");
        location.setHouse("2");
        location.setLanguage(Language.RU);
        location.setId(1);
        location.setCity("city");
        locationDao.insertLocation(connection, location);
        String expectedResult = location.toString();
        String providedResult = locationDao.getLocation(connection, location.getId()).toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }
//
//    @Test
//    public void testGetLocation() throws ClassNotFoundException, SQLException {
//        Class.forName("org.h2.Driver");
//        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
//        LocationDao locationDao = new LocationDao();
//        Location location = new Location();
//        location.setCountry("country");
//        location.setStreet("street");
//        location.setRoom("1");
//        location.setHouse("2");
//        location.setLanguage(Language.EN);
//        location.setId(1);
//        location.setCity("city");
//        String expectedResult = location.toString();
//        String providedResult = locationDao.getLocation(connection, location.getId()).toString();
//        Assertions.assertEquals(expectedResult, providedResult);
//    }
}
