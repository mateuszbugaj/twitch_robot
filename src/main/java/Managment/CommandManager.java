package Managment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CommandManager {
    public Queue<UserCommand> commandQueue = new LinkedList<>();
    public ArrayList<String > robotLogs = new ArrayList<>();

    public void saveCommand(UserCommand command){
        commandQueue.add(command);
    }

    public void saveRobotLog(String log) {
        robotLogs.add(log);
    }
}
