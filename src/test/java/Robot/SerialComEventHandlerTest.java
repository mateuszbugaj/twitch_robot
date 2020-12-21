package Robot;

import Managment.CommandManager;
import Managment.SaveRobotLogAction;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SerialComEventHandlerTest {

    SerialPortEvent mockedSerialPortEvent;
    SerialPort mockedSerialPort;

    SerialComEventHandler serialComEventHandler;
    CommandManager commandManager;

    @Before
    public void setUp() {
        mockedSerialPort = mock(SerialPort.class);
        mockedSerialPortEvent = mock(SerialPortEvent.class);

        when(mockedSerialPortEvent.getSerialPort()).thenReturn(mockedSerialPort);

        commandManager = new CommandManager(mock(SendSerialMessageAction.class));
        SaveRobotLogAction saveRobotLog = new SaveRobotLogAction(commandManager);
        serialComEventHandler = Mockito.spy(new SerialComEventHandler(saveRobotLog));
    }

    @Test
    public void receiveSerialMessage() {
        // Given
        String message = "x10 y5 z20" + (char) 10;
        doReturn(message).when(serialComEventHandler).getBuffer(mockedSerialPort);

        // When
        serialComEventHandler.serialEvent(mockedSerialPortEvent);

        // Then
        Assert.assertEquals(commandManager.robotLogs.size(), 1);

    }

    @Test
    public void poseSubscriptionTest(){
        // Given
        String message = "x10 y5 z20" + (char) 10;
        Float[] expected = new Float[]{10f, 5f, 20f};
        serialComEventHandler.addPoseSubscribers(commandManager);
        doReturn(message).when(serialComEventHandler).getBuffer(mockedSerialPort);

        // When
        serialComEventHandler.serialEvent(mockedSerialPortEvent);

        // Then
        Float[] actual = new Float[]{
                commandManager.currentPose.x,
                commandManager.currentPose.y,
                commandManager.currentPose.z
        };
        Assert.assertArrayEquals(expected, actual);
    }
}