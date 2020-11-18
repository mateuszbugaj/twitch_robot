package Managment;

import Robot.SendSerialMessageAction;
import Robot.SerialComEventHandler;
import Utils.IEnvAction;
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

    @Test
    public void updatingRobotState(){
        // Given
        SerialComEventHandler serialComEventHandler = new SerialComEventHandler(mock(SaveRobotLogAction.class));
        serialComEventHandler.subscribe(commandManager);

        // When

        // command is send immediately because robot is waiting, then robotIsWaiting is set to false
        commandManager.saveCommand(new UserCommand("content", "user"));
        serialComEventHandler.robotIsWaiting(); // robot is waiting set to true in all subscribers

        // Then

        // command is send immediately because robotIsWaiting was set by serialComEventHandler to true
        // if otherwise, command will be send to the queue making assertion fail
        commandManager.saveCommand(new UserCommand("content", "user"));
        Assert.assertEquals(commandManager.getCommandQueueSize(), 0);
    }

}
