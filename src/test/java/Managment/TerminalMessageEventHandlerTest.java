package Managment;

import Robot.SendSerialMessageAction;
import Twitch.SendChatMessageAction;
import Twitch.TwitchChatMessageEventHandler;
import Twitch.TwitchService;
import Utils.GeneralConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TerminalMessageEventHandlerTest {
    TerminalMessageEventHandler terminalHandler;
    SaveCommandAction saveCommandAction;
    CommandManager commandManager;

    @Before
    public void before(){
        commandManager = new CommandManager(null);
        saveCommandAction = new SaveCommandAction(commandManager);
    }

    @Test
    public void inputCommand() {
        // Given
        String commandMessage = "!x10; y20";
        ByteArrayInputStream source = new ByteArrayInputStream(commandMessage.getBytes());
        terminalHandler = new TerminalMessageEventHandler(source, saveCommandAction);

        // When
        terminalHandler.run();

        // Then
        assertEquals(1, commandManager.getCommandList().size());
    }

    @Test
    public void inputCommandWithAbsoluteValues() {
        // Given
        String commandMessage = "!a x-9; z10";
        ByteArrayInputStream source = new ByteArrayInputStream(commandMessage.getBytes());
        terminalHandler = new TerminalMessageEventHandler(source, saveCommandAction);

        // When
        terminalHandler.run();

        // Then
        assertEquals(1, commandManager.getCommandList().size());
    }

    @Test
    public void inputInvalidCommand() {
        // Given
        String commandMessage = "!xINVALID";
        ByteArrayInputStream source = new ByteArrayInputStream(commandMessage.getBytes());
        terminalHandler = new TerminalMessageEventHandler(source, saveCommandAction);

        // When
        terminalHandler.run();

        // Then
        assertEquals(0, commandManager.getCommandList().size());
    }

    @Test
    public void banningUsers() {
        // Given
        String userName = "user1";
        String commandMessage = "ban " + userName;
        ByteArrayInputStream source = new ByteArrayInputStream(commandMessage.getBytes());
        terminalHandler = new TerminalMessageEventHandler(source, saveCommandAction);

        // When
        terminalHandler.run();

        // Then
        assertEquals(userName, GeneralConfig.bannedForSession.get(0));
    }
}