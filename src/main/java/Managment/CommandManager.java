package Managment;

import Utils.IEnvAction;
import Utils.RobotPoseSubscriber;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandManager implements RobotPoseSubscriber {
    private final IEnvAction<String > sendSerialMessageAction;
    private final Queue<UserCommand> commandQueue = new LinkedList<>();
    private Boolean robotWaiting = true;
    public ArrayList<String > robotLogs = new ArrayList<>();
    public PVector currentPose = new PVector();

    public CommandManager(IEnvAction<String> sendSerialMessageAction) {
        this.sendSerialMessageAction = sendSerialMessageAction;
    }

    public void saveCommand(UserCommand command){
        if(robotWaiting){
            sendCommandToRobot(command);
            robotWaiting = false;
        } else {
            commandQueue.add(command);
        }
    }

    public void saveRobotLog(String log) {
        robotWaiting = true;
        robotLogs.add(log);
    }

    public void sendCommandToRobot(UserCommand command){
        sendSerialMessageAction.execute(command.getContent());
    }

    public int getCommandQueueSize(){
        return commandQueue.size();
    }

    @Override
    public void updatePose(float x, float y, float z) {
        currentPose.set(x, y, z);
    }
}
