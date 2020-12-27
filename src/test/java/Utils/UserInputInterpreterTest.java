package Utils;

import Managment.CommandManager;
import Managment.SaveCommandAction;
import Robot.SendSerialMessageAction;
import exception.InvalidOptionChoice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class UserInputInterpreterTest {


    @Before
    public void before(){
    }

    @Test
    public void interpretUserInputAsChoice() throws InvalidOptionChoice {
        // Given
        ArrayList<String > options = new ArrayList<>(Arrays.asList("x", "y", "z"));
        String userInput = "1";
        String expected = "y";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        // When
        String  choice = UserInputInterpreter.choice(options);

        // THen
        Assert.assertEquals(expected, choice);
    }

    @Test
    public void notIntegerUserInput() {
        // Given
        ArrayList<String > options = new ArrayList<>(Arrays.asList("x", "y", "z"));
        String userInput = "A";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        // When
        Assertions.assertThrows(InvalidOptionChoice.class, () -> UserInputInterpreter.choice(options));
    }

    @Test
    public void outOfRangeUserInput() {
        // Given
        ArrayList<String > options = new ArrayList<>(Arrays.asList("x", "y", "z"));
        String userInput = "3";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        // When
        Assertions.assertThrows(InvalidOptionChoice.class, () -> UserInputInterpreter.choice(options));
    }


}