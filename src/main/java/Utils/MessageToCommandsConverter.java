package Utils;


import exception.InvalidUserCommandException;

import java.util.ArrayList;
import java.util.List;

public class MessageToCommandsConverter implements FunctionWithException<String, List<String>, InvalidUserCommandException> {
    @Override
    public List<String> apply(String message) throws InvalidUserCommandException {

        List<String > robotCommands = new ArrayList<>();
        String[] proposedCommands = message.replace("!", "").split(";");
        for(String proposedCommand: proposedCommands){
            String extractedCommand = proposedCommand.replaceAll("[\n\t]", "").trim();

            if(!valid(extractedCommand)){
                throw new InvalidUserCommandException("Command " + extractedCommand + " is invalid!");
            }

            robotCommands.add(extractedCommand);
        }

        return robotCommands;

    }

    private boolean valid(String command){
        return command.matches("(a )?([xyz ]-?[0-9]{1,3}\\s?){1,3}");
    }
}
