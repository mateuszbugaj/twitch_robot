package Managment;

import Utils.IEnvAction;

public class SaveRobotLogAction implements IEnvAction<String > {

    private final CommandManager commandManager;

    public SaveRobotLogAction(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(String log) {
        commandManager.saveRobotLog(log);
    }
}
