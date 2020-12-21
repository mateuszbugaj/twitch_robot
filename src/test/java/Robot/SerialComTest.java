package Robot;

import com.fazecast.jSerialComm.SerialPort;
import exception.EmptyMessageException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SerialComTest {

    SerialCom serialCom;
    SerialPort mockedSerialPort;

    @Before
    public void setUp() {
        mockedSerialPort = mock(SerialPort.class);
        serialCom = new SerialCom(mockedSerialPort);
    }


    @Test
    public void sendMessage() throws IOException, EmptyMessageException {
        // Given
        String message = "test";
        byte[] messageBytes = message.getBytes();

        // When
        OutputStream mockedOutputStream = mock(OutputStream.class);
        when(mockedSerialPort.getOutputStream()).thenReturn(mockedOutputStream);
        doNothing().when(mockedOutputStream).write(messageBytes);
        serialCom.sendMessage(message);

        // Then
        verify(mockedSerialPort.getOutputStream(), times(1)).write(messageBytes);
    }

    @Test
    public void sendEmptyMessage() throws IOException {
        // Given
        String message = "";
        byte[] messageBytes = message.getBytes();

        // When
        OutputStream mockedOutputStream = mock(OutputStream.class);
        when(mockedSerialPort.getOutputStream()).thenReturn(mockedOutputStream);
//        doNothing().when(mockedOutputStream).write(messageBytes);
        Assertions.assertThrows(EmptyMessageException.class, () -> serialCom.sendMessage(message));

        // Then
        verify(mockedSerialPort.getOutputStream(), times(0)).write(messageBytes);
    }

//    @Test
//    public void sendNullMessage() throws IOException {
//        // Given
//        String message = null;
//        byte[] messageBytes = null;
//
//        // When
//        OutputStream mockedOutputStream = mock(OutputStream.class);
//        when(mockedSerialPort.getOutputStream()).thenReturn(mockedOutputStream);
//        doNothing().when(mockedOutputStream).write(messageBytes);
//        Assertions.assertThrows(EmptyMessageException.class, () -> serialCom.sendMessage(message));
//
//        // Then
//        verify(mockedSerialPort.getOutputStream(), times(0)).write(messageBytes);
//    }

}