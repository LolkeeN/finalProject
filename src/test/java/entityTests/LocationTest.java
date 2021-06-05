package entityTests;

import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.enums.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void testLocationSetters(){
        Location location = new Location();
        location.setId(1);
        location.setCity("Dnipro");
        location.setLanguage(Language.RU);
        location.setHouse("23");
        location.setRoom("12");
        location.setStreet("Pushkina");
        location.setCountry("Ukraine");
        String expectedResult = "Location{country='Ukraine', city='Dnipro', street='Pushkina', house='23', room='12', language='RU'}";
        String providedResult = location.toString();
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testLocationGetters(){
        Location location = new Location();
        location.setId(1);
        location.setCity("Dnipro");
        location.setLanguage(Language.RU);
        location.setHouse("23");
        location.setRoom("12");
        location.setStreet("Pushkina");
        location.setCountry("Ukraine");
        Assertions.assertEquals(1, location.getId());
        Assertions.assertEquals("Dnipro", location.getCity());
        Assertions.assertEquals("23", location.getHouse());
        Assertions.assertEquals("12", location.getRoom());
        Assertions.assertEquals("Ukraine", location.getCountry());
        Assertions.assertEquals("Pushkina", location.getStreet());
        Assertions.assertEquals("ru", location.getLanguage().getValue());
    }
}
