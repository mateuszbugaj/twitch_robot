package Managment;

import Utils.IEnvAction;

public class SaveCommandAction implements IEnvAction<UserCommand> {

    private final CommandManager commandManager;
    private boolean saveAsFirst = false;

    public SaveCommandAction(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public SaveCommandAction setSaveAsFirst(boolean saveAsFirst) {
        this.saveAsFirst = saveAsFirst;
        return this;
    }

    @Override
    public void execute(UserCommand command) {
        commandManager.saveCommand(command, saveAsFirst);
    }
}
