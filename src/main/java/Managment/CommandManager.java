package Managment;

import java.util.LinkedList;
import java.util.Queue;

public class CommandManager {
    public Queue<UserCommand> commandQueue = new LinkedList<>();

    public void saveCommand(UserCommand command){
        commandQueue.add(command);
    }

}
