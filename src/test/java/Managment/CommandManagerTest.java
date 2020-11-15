package Managment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CommandManagerTest {

    private CommandManager commandManager;

    @Before
    public void before(){
        commandManager = new CommandManager();
    }

    @Test
    public void addingCommands(){
        // Given
        UserCommand testCommand = new UserCommand("content", "user");

        // When
        commandManager.saveCommand(testCommand);

        Assert.assertEquals(1, commandManager.commandQueue.size());
    }

}
