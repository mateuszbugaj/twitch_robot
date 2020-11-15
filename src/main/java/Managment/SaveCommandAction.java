package Managment;

import Utils.IEnvAction;

public class SaveCommandAction implements IEnvAction<UserCommand> {

    private final CommandManager commandManager;

    public SaveCommandAction(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void execute(UserCommand command) {
        commandManager.saveCommand(command);
    }
}
