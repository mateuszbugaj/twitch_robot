package Utils;

import exception.InvalidUserCommandException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(JUnitPlatform.class)
public class MessageToCommandsConverterTest {
    private MessageToCommandsConverter converter;

    @Before
    public void before(){
        converter = new MessageToCommandsConverter();
    }

    @Test
    public void convertingSimpleMessageIntoCommands() throws InvalidUserCommandException {
        // Given
        String testMessage = "!x10";
        List<String > expectedCommands = List.of("x10");

        // When
        List<String> receivedCommands = converter.apply(testMessage);

        // Then
        Assert.assertEquals(expectedCommands, receivedCommands);

    }

    @Test
    public void convertingMessagesIntoCommands() throws InvalidUserCommandException {
        // Given
        String testMessage = "!x10 y20";
        List<String > expectedCommands = List.of("x10 y20");

        // When
        List<String> receivedCommands = converter.apply(testMessage);

        // Then
        Assert.assertEquals(expectedCommands, receivedCommands);

    }

    @Test
    public void convertingMultiLineMessagesIntoCommands() throws InvalidUserCommandException {
        // Given
        String testMessage = "!x10 y20; z20 x15";
        List<String > expectedCommands = List.of("x10 y20", "z20 x15");

        // When
        List<String> receivedCommands = converter.apply(testMessage);

        // Then
        Assert.assertEquals(expectedCommands, receivedCommands);

    }

    @Test
    public void convertingMultiLineMessagesWithInvalidCommands() {
        // Given
        String testMessage = "!x10 y20234; k20 x15; z10";

        // When
        assertThrows(InvalidUserCommandException.class, () -> converter.apply(testMessage));
    }
}
