package Twitch;

import Managment.SaveCommandAction;
import Managment.UserCommand;
import Utils.GeneralConfig;
import Utils.IEnvAction;
import Utils.MessageToCommandsConverter;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import exception.InvalidUserCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TwitchChatMessageEventHandler implements Consumer<IRCMessageEvent> {
    public static Logger log = LoggerFactory.getLogger(TwitchChatMessageEventHandler.class);
    private final MessageToCommandsConverter converter;
    private final IEnvAction<UserCommand> saveCommandAction;
    private final IEnvAction<String > sendChatMessageAction;

    public TwitchChatMessageEventHandler(IEnvAction<UserCommand> saveCommandAction,
                                         IEnvAction<String > sendChatMessageAction) {

        this.saveCommandAction = saveCommandAction;
        this.sendChatMessageAction = sendChatMessageAction;
        converter = new MessageToCommandsConverter();
    }

    @Override
    public void accept(IRCMessageEvent ircMessageEvent) {
        ircMessageEvent.getMessage().ifPresent(message -> { // todo: there might be better to use .getRawMassage()
            String userName = ircMessageEvent.getUserName();
            log.info("New message '" + message + "' from " + userName);
            if(GeneralConfig.bannedForSession.contains(userName)){
                log.info(userName + " is banned for this session from inputting commands");
                return;
            }

            if(message.startsWith("!")){
                try {
                    List<UserCommand> userCommands = converter.apply(message)
                            .stream()
                            .map(command -> new UserCommand(command, userName))
                            .collect(Collectors.toList());

                    log.info("Got commands: \n" + userCommands.stream().map(x -> x.toString() + "\n").collect(Collectors.joining()));
                    userCommands.forEach(saveCommandAction::execute);
                } catch (InvalidUserCommandException e) {
                    log.error(e.getMessage());
                    sendChatMessageAction.execute(e.getMessage());
                }
            }
        });
    }
}
