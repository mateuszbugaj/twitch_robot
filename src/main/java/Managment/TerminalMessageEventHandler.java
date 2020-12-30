package Managment;

import Utils.IEnvAction;
import Utils.MessageToCommandsConverter;
import exception.InvalidUserCommandException;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalMessageEventHandler implements Runnable{
    private final Scanner scanner;
    private final IEnvAction<UserCommand> saveCommand;
    private final MessageToCommandsConverter commandsParser;
    private String lastLine = "";

    public TerminalMessageEventHandler(InputStream source, IEnvAction<UserCommand> saveCommand) {
        scanner = new Scanner(source);
        this.saveCommand = saveCommand;
        commandsParser = new MessageToCommandsConverter();
    }

    @Override
    public void run() {
        System.out.println("Type in admin commands: ");

        while(scanner.hasNext()){
            lastLine = scanner.nextLine();
            if(lastLine.startsWith("!")){
                robotCommand(lastLine);
                continue;
            }

            if(lastLine.startsWith("ban")){
                banUserForSession(lastLine);
            }
        }
    }

    public void robotCommand(String line){
        try {
            List<UserCommand> userCommands = commandsParser
                    .apply(lastLine)
                    .stream()
                    .map(command -> new UserCommand(command, "root"))
                    .collect(Collectors.toList());

            userCommands.forEach(saveCommand::execute);
        } catch (InvalidUserCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    public void banUserForSession(String line){
        System.out.println("Banning...");
    }
}
