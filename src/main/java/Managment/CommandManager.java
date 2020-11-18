package Managment;

import Utils.IEnvAction;
import Utils.Subscriber;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandManager implements Subscriber {
    private final IEnvAction<String > sendSerialMessageAction;
    private final Queue<UserCommand> commandQueue = new LinkedList<>();
    private Boolean robotWaiting = true;
    public ArrayList<String > robotLogs = new ArrayList<>();

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
        robotLogs.add(log);
    }

    public void sendCommandToRobot(UserCommand command){
        sendSerialMessageAction.execute(command.getContent());
    }

    public int getCommandQueueSize(){
        return commandQueue.size();
    }

    @Override
    public void update(boolean context) {
        robotWaiting = context;
    }
}
