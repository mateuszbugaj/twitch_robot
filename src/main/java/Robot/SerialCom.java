package Robot;

import com.fazecast.jSerialComm.SerialPort;
import exception.EmptyMessageException;

import java.io.IOException;
import java.io.InputStream;

public class SerialCom {
    private final SerialPort serialPort;

    public SerialCom(SerialPort serialPort) {
        this.serialPort = serialPort;

<<<<<<< HEAD
        try {
=======
        if(serialPort != null){
>>>>>>> bf8206b0c35468a9d542ccbf3bcca5e6d319c7ea
            serialPort.setBaudRate(115200);
            serialPort.openPort();

            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

<<<<<<< HEAD
            InputStream inputStream = serialPort.getInputStream();
            if(inputStream.available() > 0){
                long skippedBytes = inputStream.skip(inputStream.available());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Serial not selected");
=======
            try {
                InputStream inputStream = serialPort.getInputStream();
                if(inputStream.available() > 0){
                    long skippedBytes = inputStream.skip(inputStream.available());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
>>>>>>> bf8206b0c35468a9d542ccbf3bcca5e6d319c7ea
        }
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
