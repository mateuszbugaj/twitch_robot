package Robot;

import com.fazecast.jSerialComm.SerialPort;
import exception.EmptyMessageException;

import java.io.IOException;

public class SerialCom {
    private final SerialPort serialPort;

    public SerialCom(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public void sendMessage(String message) throws IOException, EmptyMessageException {
        if(message == null || message.isEmpty()){
            throw new EmptyMessageException("Cannot send empty message to the robot!");
        }

        if(serialPort != null){
            serialPort.getOutputStream().write(message.getBytes());
        }
    }
}
