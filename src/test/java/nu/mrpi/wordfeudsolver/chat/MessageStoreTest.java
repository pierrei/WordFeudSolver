package nu.mrpi.wordfeudsolver.chat;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Pierre Ingmansson
 */
public class MessageStoreTest {
    private MessageStore messageStore;

    @Before
    public void setUp() throws Exception {
        messageStore = new MessageStore();
    }

    @Test
    public void getBragMessages() throws Exception {
        final String message = messageStore.getRandomBragMessage(Locale.ENGLISH);
        assertNotNull(message);
    }

    @Test
    public void getEnglishGreeting() throws Exception {
        final String greeting = messageStore.getGreeting(Locale.ENGLISH, "MrPi");
        assertNotNull(greeting);
        assertTrue(greeting.contains("Hi MrPi!"));
    }

    @Test
    public void getSwedishGreeting() throws Exception {
        final String greeting = messageStore.getGreeting(Locale.forLanguageTag("sv"), "MrPi");
        assertNotNull(greeting);
        assertTrue(greeting.contains("Hej MrPi!"));
    }
}
