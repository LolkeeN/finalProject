package daoTests;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTest {
    private static final String CONNECTION_URL = "jdbc:h2:~/meetings";
    Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");

    public UserDaoTest() throws SQLException {
    }

    @Test
    public void testInsertUser() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass");
        UserDao userDao = new UserDao();
        User user = new User();
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setRole(Role.USER);
        user.setPassword("123");
        user.setEmail("email");
        userDao.insertUser(connection, user);
        String expectedResult = user.toString();
        String providedResult = userDao.getUser(connection, user.getId()).toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testGetUser() throws SQLException {
        UserDao userDao = new UserDao();
        User user = new User("fn", "ln", "pass", "email", Role.USER);
        user.setId(userDao.getUser(DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass"), user.getId()).getId());
        User user1 = userDao.getUser(DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass"), user.getId());
        String providedResult1 = userDao.getUser(DriverManager.getConnection(CONNECTION_URL, "root", "myrootpass"), user1.getEmail(), user1.getPassword()).toString();
        Assertions.assertEquals(user1.toString(), providedResult1);
    }

    @Test
    public void testExceptionThrownInAddParticipantToMeeting() {
        UserDao userDao = new UserDao();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userDao.insertUser(connection, new User()));


        String expectedMessage = "Cannot get user by id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
