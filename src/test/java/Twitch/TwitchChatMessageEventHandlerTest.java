package Twitch;

import Managment.CommandManager;
import Managment.SaveCommandAction;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitchChatMessageEventHandlerTest {

    private TwitchChatMessageEventHandler eventHandler;
    private CommandManager commandManager;

    @Before
    public void before(){
        commandManager = new CommandManager();
        SaveCommandAction saveCommandAction = new SaveCommandAction(commandManager);
        eventHandler = new TwitchChatMessageEventHandler(saveCommandAction);

    }

    @Test
    public void testReceivingCommandFromChat(){
        // Given
        String testMessage = "!x10 y20";
        String commandContent = "x10 y20";

        IRCMessageEvent ircMessageEvent = mock(IRCMessageEvent.class);
        when(ircMessageEvent.getMessage()).thenReturn(Optional.of(testMessage));
        when(ircMessageEvent.getUserName()).thenReturn("user");

        // When
        eventHandler.accept(ircMessageEvent);

        // Then
        Assert.assertEquals(1, commandManager.commandQueue.size());
        if(commandManager.commandQueue.size() > 0){
            Assert.assertEquals(commandManager.commandQueue.peek().getContent(), commandContent);
            Assert.assertEquals(commandManager.commandQueue.peek().getUserName(), "user");
        }
    }

    @Test
    public void testReceivingInvalidCommandFromChat(){
        // Given
        String testMessage = "!k10 y20";

        IRCMessageEvent ircMessageEvent = mock(IRCMessageEvent.class);
        when(ircMessageEvent.getMessage()).thenReturn(Optional.of(testMessage));
        when(ircMessageEvent.getUserName()).thenReturn("user");

        // When
        eventHandler.accept(ircMessageEvent);

        // Then
        Assert.assertEquals(0, commandManager.commandQueue.size());
    }

    @Test
    public void testReceivingMultilineCommandFromChat(){
        // Given
        String testMessage = "!x10 y20; z30";

        IRCMessageEvent ircMessageEvent = mock(IRCMessageEvent.class);
        when(ircMessageEvent.getMessage()).thenReturn(Optional.of(testMessage));
        when(ircMessageEvent.getUserName()).thenReturn("user");

        // When
        eventHandler.accept(ircMessageEvent);

        // Then
        Assert.assertEquals(2, commandManager.commandQueue.size());
    }

    @Test
    public void testReceivingInvalidMultilineCommandFromChat(){
        // Given
        String testMessage = "!x10 p20; z30";

        IRCMessageEvent ircMessageEvent = mock(IRCMessageEvent.class);
        when(ircMessageEvent.getMessage()).thenReturn(Optional.of(testMessage));
        when(ircMessageEvent.getUserName()).thenReturn("user");

        // When
        eventHandler.accept(ircMessageEvent);

        // Then
        Assert.assertEquals(0, commandManager.commandQueue.size());
    }
}
