package Managment;

import Utils.IEnvAction;
import Utils.RobotPoseSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandManager implements RobotPoseSubscriber {
    public static Logger logger = LoggerFactory.getLogger(CommandManager.class);
    private IEnvAction<String > sendSerialMessageAction;
    private final LinkedList<UserCommand> commandQueue = new LinkedList<>();
    private Boolean robotWaiting = true;
    public ArrayList<String > robotLogs = new ArrayList<>();
    public PVector currentPose = new PVector();

    public CommandManager(IEnvAction<String> sendSerialMessageAction) {
        this.sendSerialMessageAction = sendSerialMessageAction;
    }

    public CommandManager() {
        // Placeholder user commands
//        commandQueue.add(new UserCommand("x123 y123 z123", "25_characters_long_name__"));

        for (int i = 0; i < 10; i++) {
            commandQueue.add(new UserCommand("y20", "user1"));
            commandQueue.add(new UserCommand("x55", "user1"));
            commandQueue.add(new UserCommand("x0 y0", "user1"));
            commandQueue.add(new UserCommand("y70", "user2"));
            commandQueue.add(new UserCommand("x300", "user2"));
            commandQueue.add(new UserCommand("x0 y0", "user3"));
        }

        robotWaiting = false;
    }

    public void saveCommand(UserCommand command, boolean saveAsFirst){
        if(robotWaiting){
            sendCommandToRobot(command);
            robotWaiting = false;
            return;
        }

        if (saveAsFirst){
            if (commandQueue.size() > 0 && commandQueue.peek().getUserName().equals(command.getUserName())){
                int userCommands = (int) commandQueue
                        .stream()
                        .filter(i -> i.getUserName().equals(command.getUserName()))
                        .count();

                commandQueue.add(userCommands, command);
                return;
            }

            commandQueue.addFirst(command);
            return;
        }

        commandQueue.add(command);
    }

    public void saveRobotLog(String log) {
        if(log.contains("DONE")){
            robotWaiting = true;
        }

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
