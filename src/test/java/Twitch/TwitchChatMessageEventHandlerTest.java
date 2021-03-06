package Twitch;

import Managment.CommandManager;
import Managment.SaveCommandAction;
import Robot.SendSerialMessageAction;
import Utils.GeneralConfig;
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
        commandManager = new CommandManager(mock(SendSerialMessageAction.class));
        SaveCommandAction saveCommandAction = new SaveCommandAction(commandManager);
        SendChatMessageAction sendChatMessageAction = new SendChatMessageAction(mock(TwitchService.class));
        eventHandler = new TwitchChatMessageEventHandler(saveCommandAction, sendChatMessageAction);
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

        // Robot is waiting so one command should be immediately send to the robot
        int commandQueueSize = commandManager.getCommandQueueSize();
        Assert.assertEquals(0, commandQueueSize);
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
        int commandQueueSize = commandManager.getCommandQueueSize();
        Assert.assertEquals(0, commandQueueSize);
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
        // Robot is waiting so one command should be immediately send to the robot
        // and one should be saved in a queue
        int commandQueueSize = commandManager.getCommandQueueSize();
        Assert.assertEquals(1, commandQueueSize);
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
        int commandQueueSize = commandManager.getCommandQueueSize();
        Assert.assertEquals(0, commandQueueSize);
    }

    @Test
    public void commandSendByBannedUser(){
        // Given
        String testMessage = "!x10 y20; z30";
        String userName = "user1";

        IRCMessageEvent ircMessageEvent = mock(IRCMessageEvent.class);
        when(ircMessageEvent.getMessage()).thenReturn(Optional.of(testMessage));
        when(ircMessageEvent.getUserName()).thenReturn(userName);
        GeneralConfig.bannedForSession.add(userName);

        // When
        eventHandler.accept(ircMessageEvent);

        // Then
        int commandQueueSize = commandManager.getCommandQueueSize();
        Assert.assertEquals(0, commandQueueSize);
    }
}
