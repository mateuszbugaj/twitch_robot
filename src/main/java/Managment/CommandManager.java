package Managment;

import Utils.IEnvAction;
import Utils.RobotPoseSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommandManager implements RobotPoseSubscriber {
    public static Logger logger = LoggerFactory.getLogger(CommandManager.class);
    private IEnvAction<String > sendSerialMessageAction;
    private final Queue<UserCommand> commandQueue = new LinkedList<>();
    private Boolean robotWaiting = true;
    public ArrayList<String > robotLogs = new ArrayList<>();
    public PVector currentPose = new PVector();

    public CommandManager(IEnvAction<String> sendSerialMessageAction) {
        this.sendSerialMessageAction = sendSerialMessageAction;
    }

    public CommandManager() {
        // Placeholder user commands
        commandQueue.add(new UserCommand("x10 y10", "user1"));
        commandQueue.add(new UserCommand("x0", "user1"));
        commandQueue.add(new UserCommand("y20", "user1"));
        commandQueue.add(new UserCommand("y0", "ABC123"));
        commandQueue.add(new UserCommand("x123", "ABC123"));
        commandQueue.add(new UserCommand("x123 y123 z123", "25_characters_long_name__"));
        commandQueue.add(new UserCommand("y20", "user1"));
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
        try{
            sendSerialMessageAction.execute(command.getContent());
        } catch (NullPointerException e){
            logger.error(e.getMessage());
        }
    }

    public int getCommandQueueSize(){
        return commandQueue.size();
    }

    public List<UserCommand> getCommandList(){
        return new ArrayList<>(commandQueue);
    }

    @Override
    public void updatePose(float x, float y, float z) {
        currentPose.set(x, y, z);
    }
}
