package entityTests;

import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void testUserSetters(){
        User user = new User();
        user.setId(1);
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("lm");
        user.setEmail("email");
        user.setRole(Role.USER);
        String expectedResult = "User{id=1, firstName='fn', lastName='lm', email='email', role=USER}";
        String providedResult = user.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testUserGetters(){
        User user = new User();
        user.setId(1);
        user.setPassword("123");
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("email");
        user.setRole(Role.USER);

        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("123", user.getPassword());
        Assertions.assertEquals("fn", user.getFirstName());
        Assertions.assertEquals("ln", user.getLastName());
        Assertions.assertEquals("email", user.getEmail());
        Assertions.assertEquals(1, user.getRole().getValue());
    }
}
