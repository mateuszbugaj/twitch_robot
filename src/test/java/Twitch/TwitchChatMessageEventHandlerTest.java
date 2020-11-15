package Twitch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitchChatMessageEventHandlerTest {

    private TwitchChatMessageEventHandler eventHandler;

    @Before
    public void before(){
        eventHandler = new TwitchChatMessageEventHandler();
    }

    @Test
    public void testConvertingMessages(){
        // Given
        String testMessage = "!x10 y20";

        // When
    }
}
