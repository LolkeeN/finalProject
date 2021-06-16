package daoTests;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.impl.DaoFactoryImpl;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTest {
    private final DaoFactory daoFactory = new DaoFactoryImpl();
    private final UserDao userDao = daoFactory.getUserDao();


    @Test
    public void testInsertUser() throws ClassNotFoundException, SQLException {
        User user = new User();
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setRole(Role.USER);
        user.setPassword("123");
        user.setEmail("email");
        userDao.insertUser(user);
        User providedResult = userDao.getUser(user.getId());
        Assertions.assertEquals(user, providedResult);
    }

    @Test
    public void testGetUser() throws SQLException {
        User user = new User("fn", "ln", "pass", "email", Role.USER);
        user.setId(userDao.getUser(user.getId()).getId());
        User user1 = userDao.getUser(user.getId());
        String providedResult1 = userDao.getUser(user1.getEmail(), user1.getPassword()).toString();
        Assertions.assertEquals(user1.toString(), providedResult1);
    }

}
