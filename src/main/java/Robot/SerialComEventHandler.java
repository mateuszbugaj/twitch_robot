package Robot;

import Managment.SaveRobotLogAction;
import Utils.IEnvAction;
import Utils.Subscriber;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.ArrayList;
import java.util.List;

public class SerialComEventHandler implements SerialPortDataListener {
    private final IEnvAction<String> saveRobotLogAction;
    private final int CUTOFF_ASCII = 10; // Line feed character
    private String connectedMessage = "";
    private List<Subscriber> subscribers = new ArrayList<>();

    public SerialComEventHandler(IEnvAction<String> saveRobotLogAction) {
        this.saveRobotLogAction = saveRobotLogAction;
    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        SerialPort serialPort = serialPortEvent.getSerialPort();
        connectedMessage = connectedMessage.concat(getBuffer(serialPort));
        if((connectedMessage.indexOf(CUTOFF_ASCII) + 1) > 0){
            String outputString = connectedMessage
                    .substring(0, connectedMessage.indexOf(CUTOFF_ASCII) + 1)
                    .replace("\n", "");

            connectedMessage = connectedMessage.substring(connectedMessage.indexOf(CUTOFF_ASCII) + 1);

            saveRobotLogAction.execute(outputString);
            robotIsWaiting();
        }

    }

    protected String getBuffer(SerialPort serialPort){
        int bytesAvailable = serialPort.bytesAvailable();
        byte[] buffer = new byte[bytesAvailable];
        serialPort.readBytes(buffer, bytesAvailable);

        return new String(buffer);
    }

    public void robotIsWaiting(){
        subscribers.forEach(sub -> sub.update(true));
    }


}
