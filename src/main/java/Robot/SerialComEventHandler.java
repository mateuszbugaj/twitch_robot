package Robot;

import Utils.IEnvAction;
import Utils.RobotLogToPoseConverter;
import Utils.RobotPoseSubscriber;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerialComEventHandler implements SerialPortDataListener {
    private final IEnvAction<String> saveRobotLogAction;
    private final int CUTOFF_ASCII = 10; // Line feed character
    private String connectedMessage = "";
    private RobotLogToPoseConverter logToPoseConverter;
    private ArrayList<RobotPoseSubscriber> poseSubscribers = new ArrayList<>();

    public SerialComEventHandler(IEnvAction<String> saveRobotLogAction) {
        this.saveRobotLogAction = saveRobotLogAction;
        logToPoseConverter = new RobotLogToPoseConverter();
    }

    public void addPoseSubscribers(RobotPoseSubscriber... subscribers){
        poseSubscribers.addAll(Arrays.asList(subscribers));
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

            Float[] pose = logToPoseConverter.apply(outputString);
            saveRobotLogAction.execute(outputString);
            poseSubscribers.forEach(sub -> sub.updatePose(pose[0], pose[1], pose[2]));
        }

    }


    protected String getBuffer(SerialPort serialPort){
        int bytesAvailable = serialPort.bytesAvailable();
        byte[] buffer = new byte[bytesAvailable];
        serialPort.readBytes(buffer, bytesAvailable);

        return new String(buffer);
    }
}
