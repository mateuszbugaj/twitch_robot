package Managment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommandManagerTest {

    private CommandManager commandManager;
    private SaveCommandAction saveCommandAction;

    @Before
    public void before(){
        commandManager = new CommandManager();
        saveCommandAction = new SaveCommandAction(commandManager);
    }

    @Test
    public void addingCommands(){
        // Given
        UserCommand testCommand = new UserCommand("content", "user");

        // When
        saveCommandAction.execute(testCommand);

        Assert.assertEquals(1, commandManager.commandQueue.size());
    }

}
