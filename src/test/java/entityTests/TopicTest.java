package entityTests;

import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TopicTest {
    @Test
    public void testTopicSetters(){
        Topic topic = new Topic();
        topic.setDate("03.06.21");
        topic.setAvailability(true);
        topic.setId(1);
        topic.setName("name");
        topic.setLanguage(Language.EN);
        topic.setDescription("description");
        topic.setSpeaker(new User());
        String providedResult = topic.toString();
        String expectedResult = "Topic{id=1, name='name', description='description', speaker=User{id=0, firstName='null', lastName='null', email='null', role=null}, date='03.06.21', language=EN, availability=true}";
        Assertions.assertEquals(expectedResult, providedResult);
    }

    @Test
    public void testTopicGetters(){
        Topic topic = new Topic();
        topic.setDate("03.06.21");
        topic.setAvailability(true);
        topic.setId(1);
        topic.setName("name");
        topic.setLanguage(Language.EN);
        topic.setDescription("description");
        User user = new User();
        topic.setSpeaker(user);
        Assertions.assertEquals("03.06.21", topic.getDate());
        Assertions.assertEquals(1,topic.getId());
        Assertions.assertEquals("description", topic.getDescription());
        Assertions.assertEquals("en",topic.getLanguage().getValue());
        Assertions.assertEquals("name",topic.getName());
        Assertions.assertTrue(topic.isAvailability());
        Assertions.assertEquals(user,topic.getSpeaker());
    }

    @Test
    public void testTopicConstructor(){
        Topic topic = new Topic("name");
        Topic topic2 = new Topic();
        topic2.setName("name");
        Assertions.assertEquals(topic.toString(), topic2.toString());
    }
}
