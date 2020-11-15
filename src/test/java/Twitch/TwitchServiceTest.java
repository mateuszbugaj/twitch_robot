package Twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.TwitchChat;
import exception.EmptyChatMessageException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TwitchServiceTest {
    private final String TEST_CHANNEL_NAME = "testChannelName";

    @Test
    public void sendChatMessage() throws Exception {
        // Given
        String message = "Test message";
        TwitchClient mockedTwitchClient = mock(TwitchClient.class);
        TwitchService twitchService = new TwitchService(mockedTwitchClient, TEST_CHANNEL_NAME);

        // When
        TwitchChat mockedTwitchChat = mock(TwitchChat.class);
        when(mockedTwitchClient.getChat()).thenReturn(mockedTwitchChat);
        doNothing().when(mockedTwitchChat).sendMessage(TEST_CHANNEL_NAME, message);
        twitchService.sendChatMessage(message);

        // Then
        verify(mockedTwitchChat, times(1)).sendMessage(TEST_CHANNEL_NAME, message);
    }

    @Test
    public void sendEmptyChatMessage(){
        // Given
        String testMessage = "";
        TwitchClient mockedTwitchClient = mock(TwitchClient.class);
        TwitchService twitchService = new TwitchService(mockedTwitchClient, TEST_CHANNEL_NAME);

        // When
        TwitchChat mockedTwitchChat = mock(TwitchChat.class);
        Assertions.assertThrows(EmptyChatMessageException.class, () -> twitchService.sendChatMessage(testMessage));

        // Then
        verify(mockedTwitchChat, times(0)).sendMessage(TEST_CHANNEL_NAME, testMessage);
    }

    @Test
    public void sendNullChatMessage(){
        // Given
        String testMessage = null;
        TwitchClient mockedTwitchClient = mock(TwitchClient.class);
        TwitchService twitchService = new TwitchService(mockedTwitchClient, TEST_CHANNEL_NAME);

        // When
        TwitchChat mockedTwitchChat = mock(TwitchChat.class);
        Assertions.assertThrows(EmptyChatMessageException.class, () -> twitchService.sendChatMessage(testMessage));

        // Then
        verify(mockedTwitchChat, times(0)).sendMessage(TEST_CHANNEL_NAME, testMessage);
    }
}
