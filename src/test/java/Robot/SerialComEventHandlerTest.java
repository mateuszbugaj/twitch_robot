package Robot;

import Managment.CommandManager;
import Managment.SaveRobotLogAction;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SerialComEventHandlerTest {

    SerialPortEvent mockedSerialPortEvent;
    SerialPort mockedSerialPort;

    SerialComEventHandler serialComEventHandler;
    CommandManager commandManager;

    @BeforeEach
    void setUp() {
        mockedSerialPort = mock(SerialPort.class);
        mockedSerialPortEvent = mock(SerialPortEvent.class);

        when(mockedSerialPortEvent.getSerialPort()).thenReturn(mockedSerialPort);

        commandManager = new CommandManager(mock(SendSerialMessageAction.class));
        SaveRobotLogAction saveRobotLog = new SaveRobotLogAction(commandManager);
        serialComEventHandler = Mockito.spy(new SerialComEventHandler(saveRobotLog));
    }

    @Test
    void receiveSerialMessage() {
        // Given
        String message = "Test" + (char) 10;
        doReturn(message).when(serialComEventHandler).getBuffer(mockedSerialPort);

        // When
        serialComEventHandler.serialEvent(mockedSerialPortEvent);

        // Then
        Assert.assertEquals(commandManager.robotLogs.size(), 1);

    }
}