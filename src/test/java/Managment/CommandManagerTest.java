package Managment;

import Robot.SendSerialMessageAction;
import Robot.SerialComEventHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CommandManagerTest {

    private CommandManager commandManager;
    private SaveCommandAction saveCommandAction;


    @Before
    public void before(){
        commandManager = new CommandManager(mock(SendSerialMessageAction.class));
        saveCommandAction = new SaveCommandAction(commandManager);
    }

    @Test
    public void addingCommands(){
        // Given
        UserCommand testCommand = new UserCommand("content", "user");

        // When
        saveCommandAction.execute(testCommand); // first is going to be send immediately
        saveCommandAction.execute(testCommand);

        Assert.assertEquals(1, commandManager.getCommandQueueSize());
    }

}
